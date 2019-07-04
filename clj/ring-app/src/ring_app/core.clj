(ns ring-app.core
  (:require
    [ring.adapter.jetty :as jetty]))

;Handlers
(defn handler
  ;synchronous handler: one-argument
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

  ;asynchronous handler: three-argument
  ;[request respond raise]
  ;(respond (what-is-my-ip request))
  ;)

;middleware
;higher-level functions adding func to handlers
;first-arg is a handler, the return value is a new handler that calls the original handler


;helper
(defn content-type-response [response content-type]
  (assoc-in response [:headers "Content-Type"] content-type))

;actual middleware handling sync/async
(defn wrap-content-type
  [handler content-type]
  (fn
    ([request]
     ;synchronous handler: one-argument
     (-> (handler request) (content-type-response content-type)))
    ([request respond raise]
     ;asynchronous handler: three-argument
     (handler request #(respond (content-type-response % content-type)) raise))))


;using the handler
(def app (wrap-content-type handler "text/html"))

;(def app
;  (-> handler
;      (wrap-content-type "text/html")
;      (wrap-keyword-params)
;      (wrap-params)))
(defn main-handler
  [request]
  (cond
    (= "/" (:uri request))
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body "<h1>Hello World</h1>"}
    (= "/ip" (:uri request))
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (str "<h1>" (:remote-addr request) "</h1>")}
    (= "/x" (:uri request))
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body "<h1>Hello World</h1>"}
    :else
      {:status 404
       :headers {"Content-Type" "text/html"}
       :body "<h2>Danger Will Robinson</h2>You are in the <em>wrong</em> place"}))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty main-handler {:port (Integer/valueOf port-number)}))

