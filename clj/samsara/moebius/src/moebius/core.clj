(ns moebius.core
  (:require [clojure.string :as s])
  (:require [clojure.java.io :as io])
  (:require [taoensso.timbre :as log])
  (:require [clojure.core.match :refer [match]])
  (:require [potemkin :refer [import-vars]])
  (:require [where.core]))



;;
;; #              Internal processing core functions
;;

(defn- stepper
  "It returns a function which performs a single step in the cycle. It
  processes the first event from the `to-process` and puts the result in
  `processed` if it's not `nil`. When the result is expanded with multiple
  events then these are added to head of `to-process`."
  [f]
  (fn [{:keys [to-process processed state] :as domain}]
    (if-not (seq to-process)
      domain
      (let [[new-state [head & tail]] (f state (first to-process))]
        {:to-process (seq (concat tail (next to-process)))
         :state      new-state
         :processed  (if head (conj processed head) processed)}))))



(defn- cycler
  "It takes a function and a list of events.
  The function `f` takes a single event and must return a list
  of 0 or more elements. When `f` returns an empty list,
  the element has been filtered out. When the function `f` returns
  only 1 element it is commonly referred as an enrichment process.
  When the `f` returns more than one elements the first one is typically
  the event which has been processed and any additional are correlated
  events which will be put back in the cycle to to follow the same process.
  If you wish to just `expand` the event (given an event produce 2 or
  more events and discard the original event) then all you need to
  do is return a list of events in which the first element is `nil`.
  This is called expansion.
  "
  [f state events]
  (->> (stepper f)
       (#(iterate % {:to-process events :state state :processed []}))
       (drop-while :to-process)
       first
       ((juxt :state :processed))))



;;
;; #              Functional composition helpers
;;

(defn- stateful
  "Wraps a stateless function into a stateful one.
  It returns a function which takes two parameters `state` and `event`
  and applies `f` to the event and leave the state unchanged."
  [f]
  (fn [state event]
    [state (f event)]))



(defn- stateful-pred
  "Wraps a stateless predicate function into a stateful one.
  It returns a function which takes two parameters `state` and `event`
  and applies `pred` to the event and ignore the state."
  [pred]
  (fn [state event]
    (pred event)))



(defn- generic-wrapper
  "Generic wrapper to transform the output of processing functions
  into the format accepted by the `cycler`.  The internal cycle expects
  a function which takes one parameter composed of
  `[state [event & tail]]` and return the new state and a new list
  of events."
  [wrapper]
  (fn [f]
    (fn [[state [event & tail] :as input]]
      (if-not event
        ;; if the event is nil (filtered out)
        ;; then skip process
        input
        (let [result (f state event)
              [s' e'] (wrapper state event result)]
          [s' (concat e' tail)])))))



(defn- pipeline-wrapper
  "Takes a pipeline which accepts state and event and wraps the result
  into an array as expected by the `cycler`"
  [f]
  ((generic-wrapper
    (fn [_ _ result]
      result)) f))



;; TODO: should return state or s' when returned state is nil?
(defn- enricher-wrapper
  "Takes a function which accepts an event and wraps the result
  into an array as expected by the `cycler`"
  [f]
  ((generic-wrapper
     (fn [s0 e0 [s1 e1]]
       (if e1
         [s1 [e1]]
         [s0 [e0]]))) f))



(defn- correlator-wrapper
  "Takes a function which accepts an event and turns the output into
  something expected by the cycler.
  If the correlation function return nil, the state is left
  unchanged and no event is generated.
  If the state is changed, it needs to be propagated even if no
  events are generated. If the state is changed and 1 or more
  events are generated then the new state must be returned
  and all events must be added to the processing queue.
  In any case the given event must NOT be changed."
  [f]
  ((generic-wrapper
    (fn [s0 e0 [s1 r :as result]]
      (if result
        (let [correlated (if (map? r) [r] r)]
          [s1 (concat [e0] correlated)])
        [s0 [e0]]))) f))



(defn- filterer-wrapper
  "Similar to `filter` it takes a predicate which applied to
  an event return something truthy for the events to keep."
  [pred]
  ((generic-wrapper
    (fn [s0 e0 result]
      [s0 [(when result e0)]])) pred))



