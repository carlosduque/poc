(ns cbt.core-test
  (:require [expectations :refer :all]
            [cbt.core :refer :all]))

;; An empty string *should* return *0*
(expect 0 (sum-a-string ""))
