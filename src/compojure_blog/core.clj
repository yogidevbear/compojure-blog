(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup]))

(defroutes app
  (GET "/" []
    (hiccup/html
      [:html
        [:head
          [:title "My blog running on Clojure!"]]
        [:body
          [:h1
            (str "Welcome!")]]]))
  (route/not-found "Page not found"))

(defn -main []
  (run-server app {:port 12345}))
