(ns printer.database-adapter
  (:require [yesql.core :refer [defqueries]]
            [jdbc.pool.c3p0 :as pool]
            [environ.core :refer [env]]
            [pandect.algo.sha256 :refer :all]
            [clj-statsd :as statsd])
  (:import (java.net URI)))

(def db-uri (URI. (env :database-uri)))

;TODO: This should log/alert
(try
  (statsd/setup (env :statd-host) (env :statd-port))
  (catch Exception e (str "caught exception: " (.getMessage e))))

(def user-and-password
  (if (nil? (.getUserInfo db-uri)) nil (clojure.string/split (.getUserInfo db-uri) #":")))

(def spec
  (pool/make-datasource-spec
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :user (get user-and-password 0)
     :password (get user-and-password 1)
     :subname (if (= -1 (.getPort db-uri))
                (format "//%s%s" (.getHost db-uri) (.getPath db-uri))
                (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))}))

(defqueries "queries/articles.sql"
  {:connection spec})

(defn in_database [hash]
  (let [result (article-by-hash {:hash hash})]
    (if (> (count result) 0) true false )))

(defn consume-article [article]
  (let [hash (str (sha256 (clojure.string/join " " [(:author article) (:title article) (:link article)])))]
    (if (in_database hash) nil
      (do
        (create-article<! (conj article {:hash hash}))
        (statsd/increment (clojure.string/lower-case (str (:publication article) "_articles_processed")))
        (statsd/increment "articles_processed")))))
