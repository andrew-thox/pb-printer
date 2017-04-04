(ns printer.titan-adapter
  (:require [clojurewerkz.titanium.graph    :as tg]
            [clojurewerkz.titanium.vertices :as tv]
            [clojurewerkz.titanium.edges    :as te]))


;current fields of article
;:title, :author, :publication, to_timestamp(:publish_date/1000), :link,  to_timestamp(:acquistion_date/1000), :hash
;TODO: we need to know the source
;TODO: narrow caught exception down beyond illegalargumentexception

(defn create-article-vertex
  "create an article vertex, contains only type and url (url == pk) - i"
  [url title date-published date-processed body]
  (try
    (tg/transact!
      (let [vertex (tv/create! {:url url
                                :type "article"
                                :title title
                                :date-published date-published
                                :date-processed date-processed})]
        ;When multiple optional args I think we should merge a map of all opt args provided
        ;Body should not be optional once we implement scraping it
        (if body (tv/assoc! (tv/refresh vertex) :body body)) vertex))
   ;IllegalArguementException implies article vertex already exists
   (catch IllegalArgumentException e (first (tg/transact! (tv/find-by-kv :url url))))))

(defn create-author-vertex
  "create an author vertex from name - idempotent"
  [name]
  (try
    (tg/transact!
      (tv/create! {:name name
                   :type "author"}))
  (catch Exception e (first (tg/transact! (tv/find-by-kv :name name))))))

(defn create-outlet-vertex
  "create an outlet vertex from name - idempotent"
  [name]
  (try
    (tg/transact!
      (tv/create! {:name name
                   :type "outlet"}))
  (catch Exception e (first (tg/transact! (tv/find-by-kv :name name))))))

(defn connect-author
  "establishes who an article was written by"
  [article-vertex author-vertex]
  (tg/transact! (te/connect! (tv/refresh article-vertex)
                               :wrote
                               (tv/refresh author-vertex))))

(defn connect-outlet
  "establishes who an article was published by"
  [article-vertex author-vertex]
  (tg/transact! (te/connect! (tv/refresh article-vertex)
                               :published
                               (tv/refresh author-vertex))))

(defn create-article [article]
  ;create/retrieve article vertex, author vertex and outlet vertex
  (let [article-vertex (create-article-vertex (:link article)
                                       (:title article)
                                       (:publish_date article)
                                       (:acquistion_date article)
                                       (:body article))
        author-vertex (create-author-vertex (:author article))
        outlet-vertex (create-outlet-vertex (:publication article))]
    (connect-author article-vertex author-vertex)
    (connect-outlet article-vertex outlet-vertex)))
  ;Potential flow - ^^^^^^^^^^
  ;check who wrote the article
  ;does existing article have the same author
  ;yes - do nothing
  ;no - remove edge for existing author, add edge for new author
  ;new-article, create author, create edge
  ;does article have the same outlet
  ;yes - do nothing
  ;no - what the fuck?
  ;article has no outlet
  ;new outlet, create outlet, create edge


(def article {:publication "New Statesman"
 :title "Truth and reconciliation? The reality is Northern Ireland will have neither"
 :author "Kevin Meagher"
 :link "http://www.statesman.com/politics/devolution/2016/01/truth-and-reconciliation-reality-northern-ireland-will-have-neither"
 :publish_date 1452685252000
 :acquistion_date 1453327403433})