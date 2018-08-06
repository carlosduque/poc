(ns sandbox.core)

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

