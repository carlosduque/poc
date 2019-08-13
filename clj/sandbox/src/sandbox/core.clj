(ns sandbox.core
  (:gen-class))

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
