(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]
            [hiccup.core :as hiccup]))

(defn slurp-file [filename]
  (slurp (io/resource filename)))

(defroutes app
  (GET "/" []
    (let [data (slurp-file "../resources/data.edn")]
      data))
  ;(GET "/" []
;    (hiccup/html
;      [:html
;        [:head
;          [:title "My blog running on Clojure!"]]
;        [:body
;          [:h1
;            (str "Welcome!")]]]))
  (route/not-found "Page not found"))

(defn -main []
  (run-server app {:port 12345}))
