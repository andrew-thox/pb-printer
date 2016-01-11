(ns printer.pail.extractors
  (:require [clj-thrift.base :as thrift]
            [clj-thrift.union :as tu]))

(defn property-union-value
  "get a value from a union, inside a struct inside a union.
   name is the property name inside the struct.
   Union -> Struct :<name> -> Union - value."
  [du name]
  (tu/current-value (thrift/value (tu/current-value du) name)))

(defn property-value [du name]
  "get the named field value from the current structure in the top level union.
   Union -> struct :<name> - value."
  (thrift/value (tu/current-value du) name))

(defn get-property-id [du]
  "get the id field from the current structure in the top level union."
  (property-value du :id))