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

(defn get-document-delay
  [url]
  (let [url url
        id (md5 url)] ;; should really hash the contents, not the url :-)
    {:id id
     :url url
    :mime "text/plain"
    :content (delay (slurp url))}))

(defn get-document-future
  [url]
  (let [url url
        id (md5 url)] ;; should really hash the contents, not the url :-)
    {:id id
     :url url
    :mime "text/plain"
    :content (future (slurp url))}))

(defn get-document
  [url]
  (let [url url
        id (md5 url)] ;; should really hash the contents, not the url :-)
    {:id id
     :url url
    :mime "text/plain"
    :content (slurp url)}))

(defn deref-val
  [value]
  (println "value" @value))


(with-new-thread (get-document-promise url))

;;futures
(def f (get-document-future url))
(realized? (:content f))
@(:content f) ;; the contents of the book should already be there!

;;promises
(def p (promise))
(realized? p)
(with-new-thread (deliver p (get-document url)))
(:content p)

;;delays
(def d (get-document-delay url))
(realized? (:content d))
@(:content d) ;; will get the contents of the book now!
(realized? (:content d))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
