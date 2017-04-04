(ns printer.types
  (:require [clojurewerkz.titanium.graph    :as tg]
            [clojurewerkz.titanium.types    :as tt]
            [clojurewerkz.titanium.edges    :as te]
            [clojurewerkz.titanium.vertices :as tv]))

;defkey-once

(defn clear-graph!
 "Clears the graph of all objects."
 []
 (doseq [e (te/get-all-edges)]
   (te/remove! e))
 (doseq [v (tv/get-all-vertices)]
   (tv/remove! v)))

;TODO: check argument types, both as inputs to the func and check that existing keys are keys and edge labels are edge labels
(defn get-or-create-property-key
  "some desc"
  [name type opts]
  (let [existing-type (tg/transact! (tt/get-type name))]
    (if existing-type
      existing-type
      (tg/transact!
        (tt/defkey (keyword name)
                   type
                   opts)))))

;TODO: implement opts for edge labels
(defn get-or-create-edge-label
  "some desc"
  [name]
    (let [existing-type (tg/transact! (tt/get-type name))]
    (if existing-type
      existing-type
      (tg/transact!
        (tt/deflabel (keyword name))))))

;define name
;Is it possible to have a collision between author name and pub name?
;We obviously can't handle multiple authors with the same name, at least not in V1
;Google and Amazon don't handle this well
;TODO: It's pretty pointless returning the output of the last edge label

(defn create-types []
  (get-or-create-property-key "name" String {:indexed-vertex? true :unique-direction :both})
  (get-or-create-property-key "type" String {:indexed-vertex? true :unique-direction :out})
  (get-or-create-property-key "url" String {:indexed-vertex? true :unique-direction :both})
  (get-or-create-property-key "title" String {:unique-direction :out})
  (get-or-create-property-key "date-published" Long {:unique-direction :out})
  (get-or-create-property-key "date-processed" Long {:unique-direction :out})
  (get-or-create-property-key "third-party-guid" String {:unique-direction :out :indexed-vertex? true})
  (get-or-create-property-key "hash" String {:unique-direction :out})
  (get-or-create-property-key "body" String {:unique-direction :out})

  (get-or-create-edge-label "wrote")
  (get-or-create-edge-label "published"))



;Other ideas
;pedigree
;date
;source
  ;equiv - possible implementation of authors who have multiple names
;(tg/open "/Users/andrew/Programming/cloggs/printer/example-db")

(require '[clojurewerkz.titanium.graph    :as tg])
(require '[clojurewerkz.titanium.types    :as tt])
(require '[clojurewerkz.titanium.edges    :as te])
(require '[clojurewerkz.titanium.vertices :as tv])
(require '[ogre.core :as oq])
