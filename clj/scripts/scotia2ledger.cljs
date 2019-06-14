#!/usr/bin/env planck

;clojurescript
(ns ledger.core)

(require '[planck.core :refer [*in* slurp spit]]
         '[planck.io :refer [reader]])

(defn read-csv-file
  [filename]
  (with-open [reader (reader filename)]
    (doall
      (read-csv reader))))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data)
            (map keyword)
            repeat)
       (rest csv-data)))

(defn -main
  "Read a csv file"
  [& args]
  (println (csv-data->maps (read-csv-file (first args)))))
