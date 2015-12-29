(ns printer.consumers
  (:require [printer.database-adapter :as dba]
            [printer.schemas :as schemas]
            [abracad.avro :as avro]))

(defn article-consumer
  [ch {:keys [content-type delivery-tag] :as meta} ^bytes payload]
  (let [article (->> payload (avro/decode schemas/auto-schema))]
    (dba/consume-article article)))
