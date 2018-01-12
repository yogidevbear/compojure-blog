(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]))

(defroutes app
  (GET "/" [] "Hello World")
  (route/not-found "Page not found"))

(defn -main []
  (run-server app {:port 12345}))
