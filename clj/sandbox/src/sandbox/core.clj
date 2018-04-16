(ns sandbox.core)

;;anonymous function for creating a set
((fn [x y]
  (println "Making a set")
  #({ x y})) 45 78)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

;; blocks
(do
  (def x 5)
  (def y 4)
  (+ x y)
  [x y])

;; locals 
(let [r      5
      pi     3.1415
      r-squared (* r r)]
  (println "radius is" r)
  (* pi r-squared))

;; recursion
(defn print-down-from [x]
  (when (pos? x)
    (println x)
    (recur (dec x))))

(print-down-from 7)

;; with an accumulator
(defn sum-down-from [sum x]
  (if (pos? x)
    (recur (+ sum x) (dec x))
    sum))

(sum-down-from 0 9)

;; with loop
(defn sum-down-from-x [initial-x]
  (loop [sum 0, x initial-x]
    (if (pos? x)
      (recur (+ sum x) (dec x))
      sum)))

(sum-down-from-x 9)

;; nil pun: empty collections are true, but using _seq_ on the collection
;; returns false
(seq [1 2 3])

(seq [])

(defn print-seq [s]
  (when (seq s)
    (prn (first s))
    (recur (rest s))))

(print-seq [])

(print-seq [1 2])

;; destructuring
(def guys-whole-name ["Guy" "Lewis" "Steele"])

(str (nth guys-whole-name 2) ", "
     (nth guys-whole-name 0) " "
     (nth guys-whole-name 1))

;; with let
(let [[f-name m-name l-name] guys-whole-name]
    (str l-name ", " f-name " " m-name))

(let [[a b c & more] (range 10)]
  (println "a b c are:" a b c)
  (println "more is:" more))

(let [range-vec (vec (range 10))
      [a b c & more :as all] range-vec]
  (println "a b c are:" a b c)
  (println "more is:" more)
  (println "all is:" all))

;; destructuring maps
(def guys-name-map
  {:f-name "Guy" :m-name "Lewis" :l-name "Steele"})

(let [{f-name :f-name, m-name :m-name, l-name :l-name} guys-name-map]
  (str l-name ", " f-name " " m-name))

(let [{:keys [f-name m-name l-name]} guys-name-map]
  (str l-name ", " f-name " " m-name))

(let [{f-name :f-name, :as whole-name} guys-name-map]
  (println "First name is" f-name)
  (println "Whole name is below:")
  whole-name)

(let [{:keys [title f-name m-name l-name],
       :or {title "Mr."}} guys-name-map]
  (println title f-name m-name l-name))

(defn whole-name [& args]
  (let [{:keys [f-name m-name l-name]} args]
    (str l-name ", " f-name " " m-name)))

(whole-name :f-name "Guy" :m-name "Lewis" :l-name "Steele")

;;interop
(defn xors [max-x max-y]
  (for [x (range max-x) y (range max-y)]
    [x y (bit-xor x y)]))

(xors 2 2)

(def frame (java.awt.Frame.))
(.setVisible frame true)
(.setSize frame (java.awt.Dimension. 200 200))
(def gfx (.getGraphics frame))
;;(.fillRect gfx 100 100 50 75)
;;(.setColor gfx (java.awt.Color. 255 128 0))
(doseq [[x y xor] (xors 200 200)]
  (.setColor gfx (java.awt.Color. xor xor xor))
  (.fillRect gfx x y 1 1))

(def a 1.0e50)
(def b -1.0e50)
(def c 17.0e00)

(+ (+ a b) c)
(+ a (+ b c))

(def aa (rationalize 1.0e50))
(def bb (rationalize -1.0e50))
(def cc (rationalize 17.0e00))

(+ (+ aa bb) cc)
(+ aa (+ bb cc))

;;keywords
(def population {:zombies 2700 :humans 9})
(get population :zombies)
(:humans population)

(defn pour [lb ub]
  (cond
    (= ub :toujours) (iterate inc lb)
    :else (range lb ub)))

(pour 1 10)

(take 5 (pour 1 :toujours))

;namespace
(defn do-blowfish [directive]
  (case directive
    :aquarium/blowfish  (println "feed the fish")
    :crypto/blowfish    (println "encode the message")
    :blowfish           (println "not sure what to do")))

(ns crypto)
(sandbox.core/do-blowfish :blowfish)

(sandbox.core/do-blowfish ::blowfish)

(ns aquarium)
(sandbox.core/do-blowfish :blowfish)

(sandbox.core/do-blowfish ::blowfish)

;regex
(re-seq #"\w+"     "one-two/three")
(re-seq #"\w*(\w)" "one-two/three")

;collection types
(def ds [:willie :barnabas :adam])
ds

(def ds1 (replace {:barnabas :quentin} ds))
ds1
ds

(class (hash-map :a 1))
(seq (hash-map :a 1))
(class (seq (hash-map :a 1)))
(seq (keys (hash-map :a 1)))
(class (keys (hash-map :a 1)))

(vec (range 10))

(let [my-vector [:a :b :c]]
  (into my-vector (range 10)))

(into (vector-of :int) [Math/PI 2 1.3])
(into (vector-of :char) [100 101 102])

(def a-to-j (vec (map char (range 65 75 ))))
a-to-j

(nth a-to-j 4)
(get a-to-j 4)
(a-to-j 4)
(seq a-to-j)
(rseq a-to-j)
(assoc a-to-j 4 "no longer E")

(replace {2 :a 4 :b} [1 2 3 2 3 4])

(def matrix
  [[1 2 3]
   [4 5 6]
   [7 8 9]])
(get-in matrix [1 2])
(assoc-in matri [1 2] 'x)
(update-in matrix [1 2] * 100)

(defn neighbors
  ([size yx] (neighbors [[-1 0] [1 0] [0 -1] [0 1]]
                        size
                        yx))
  ([deltas size yx]
   (filter (fn [new-yx]
             (every? #(< -1 % size) new-x))
           (map #(vec (map + yx %))
                deltas))))

(neighbors 3 [0 0])

;; vectors as stacks
(def my-stack [1 2 3])
(peek my-stack)
(pop my-stack)
(conj my-stack 4)
(+ (peek my-stack) (peek (pop my-stack)))

(subvec a-to-j 3 6)

(first {:width 10 :height 20 :depth 15})
(vector? (first {:width 10 :height 20 :depth 15}))

(doseq [[dimenson amount] {:width 10 :height 20 :depth 15}]
  (println (str (name dimension) ":") amount "inches"))

;; lists
(cons 1 '(2 3))
(conj '(2 3) 1)

;;queues
;;; first provide a multimethod for pretty printing
(defmethod print-method clojure.lang.PersistentQueue
  [q w]
  (print-method '<- w)
  (print-method (seq q) w)
  (print-method '-< w))

(def schedule
  (conj clojure.lang.PersintentQueue/EMPTY
        :wake-up :shower :brush-teeth))

(peek schedule)
(pop schedule)
(rest schedule)

;; sets
(#{:a :b :c :d} :c)

(#{:a :b :c :d} :e)

(get #{:a 1 :b 2} :b)
(get #{:a 1 :b 2} :z :nothing-doing)

(into #{[]} [()])
(into #{[1 2]} '[(1 2)])

(some #{:b} [:a 1 :b 2])
(some #{1 :b} [:a 1 :b 2])

(sorted-set :b :c :a)
(sorted-set [3 4] [1 2])

(require 'clojure.set))
;(ns my.cool.lib
;  (:require clojure.set))

(clojure.set/intersection #{:humans :fruit-bats :zombies}
                          #{:chupacabra :zombies :humans})

(clojure.set/intersection #{:pez :gum :dots :skor}
                          #{:pez :skor :pocky}
                          #{:pocky :gum :skor})

(clojure.set/union #{:humans :fruit-bats :zombies}
                   #{:chupacabra :zoies :humans})

(clojure.set/union #{:pez :gum :dots :skor}
                   #{:pez :skor :pocky}
                   #{:pocky :gum :skor})

(clojere.set/difference #{1 2 3 4} #{3 4 5 6})

;;maps
(hash-map :a 1, :b 2, :c 3, :d 4, :e )
(let [m {:a 1 1 :b [1 2 3] "4 5 6"}]
  [(get m :a) (get m [1 2 3])])

(seq {:a 1 :b 2})
(into {} (map vec '[(:a 1) (:b 2)]))

(apply hash-map [:a 1 :b 2])
(zipmap [:a :b] [1 2])

(sorted-map :thx 1138 :r2d 2)
(sorted-map "bac" 2 "abc" 9)
(sorted-map-by #(compare (subs 1 1) (sus %2 1)) "bac" 2 "abc" 9)

(assoc {1 :int} 1.0 :float)

;;keeping order
(seq (hash-map :a 1 : 2 :c 3))
(seq (array-map :a 1 : 2 :c 3))

(defn index [coll]
  (cond
    (map? coll) (seq coll)
    (set? coll) (map vector coll coll)
    :else (map vector (iterate inc 0) coll)))

(index [:a 1 :b 2 .c 3 :d 4])
(index {:a 1 :b 2 .c 3 :d 4})
(index #{:a 1 :b 2 :c 3 :d 4})

(defn pos [e coll]
  (for [[i v] (index coll) :whn (= e v)] i))

(defn pos [pred coll]
  (for [[i v] (index coll) :when (pred v)] i))

;;functional programming techniques
;;structural sharing
(def baselist (list :barnabas :adam))
(def lst1 (cons :willie baselist))
(def lst2 (cons :phoeninx baselist))
lst1
lst2
(= (next lst1) (next lst2))
(identical? (next lst1) (next lst2))

(defn xconj [t v]
  (cond
        (nil? t) {:val v :L nil :R nil}
        (< v (:val t)) {:val (:val t)
                        :L (xconj (:L t) v)
                        :R (:R t)}
        :else {:val (:val t),
               :L (:L t),
               :R (xconj (:R t) v)}))

(def tree1 (xconj nil 5))
(tree1)
(def tree1 (xconj tree1 3))
(tree1)
(def tree1 (xconj tree1 2))

(defn xseq [t]
  (when t
    (concat (xseq (:L t)) [(:val t)] (xseq (:R t)))))
(xseq tree1)

(def tree2 (xconj tree1 7))
(xseq tree2)
(identical? (:L tree1) (:L tree2))

;; lazyness
(defn lz-rec-step [s]
  (lazy-seq
            (if (seq s)
              [(first s) (lz-rec-step (rest s))]
              [])))

(lz-rec-step [1 2 3 4]))
(class (lz-rec-step [1 2 3 4]))

(defn triangle [n]
  (/ (* n (+ n 1)) 2))

(triangle 10)

(map triangle (range 1 11))

(def tri-nums (map triangle (iterate inc 1)))
(take 10 tri-nums)
(take 10 (filter even? tri-nums))
(nth tri-nums 99)
(double (reduce + (take 1000 (map / tri-nums))))
(take 2 (drop-while #(< % 10000) tri-nums))

;; quicksort
(defn rand-ints [n]
  (take n (repeatedly #(rand-int n))))

(rand-ints 10)

(defn sort-parts [work]
  (lazy-seq
            (loop [[part & parts] work] 
              (if-let [[pivot & xs] (seq part)]
                (let [smaller? #(< % pivot)]
                  (recur (list*
                               (filter smaller? xs)
                               pivot
                               (remove smaller? xs)
                               parts)))
                (when-let [[x & parts] parts]
                  (cons x (sort-parts parts)))))))

(defn qsort [xs]
  (sort-parts (list xs)))

(qsort [2 1 4 3])
(qsort (rand-ints 20))

;;continue on chap7 p136

(map [:chthon :phthor :beowulf :grendel] #{0 3})

;; compose functions
(def fifth (comp first rest rest rest rest))
(fifth [1 2 3 4 5])

(defn fnth [n]
  (apply comp
         (cons first
               (take (dec n) (repeat rest)))))

((fnth 5) '[a b c d e])

(map (comp
       keyword
       #(.toLowerCase %)
       name)
     '(a B C))

((partial + 5) 100 200)

;; reverse truth with complement
(let [truthiness (fn [v] v)]
  [((comlpement truthiness) true)
   ((complement truthiness) 42)
   ((complement truthiness) false)
   ((complement truthiness) nil)])

((complement even?) 2)

;; function as data
(defn join
  {:test (fn []
           (assert
             (= (join "," [1 2 3]) "1,3,3")))}
  [sep s]
  (apply str (interpose sep s)))

;;shorthand version for metadata
(defn ^:private ^:dynamic sum [nums]
  (map + nums))

;;functions as arguments
(sort [1 5 7 0 -42 13])
(sort ["z" "x" "a" "aa"])
(sort [(java.util.Date.) (java.util.Date. 100)])
(sort [[1 2 3] [-1 0 1] [3 2 1]])
(sort > [7 1 4])
;;will throw a cast class exception
(sort ["z" "x" "a" "aa" 1 5 8])

(sort [{:age 99} {:age 13} {:age 7}])

(sort-by second [[:a 7] [:c 13] [:b 21]])
(sort-by str ["z" "x" "a" "aa" 1 5 8])
(sort-by :age [{:age 99} {:age 13} {:age 7}])

(def plays [{:band "Burial"     :plays 979  :loved 9}
            {:band "Eno"        :plays 2333 :loved 15}
            {:band "Bill Evans" :plays 979  :loved 9}
            {:band "Magma"      :plays 2665 :loved 31}])

(def sort-by-loved-ratio (partial sort-by #(/ (:plays %) (:loved %))))
(sort-by-loved-ration plays)

;;functions as return values
(sort-by (columns [:plays :loved :band]) plays)

(defn columns [column-names]
  (fn [row]
    (vec (map row column-names))))

(columns [:plays :loved :band])

((columns [:plays :loved :band])
 {:band "Burial" :plays 979 :loved 9})

;;continue on page 144
