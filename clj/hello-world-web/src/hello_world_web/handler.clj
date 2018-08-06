(ns hello-world-web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/user/:number{[0-9]+}" [number]
       (str "<h1>Hello World: " number "</h1>"))
  ;;curl http://localhost:3000/user/carlos?greeting=hola
  (GET "/user/:name" [name greeting]
       (str "<h1>" greeting " " name "</h1>"))
  (GET "/whats-my-ip" [request]
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (:remote-addr request)})
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
