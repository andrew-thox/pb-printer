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

;TODO: check argument types
(defn get-or-create-type
  "some desc"
  [name type opts]
  (let [existing-type (tg/transact! (tt/get-type name))]
    (if existing-type
      existing-type
      (tg/transact!
        (tt/defkey (keyword name)
                   type
                   opts)))))

;define name
;Is it possible to have a collision between author name and pub name?
;We obviously can't handle multiple authors with the same name, at least not in V1
;Google and Amazon don't handle this well

(defn create-types []
  (get-or-create-type "name" String {:indexed-vertex? true :unique-direction :both})
  (get-or-create-type "type" String {:indexed-vertex? true :unique-direction :out})
  (get-or-create-type "url" String {:indexed-vertex? true :unique-direction :both})
  (get-or-create-type "title" String {:unique-direction :out})
  (get-or-create-type "date-published" Long {:unique-direction :out})
  (get-or-create-type "date-processed" Long {:unique-direction :out})
  (get-or-create-type "third-party-guid" String {:unique-direction :out :indexed-vertex? true})
  (get-or-create-type "hash" String {:unique-direction :out})
  (get-or-create-type "body" String {:unique-direction :out})

  ;edge labels
  (tg/transact!
    (tt/deflabel :wrote ))
  ;published
  (tg/transact!
    (tt/deflabel :published )))
  ;equiv - possible implementation of authors who have multiple names

;Other ideas
;pedigree
;date
;source

;(tg/open "/Users/andrew/Programming/cloggs/printer/example-db")

(require '[clojurewerkz.titanium.graph    :as tg])
(require '[clojurewerkz.titanium.types    :as tt])
(require '[clojurewerkz.titanium.edges    :as te])
(require '[clojurewerkz.titanium.vertices :as tv])
(require '[ogre.core :as oq])
