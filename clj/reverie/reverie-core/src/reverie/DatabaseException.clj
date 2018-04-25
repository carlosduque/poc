(ns reverie.DatabaseException
  (:gen-class
   :extends java.lang.Exception
   :implements [clojure.lang.IDeref]
   :init init
   :state state
   :constructs {[String] [String]}))


(defn -init [message]
  [[message] [message]])

(defn -deref [this]
  (.state this))
