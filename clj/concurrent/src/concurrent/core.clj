(ns concurrent.core
  (:gen-class))

(defn now []
  (str (java.time.Instant/now)))

(defn gen-uuid []
  (str (java.util.UUID/randomUUID)))

(defn complex-job
  [id sent-at]
  (let [duration (* 1000 (+ 1 (rand-int 7)))
        started (now)]
    (Thread/sleep duration)
    {:id id
     :sent-at sent-at
     :started started
     :end   (now)
     :duration duration}))

(defn deref-val
  [value]
  (println "value" @value))

(def thread (Thread. (complex-job gen-uuid now)))
(.start thread)

;;futures
(def f (let [id (gen-uuid)
             sent-at (now)]
         (future (complex-job "future" sent-at))))

;;promises
(def p (promise))

(doto (Thread. (fn []
                 (complex-job )
                 (deliver p 42)))
               .start)

;;delays
(def d (let [id (gen-uuid)
             sent-at (now)]
         (delay (complex-job "delay" sent-at))))

;;realize values
(deref-val t)
(deref-val f)
(deref-val p)
(deref-val d)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
