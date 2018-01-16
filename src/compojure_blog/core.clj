(ns compojure-blog.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [hiccup.core :as hiccup]))

(defn slurp-file [filename]
  (slurp (io/resource filename)))

(defn html-head [attributes]
  "Takes a hash-set of attributes for elements within in html <head> tag"
  (hiccup/html
    [:head
      (if (:head-title attributes)
        [:title (:head-title attributes)]
        [:title "My blog running on Clojure!"])
      (if (:head-link-css attributes)
        (for [css (:head-link-css attributes)]
          [:link {:rel "stylesheet" :href css}]))]))

(defn html-nav []
  "Outputs the page navigation"
  (hiccup/html
    [:nav
      [:a {:href "/"} "Home"]]))

(defroutes app
  (GET "/" []
    (let [data (slurp-file "../resources/data.edn")]
      (hiccup/html
        [:html
          (html-head {})
          [:body
            (html-nav)
            [:h1 "Welcome"]
            [:ul
              (for [category (:categories (edn/read-string data))]
                [:li category])]
            (for [post (:posts (edn/read-string data))]
              [:div
                [:h2
                  [:a {:href (str "/post/" (:id post))}
                    (:title post)]]
                [:p (:excerpt post)]
                [:p
                  [:a {:href (str "/post/" (:id post))}
                    "Read more..."]]])]])))
  (GET "/post/:id" [id]
    (let [data (slurp-file "../resources/data.edn")]
      (let [post (first (filter #(= id (:id %)) (:posts (edn/read-string data))))]
        (hiccup/html
          (html-head {:head-title (:title post) :head-link-css ["/main.css"]})
          [:body
            (html-nav)
            [:h1 (:title post)]
            [:div (:content post)]]))))
  (route/resources "/")
  (route/not-found "Page not found"))

(defn -main []
  (run-server app {:port 12345}))
