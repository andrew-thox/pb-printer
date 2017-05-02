(ns printer.database-adapter
  (:require [yesql.core :refer [defqueries]]
            [jdbc.pool.c3p0 :as pool]
            [environ.core :refer [env]]
            [pandect.algo.sha256 :refer :all]
            [qbits.spandex :as s]
            [qbits.spandex.utils :as s-utils])
  (:import (java.net URI)))

(def c (s/client {:hosts [(env :aws-elastic-url)]}))

(defn consume-article [article]
  (let [hash (str (sha256 (clojure.string/join " " [(:author article) (:title article) (:link article)])))]
    (if false nil
      (do
        (println (conj article {:hash hash}))
        (s/request c {:url (s-utils/url ["customer" "external"])
              :method :post
              :body article})
        (println "NEW ARTICLE")
        (System/exit 0)
        ))))
