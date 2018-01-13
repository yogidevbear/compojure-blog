(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [hiccup.core :as hiccup]))

(defn slurp-file [filename]
  (slurp (io/resource filename)))

(defroutes app
  (GET "/" []
    (let [data (slurp-file "../resources/data.edn")]
      ;(select-keys (edn/read-string data) [:categories]))
      ;(select-keys (edn/read-string data) [:authors]))
      (hiccup/html
        [:html
          [:head
            [:title "My blog running on Clojure!"]]
          [:body
            [:h1 "Welcome"]
            [:p (str (select-keys (edn/read-string data) [:categories]))]
            [:ul
              (for [category (select-keys (edn/read-string data) [:categories])]
                [:li category])]]])))
  (route/not-found "Page not found"))

(defn -main []
  (run-server app {:port 12345}))
