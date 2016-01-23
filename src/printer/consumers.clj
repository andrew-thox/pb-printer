(ns printer.consumers
  (:require [printer.titan-adapter :as dba]
            [printer.avro :as schemas]
            [abracad.avro :as avro]))

(defn article-consumer
  [ch {:keys [content-type delivery-tag] :as meta} ^bytes payload]
  (let [article (->> payload (avro/decode schemas/auto-schema))]
    (println article)))