(defn- as-stateful
  "Returns a stateful version of the function wrapped with the
  appropriate wrapper. If the function is already stateful it is
  returned without wrapping"
  [f {:keys [moebius-wrapper moebius-type] :as m}]
  (let [stateful' (if (= :filtering moebius-type) stateful-pred stateful)
        wrapped (case moebius-wrapper :stateful f :stateless (stateful' f))]
    wrapped))



(defn- wrapped-fn
  "It takes a stateful function and wraps it according to its semantic
  behavior. It add `:moebius-normalized true` to the metadata."
  [f {:keys [moebius-wrapper moebius-type] :as m}]
  (let [wrapper (case moebius-type
                  :enrichment enricher-wrapper
                  :correlation correlator-wrapper
                  :filtering filterer-wrapper
                  :pipeline pipeline-wrapper)]
    (with-meta (wrapper f) (assoc m :moebius-normalized true))))



(defn moebius-fn
  "Takes a normal clojure function and add the necessary metadata
   to be used inside a pipeline.

     - `name`: is the function name as in `defn`
     - `docstring`: the description of the function
     - `type`: is one of: :enrichment, `:correlation`, `:filtering`
          or `:pipeline`
     - `statefulness`: whether the function is handling the state
          as well or it is stateless. Should be one of the following
          options: `:stateful` or `:stateless`
     - `f`: is the function which either process a single event
          or it process `state` and `event`
     - `opts`: optional parameters are used to add more details
          such as:
        - `moebius-fns` in the pipeline which contains
          the list of function which have been packed together.
  "
  [name docstring type statefulness f & {:as opts}]
  (with-meta f
    (merge
     opts
     {:moebius-name name
      :moebius-doc  docstring
      :moebius-wrapper statefulness
      :moebius-type type})))



;; TODO: docstring support
(defn- compose-pipeline
  "Composes the given moebius function into a single function
   which is the logical equivalent of (-> [state event] f1 f2 f3 f4)"
  [& fs]
  (let [metas   (apply vector (map meta fs))
        compose (apply comp (reverse fs))
        wrap    (comp compose (fn [state e] [state [e]]))]
    (moebius-fn "pipeline" "" :pipeline :stateful wrap :moebius-fns metas)))



;;
;; #              Public use functions
;;

(defn pipeline
  "Pipeline composes stearming processing functions
  into a chain of processing which is then applied by
  `cycler`"
  [& fs]
  (->> fs
       (map (fn [f]
              (let [m (meta f)]
                (wrapped-fn (as-stateful f m) m))))
       (apply compose-pipeline)))



(defmacro -def-moebius-function
  "handy macro to define an moebius function"
  [type fname docstring params & body]
  (let [p# (count params)
        wrapper (if (= 2 p#) :stateful :stateless)]
    (if (not (<= 1 p# 2))
      (throw (IllegalArgumentException.
              (str "Invalid number of parameters for function: "
                   name ". It can be either [event] or [state event]")))
      (let [attrs (merge (meta fname)
                         {:doc docstring
                          :arglists [`'~params]})
            fsym (with-meta fname attrs)]
        `(def ~fsym
           (moebius-fn (str '~fname) ~docstring ~type ~wrapper
                       (fn ~params
                         ~@body)))))))


(defmacro -def-moebius-builder [type fname docstring args &
                                [[afn params & body] :as forms]]
  (let [p# (count params)
        wrapper (if (= 2 p#) :stateful :stateless)]
    (if (not (<= 1 p# 2))
      (throw (IllegalArgumentException.
              (str "Invalid number of parameters for function: "
                   name ". It can be either [event] or [state event]")))
      (let [attrs (merge (meta fname)
                         {:doc docstring
                          :arglists [`'~params]})
            fsym (with-meta fname attrs)]
        `(defn ~fsym ~args
           (moebius-fn (str '~fname) ~docstring ~type ~wrapper
                       ~@forms))))))



(defmacro defenrich
  "handy macro to define an enrichment function"
  ^{:arglists '([name docstring? [event] & body]
                [name docstring? [state event] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-function :enrichment ~name ~docstring ~params ~@body)))



(defmacro defenrich*
  "handy macro to define an enrichment function which closes over a configuration"
  ^{:arglists '([name docstring? [cfg] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-builder :enrichment ~name ~docstring ~params ~@body)))



(defn as-enrich
  ([name statefulness f]
   (moebius-fn name "" :enrichment statefulness f))
  ([name docstring statefulness f]
   (moebius-fn name docstring :enrichment statefulness f)))



(defmacro defcorrelate
  "handy macro to define a correlations function"
  ^{:arglists '([name docstring? [event] & body]
                [name docstring? [state event] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-function :correlation ~name ~docstring ~params ~@body)))



(defmacro defcorrelate*
  "handy macro to define an correlation function which closes over a configuration"
  ^{:arglists '([name docstring? [cfg] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-builder :correlation ~name ~docstring ~params ~@body)))



(defn as-correlate
  ([name statefulness f]
   (moebius-fn name "" :correlation statefulness f))
  ([name docstring statefulness f]
   (moebius-fn name docstring :correlation statefulness f)))



(defmacro deffilter
  "handy macro to define a filtering function"
  ^{:arglists '([name docstring? [event] & body]
                [name docstring? [state event] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-function :filtering ~name ~docstring ~params ~@body)))



(defmacro deffilter*
  "handy macro to define an filtering function which closes over a configuration"
  ^{:arglists '([name docstring? [cfg] & body])}
  [name & args]
  (let [[docstring params & body] (if (string? (first args)) args (cons "" args))]
    `(-def-moebius-builder :filtering ~name ~docstring ~params ~@body)))



(defn as-filter
  ([name statefulness f]
   (moebius-fn name "" :filtering statefulness f))
  ([name docstring statefulness f]
   (moebius-fn name docstring :filtering statefulness f)))



(defn inject-if
  "injects `value` if `condition` is truthy,
  to the given `event` with the `property` name"
  [event condition property value]
  (if condition
    (assoc event property value)
    event))



(defn inject-as
  "injects `value` if not null, to the given `event`
  with the `property` name"
  [event property value]
  (inject-if event value property value))



(defmacro when-event-name-is
  "if the eventName of the given event is equal to the give name
  then evaluate the body. Otherwhise `nil` is returned.

   example:

      (when-event-name-is event \"game.started\"
          (-> event
              (assoc :new-property \"a-value\")
              (assoc :property2 6)))


  alternatively you can provide a list of event's names:

      (when-event-name-is event [\"game.started\" \"game.level.completed\"]
          (assoc event :new-property \"a-value\"))

  "
  {:style/indent 2}
  [event name & body]
  `(let [_event# ~event _name# ~name
         _names# (if (string? _name#) [_name#] _name#)]
     (when (contains? (set _names#) (:eventName _event#))
       ~@body)))



(defmacro when-event-match
  "If the event matches one of the patterns the related expression is
  evaluated and returned.
  If none matches `nil` is returned.

   example:


       (let [event {:eventName \"game.started\" :level 8}]
          (when-event-match event
            [{:eventName \"game.started\" :level 0}]
            (assoc event :new-player true)

            [{:eventName _ :level (_ :guard even?)}]
            (assoc event :start :even-level)

            [{:eventName _ :level (_ :guard #(= 0 (mod % 11)))}]
            (assoc event :level-type :extra-challenge)

            [{:eventName \"game.new.level\" :level _}]
            (assoc event :level-type :normal)))

  It implies a `:else` statement so you can't use one in yours.

  "
  {:style/indent 1}
  [event & body]
  `(let [_event# ~event]
     (match [_event#]
            ~@body
            :else nil)))



(def
  ^{:doc
    "Given a glob pattern it compiles it down to a regular expression
     which can be used with functions like `re-matches`, `re-find`, etc.
     To improve performances the function is `memoized` so common
     patterns are compiled only once."
    :arglists '([glob])}
  glob-pattern
  (memoize
   (fn [glob]
     (re-pattern
      (-> (str "^" glob "$")
          (s/replace "." "\\.")
          (s/replace "**" "[[::multi::]]")
          (s/replace "*"  "[[::single::]]")
          (s/replace "[[::multi::]]"  ".*")
          (s/replace "[[::single::]]" "[^.]*"))))))



(defn match-glob
  "Glob matching simplifies the event matching when names
  are in a dotted form. Allowed globs are:

  Dotted forms name are as follow:

    <segment>.<segment>.<...>.<segment>

    *  - single * matches any single segment
    ** - matches multiple segments

  For example:

    (match-glob \"game.*.started\"  \"game.level.started\")   => truthy
    (match-glob \"game.*.started\"  \"game.level.2.started\") => falsey
    (match-glob \"game.**.started\" \"game.level.2.started\") => truthy
    (match-glob \"game.**\"         \"game.level.5.stopped\") => truthy
    (match-glob \"game.**\"         \"game.anything.else\")   => truthy
    (match-glob \"game.**.ended\"   \"game.1.2.3.ended\")     => truthy

  "
  [glob name]
  (re-matches (glob-pattern glob) name))



;; import where
(import-vars [where.core where])



(defn moebius
  "It takes a list of functions transformation and produces a function
  which applied to a state and sequence of events will apply those
  transformations in the given order."
  [& fs]
  (partial cycler (apply pipeline fs)))
