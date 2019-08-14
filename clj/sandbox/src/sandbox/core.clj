(ns sandbox.core
  (:gen-class))

;; #28
(def nested1 '((1 2) 3 [4 [5 6]]))
(def nested2 ["a" ["b"] "c"])
(def nested3 '((((:a)))))

(defn lat
  [coll]
  (loop [acc '()
         xs coll]
    (cond
      (nil? xs) acc
      (seqable? (first xs)) (recur acc (first xs))
      :else (recur (conj acc (first xs)) (rest xs)))))


;; #30
(def mystr "Leeeerrroy")
(def myvec [1 1 2 3 3 2 2 3])
(def mytup [[1 2] [1 2] [3 4] [1 2]])

(#(reduce (fn [acc x]
           (if (not= (last acc) x)
              (conj acc x)
              acc))
         [] %) mystr)

;; #31
#(partition-by identity %)
