(ns printer.taps
  (:require  [pail-cascalog.core :as pcas]))

(defn first-name-tap [pail-connection]
  (pcas/pail->tap pail-connection :attributes [["property" "first_name"]] ))

(defn last-name-tap [pail-connection]
  (pcas/pail->tap pail-connection :attributes [["property" "last_name"]] ))

(defn location-tap [pail-connection]
  (pcas/pail->tap pail-connection :attributes [["property" "location"]] ))

(defn friendedge-tap [pail-connection]
  (pcas/pail->tap pail-connection :attributes [["friendshipedge"]] ))