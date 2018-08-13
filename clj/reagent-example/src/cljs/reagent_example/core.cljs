(ns reagent-example.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to reagent-example"]
   [:div [:a {:href "/about"} "go to about page"]]
   [:div [:a {:href "/sh"} "go to say-hello page"]]
   [:div [:a {:href "/sp"} "go to simple-parent component"]]
   [:div [:a {:href "/lu"} "go to lister-user page"]]
   [:div [:a {:href "/tc"} "go to timer-component page"]]
   [:div [:a {:href "/c"} "go to counting-component page"]]])

(defn about-page []
  [:div [:h2 "About reagent-example"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn simple-parent []
  [:div
   [:p "I include simple-component."]
   [simple-component]])

(defn hello-component [name]
  [:p "Hello, " name "!"])

(defn say-hello []
  [hello-component "world"])

(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
  [:div
   "Here is a list:"
   [lister (range 3)]])

(defn timer-component []
  (let [seconds-elapsed (reagent/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 1000)
      [:div
       "Seconds Elapsed: " @seconds-elapsed])))

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn shared-state []
  (let [val (reagent/atom "foo")]
    (fn []
      [:div
       [:p "The value is now: " @val]
       [:p "Change it here: " [atom-input val]]])))

(defn render-simple []
  (reagent/render [simple-component]
                  (.-body js/document)))

;; -------------------------
;; Routes

(defonce page (atom #'home-page))

(def click-count (reagent/atom 0))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

(secretary/defroute "/sh" []
  (reset! page #'say-hello))
(secretary/defroute "/sp" []
  (reset! page #'simple-parent))
(secretary/defroute "/lu" []
  (reset! page #'lister-user))
(secretary/defroute "/tc" []
  (reset! page #'timer-component))
(secretary/defroute "/c" []
  (reset! page #'counting-component))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
