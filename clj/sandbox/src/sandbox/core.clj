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
