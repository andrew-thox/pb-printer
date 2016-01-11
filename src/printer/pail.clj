(ns printer.pail
  (:require [clj-pail.structure :refer [gen-structure]]
            [pail-thrift.serializer :as s]
            [pail-thrift.partitioner :as p]
            [printer.partitioner :as p2])
  (:import [people DataUnit])
  (:gen-class))

(gen-structure printer.DataUnitPailStructure
               :type DataUnit
               :serializer (s/thrift-serializer DataUnit)
               :partitioner (p2/union-name-property-partitioner DataUnit))