(ns printer.database-adapter
  (:require [yesql.core :refer [defqueries]]
            [jdbc.pool.c3p0 :as pool]
            [environ.core :refer [env]])
  (:import (java.net URI)))

(def db-uri (URI. (env :database-uri)))

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

(defn in_database [article]
  (let [hash (str (hash article))
        result (article-by-hash {:hash hash})]
    (if (> (count result) 0) true false )))

(defn insert_article [article]
  (if (in_database article) nil
    (create-article<! (conj article {:hash (str (hash article))}))))
