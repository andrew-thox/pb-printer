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

(def article
  (avro/parse-schema
    {:type :record
     :name "Article"
     :fields [{:name "id", :type "string"}]}))

(def article-property-value
  (avro/parse-schema
    {:type :record
     :name "ArticlePropertyValue"
     :fields [{:name "title" :type ["string", :null]}
              {:name "publication_date" :type ["long", :null]}
              {:name "external_unique_identifier" :type ["string", :null]}
              {:name "internal_unique_identifier" :type ["string", :null]}
              {:name "url" :type ["string", :null]}
              {:name "text" :type ["bytes", :null]}]}))

(def article-property
  (avro/parse-schema
    {:type :record
     :name "ArticleProperty"
     :fields [{:name "article" :type "Article"}
              {:name "article_property_value" :type "ArticlePropertyValue"}]}))

(def data-unit
  (avro/parse-schema
    {:type :record
     :name "DataUnit"
     :fields [{:name "article_property" :type "ArticleProperty"}]}))

;TODO: Add source to pedigree
(def pedigree
  (avro/parse-schema
    {:type :record
     :name "Pedigree"
     :field [{:name "timestamp" :type "long"}]}))

(def data
  (avro/parse-schema
    {:type :record
     :name "Data"
     :fields [{:name "data_unit" :type "DataUnit"}
              {:name "pedigree" :type "Pedigree"}]}))