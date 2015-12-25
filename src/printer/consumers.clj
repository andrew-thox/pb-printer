(ns printer.consumers
  (:require [printer.database-adapter :as dba]))

(defn article-consumer
  [ch {:keys [content-type delivery-tag] :as meta} ^bytes payload]
  (dba/consume-article payload))
