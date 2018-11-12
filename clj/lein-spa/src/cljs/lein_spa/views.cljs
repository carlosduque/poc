(ns lein-spa.views
  (:require
   [re-frame.core :as re-frame]
   [lein-spa.subs :as subs]
   [lein-spa.events :as events]
   ))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]

     [:div
      [:a {:href "#/msg"}
       "go to Message Page"]]
     ]))


;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; message
(defn msg-input []
  [:div.msg-input
   "Message input: "
   [:form {:novalidate "" :role "form"}
     [:input {:type "text"
              :value @(re-frame/subscribe [::subs/msg])}]
     [:button
       {:on-click #(re-frame/dispatch [::events/msg-register (-> % .-target .-value)])}
       "Send"]]])



(defn msg-panel []
  [:div
   [:h1 "Register a message."]
   [msg-input]
   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :msg-panel [msg-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
