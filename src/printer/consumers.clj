(ns printer.consumers
  (:require [clj-json.core :as json]
            [printer.database-adapter :as dba]))

(defn article-consumer
  [ch {:keys [content-type delivery-tag] :as meta} ^bytes payload]
  (let [article (json/parse-string (String. payload) true)]
    (dba/insert_article article)))
