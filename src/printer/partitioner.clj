(ns printer.partitioner
  (:require [clj-pail.partitioner :as p]
            [clj-thrift.union :as union]
            [clj-thrift.base :as thrift]
            [clj-thrift.type :as type]))

(defrecord ^{:doc "A 2 level pail partitioner for Thrift unions. It requires a type, which must be a subtype
                  of `TUnion`. The partitioner will partition based on the union's set field name so that
                  all union values with the same field will be placed in the same partition. If a field's
                  name is property or ends in property or Property the partitioner will also partition
                  by the union found in the :property field of that structure.
                  Union PropertyValue {
                     1: name;
                     2: lastname;
                  }
                  Struct PersonProperty {
                     1: string id;
                     2: PropertyValue property;    /* <--- this name 'property' is required. it is the subunion */
                  }
                  Union DataUnit {
                     1: PersonProperty property;
                     2: string Things;
                  }
                  Partitioning DataUnit will result in /property/name, /property/lastname, and /Things as the partitions."}

  UnionNamePropertyPartitioner
  [type]


  p/VerticalPartitioner
  (p/make-partition
    [this object]
    (let [res (vector (union/current-field-name object))]
      (if (re-find #"^.*[Pp]roperty$" (first res))
        (let [subunion (thrift/value (union/current-value object) :property)]
          (conj res (union/current-field-name subunion)))
        res)))


  (p/validate
    [this dirs]
    [(try
       (contains? (type/field-names type)
                  (first dirs))
       (catch Exception e
         false))
     (rest dirs)])
  )

(defn union-name-property-partitioner
  [type]
  (UnionNamePropertyPartitioner. type))