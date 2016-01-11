(ns printer.pail.helpers
  (:require [clj-pail.core :as pl]))

;; pail helpers
(defn find-or-create [pstruct path & {:as create-key-args}]
  "Get a pail from a path, or create one if not found"
  (try (pl/pail path)
       (catch Exception e
          (apply pl/create (pl/spec pstruct) path (mapcat identity create-key-args)))))