(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]))

(defroutes app
  (GET "/" [] "Hello World"))

(defn -main []
  (run-server app {:port 12345}))
