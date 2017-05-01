(ns printer.database-adapter
  (:require [yesql.core :refer [defqueries]]
            [jdbc.pool.c3p0 :as pool]
            [environ.core :refer [env]]
            [pandect.algo.sha256 :refer :all]
            [clj-statsd :as statsd])
  (:import (java.net URI)))

(defn consume-article [article]
  (let [hash (str (sha256 (clojure.string/join " " [(:author article) (:title article) (:link article)])))]
    (if false nil
      (do
        (println (conj article {:hash hash}))
        (statsd/increment (clojure.string/lower-case (str (:publication article) "_articles_processed")))
        (statsd/increment "articles_processed")))))
