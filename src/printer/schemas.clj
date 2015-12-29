(ns printer.schemas
  (:require [abracad.avro :as avro]
            [abracad.avro.edn :as aedn]))

(def auto-schema (aedn/new-schema))

(def article-schema
  (avro/parse-schema
   {:type :record
    :name "LongList"
    :aliases ["LinkedLongs"]
    :fields [{:name "author", :type "string"}
             {:name "title", :type "string"}
             {:name "link", :type "string"}
             {:name "publication", :type "string"}
             {:name "publish_date", :type "long"}
             {:name "acquistion_date", :type "long"}]}))