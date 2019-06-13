#!/usr/bin/env planck

;clojurescript
(require '[planck.core :refer [*in* slurp spit]])

(slurp "./books.csv")
(spit "./math.txt" (+ 3 4 6 7 8))

(pr (slurp *in*))

(def data (slurp "https://swapi.co/api/people/"))
(.parse js/JSON data)
(js->clj (.parse js/JSON data) :keywordize-keys true)

;in one line
(-> "https://swapi.co/api/people/"
    slurp
    JSON.parse
    (js-clj :keywordize-keys true))
