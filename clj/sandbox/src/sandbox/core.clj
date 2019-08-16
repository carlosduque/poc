(ns sandbox.core
  (:gen-class))

;; #28
(def n1 '((1 2) 3 [4 [5 6]]))
(def n2 ["a" ["b"] "c"])
(def n3 '((((:a)))))

(defn lat
  [coll]
  (loop [acc '()
         xs coll]
    (println "acc: " acc " xs: " xs)
    (cond
      (empty? xs) (reverse acc)
      (seqable? (first xs)) (recur acc (concat (first xs) (rest xs)))
      :else (recur (cons (first xs) acc) (rest xs)))))

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

