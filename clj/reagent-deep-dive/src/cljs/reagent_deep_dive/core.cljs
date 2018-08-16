(ns reagent-deep-dive.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to reagent-deep-dive"]
   [:div [:a {:href "/simple-button"} "go to simple-button page"]]
   [:div [:a {:href "/simple-form"} "go to simple-form page"]]
   [:div [:a {:href "/concentric-circles"} "go to concentric-circles page"]]
   [:div [:a {:href "/many-circles"} "go to many-circles page"]]
   [:div [:a {:href "/counter"} "go to counter page"]]
   [:div [:a {:href "/waldo"} "go to waldo page"]]
   [:div [:a {:href "/reaction"} "go to reaction (sorting as reaction) page"]]
   [:div [:a {:href "/func-ret-func"} "go to func-ret-func (mousetraps) page"]]
   [:div [:a {:href "/with-let"} "go to with-let page"]]
   ])

(defn simple-button []
  [:div#my-button.my-class1.my-class2 [:h2 "reagent-deep-dive:simple-button"]
   [:div [:button {:on-click
                   (fn [e]
                     (js/alert "You pressed the button!"))}
          "Do not press"]]])

(defn simple-form []
  [:div
   [:h3 "Greetings human"]
   [:form
    {:on-submit
     (fn [e]
       (.preventDefault e)
       (js/alert
         (str "You said: " (.. e -target -elements -message -value))))}
    [:label
     "Say something:"
     [:input
      {:name "message"
       :type "text"
       :default-value "Hello"}]]
    [:input {:type "submit"}]]])

(defn concentric-circles []
  [:svg {:style {:boder "1px solid"
                 :background "white"
                 :width "150px"
                 :height "150px"}}
   [:circle {:r 50, :cx 75, :cy 75, :fill "green"}]
   [:circle {:r 25, :cx 75, :cy 75, :fill "blue"}]
   [:path {:stroke-width 12
           :stroke "white"
           :fill "none"
           :d "M 30,40 C 100,40 50,110 120,110"}]])

(defn many-circles []
  (into
    [:svg {:style {:border "1px solid"
                   :background "white"
                   :width "600px"
                   :height "600px"}}]
    (for [i (range 12)]
      [:g
       {:transform (str
                     "translate(300,300) "
                     "rotate(" (* i 30) ") "
                     "translate(100)")}
       [concentric-circles]])))

(def c (reagent/atom 1))
(defn counter []
  [:div
   [:div "Current counter value: " @c]
   [:button
    {:disabled (>= @c 4)
     :on-click
     (fn clicked [e]
       (swap! c inc))}
    "inc"]
   [:button
    {:disabled (<= @c 1)
     :on-click
     (fn clicked [e]
       (swap! c dec))}
    "dec"]
   (into [:div] (repeat @c [concentric-circles]))])

(defn waldo []
   (let [show? (reagent/atom false)]
     (fn w []
       [:div
        (if @show?
          [:div
           [:h3 "You found me!"]
           [:img
            {:src "https://goo.gl/EzvMNp"
             :style {:height "320px"}}]]
          [:div
           [:h3 "Where are you now?"]
           [:img
            {:src "https://i.ytimg.com/vi/HKMlPDwmTYM/maxresdefault.jpg"
             :style {:height "320px"}}]])
        [:button
         {:on-click
          (fn [e]
            (swap! show? not))}
         (if @show? "reset" "search")]])))

(def rolls (reagent/atom [1 2 3 4]))
(def sorted-rolls (reagent.ratom/reaction (sort @rolls)))
(defn sorted-d20 []
  [:div
   [:button {:on-click (fn [e] (swap! rolls conj (rand-int 20)))} "Roll!"]
   [:p (pr-str @sorted-rolls)]
   [:p (pr-str (reverse @sorted-rolls))]])

;;part II
(defn a-better-mouse-trap [mouse]
  (let [mice (reagent/atom 1)]
    (fn render-mouse-trap [mouse]
      (into
        [:div
         [:button
          {:on-click
           (fn [e]
             (swap! mice (fn [m] (inc (mod m 4)))))}
          "Catch!"]]
        (repeat @mice mouse)))))
(defn mouse-trap []
  [:div
   [a-better-mouse-trap
    [:img
     {:src "https://www.domyownpestcontrol.com/images/content/mouse.jpg"
      :style {:width "150px" :border "1px solid"}}]]
   [a-better-mouse-trap
    [:img
     {:src "https://avatars1.githubusercontent.com/u/9254615?v=3&s=150"
      :style {:border "1px solid"}}]]])

(defn lambda [rotation x y]
  [:g {:transform (str "translate(" x "," y ")"
                       "rotate(" rotation ") ")}
   [:circle {:r 50, :fill "green"}]
   [:circle {:r 25, :fill "blue"}]
   [:path {:stroke-width 12
           :stroke "white"
           :fill "none"
           :d "M -45,-35 C 25,-35 -25,35 45,35 M 0,0 -45,45"}]])

(defn spinnable []
  (reagent/with-let [rotation (reagent/atom 0)]
    [:svg
     {:width 150 :height 150
      :on-mouse-move
      (fn [e]
        (swap! rotation + 30))}
     [lambda @rotation 75 75]]))

(defn several-spinnables []
  [:div
   [:h3 "Move your mouse over me"]
   [a-better-mouse-trap [spinnable]]])

;; -------------------------
;; Routes
(defonce page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/simple-button" []
  (reset! page #'simple-button))

(secretary/defroute "/simple-form" []
  (reset! page #'simple-form))

(secretary/defroute "/concentric-circles" []
  (reset! page #'concentric-circles))

(secretary/defroute "/many-circles" []
  (reset! page #'many-circles))

(secretary/defroute "/counter" []
  (reset! page #'counter))

(secretary/defroute "/waldo" []
  (reset! page #'waldo))

(secretary/defroute "/reaction" []
  (reset! page #'sorted-d20))

;;part II
(secretary/defroute "/func-ret-func" []
  (reset! page #'mouse-trap))

(secretary/defroute "/with-let" []
  (reset! page #'several-spinnables))
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
