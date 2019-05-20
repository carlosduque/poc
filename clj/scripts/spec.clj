(ns sandbox.spec
  (:require [clojure.spec.alpha :as s]))

;predicates
(s/conform even? 1000)
(s/valid? even? 10)
(s/valid? string? "abc")
(s/valid? #(> % 5) 10)

(import java.util.Date)
(s/valid? inst? (Date.))

(s/valid? #{:club :diamond :heart :space} :club)
(s/valid? #{:club :diamond :heart :space} 42)

;registry
(s/def ::date inst?)
(s/def ::suit #{:club :diamond :heart :spade})

(s/valid? ::date (java.util.Date.))
(s/conform ::suit :club)

;composed
(s/def ::big-even (s/and int? even? #(> % 1000)))
(s/valid? ::big-even :foo)
(s/valid? ::big-even 10)
(s/valid? ::big-even 100000)

(s/def ::name-or-id (s/or :name string?
                          :id   int?))
(s/valid? ::name-or-id "abc")
(s/valid? ::name-or-id 100)
(s/valid? ::name-or-id :foo)

(s/conform ::name-or-id "abc")
(s/conform ::name-or-id 100)

;explain
(s/explain ::suit 42)
(s/explain ::big-even 5)
(s/explain ::name-or-id :foo)
