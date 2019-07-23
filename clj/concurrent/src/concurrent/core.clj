(ns concurrent.core
  (:import java.security.MessageDigest
           java.math.BigInteger)
  (:gen-class))

(defmacro with-new-thread [& body]
  `(.start (Thread. (fn [] ~@body))))

(defn now []
  (str (java.time.Instant/now)))

(defn gen-uuid []
  (str (java.util.UUID/randomUUID)))

(defn md5
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn complex-job
  [id sent-at]
  (let [duration (* 1000 (+ 1 (rand-int 7)))
        started (now)]
    (Thread/sleep duration)
    {:id id
     :sent-at sent-at
     :started started
     :end   (now)
     :duration duration}))

(def url "http://www.gutenberg.org/cache/epub/103/pg103.txt")

(defn get-document
  [url]
  (let [url url
        id (md5 url)]
    {:id id
     :url url
    :mime "text/plain"
    :content (delay (slurp url))}))

(defn deref-val
  [value]
  (println "value" @value))

(with-new-thread (get-document url))

(with-new-thread (complex-job gen-uuid now))

(def thread (Thread. (complex-job gen-uuid now)))
(.start thread)

;;futures
(def f (let [id (gen-uuid)
             sent-at (now)]
         (future (complex-job "future" sent-at))))

;;promises
(def p (promise))

(doto (Thread. (fn []
                 (complex-job )
                 (deliver p 42)))
               .start)

;;delays
(def d (let [id (gen-uuid)
             sent-at (now)]
         (delay (complex-job "delay" sent-at))))

;;realize values
(deref-val t)
(deref-val f)
(deref-val p)
(deref-val d)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
