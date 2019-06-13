(ns scotia2ledger.core
  (require '[clojure.data.csv :as csv]
           '[clojure.java.io :as io])
  (:gen-class))

(defn 
(with-open [reader (io/reader "in-file.csv")]
  (doall
    (csv/read-csv reader)))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data) ;; First row is the header
            (map keyword) ;; Drop if you want string keys instead
            repeat)
       (rest csv-data)))

(csv-data->maps (read-csv reader))

(->> (read-csv reader)
     csv-data->maps
     (map (fn [csv-record]
            (update csv-record :bar #(Long/parseLong %)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (csv-data)))
