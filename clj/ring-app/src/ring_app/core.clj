(ns ring-app.core)

;Handlers
(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn what-is-my-ip
  ;synchronous handler: one-argument
  [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (:remote-addr request)}

  ;asynchronous handler: three-argument
  ;[request respond raise]
  ;(respond (what-is-my-ip request))
  )

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
     (-> (handler request) (content-type-response content-type)))
    ([request respond raise]
     (handler request #(respond (content-type-response % content-type)) raise))))


;using the handler
(def app (wrap-content-type handler "text/html"))

;(def app
;  (-> handler
;      (wrap-content-type "text/html")
;      (wrap-keyword-params)
;      (wrap-params)))
