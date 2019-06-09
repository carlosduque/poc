(ns work_threads.core)

(def queue (atom clojure.lang.PersistentQueue/EMPTY))
(swap! queue conj :a)
