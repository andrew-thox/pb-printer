(ns printer.out
  (:require [ogre.core :as oq]))

;this will eventually form the base for the front end api

;list of authors
(tg/transact!
 (oq/query (tv/find-by-kv :type "author")
           (oq/property :name)
           oq/into-set!))

;list of outlets
(tg/transact!
 (oq/query (tv/find-by-kv :type "outlet")
           (oq/property :name)
           oq/into-set!))

;list of articles
(tg/transact!
 (oq/query (tv/find-by-kv :type "article")
           (oq/property :title)
           oq/into-set!))

;TODO: Work out which direction is which
;list of articles by Kevin Meagher
(tg/transact!
 (oq/query (tv/find-by-kv :name "Kevin Meagher")
           (oq/<-- [:wrote])
           (oq/property :title)
           oq/into-set!))

;list of articles published by New Statesman
(tg/transact!
 (oq/query (tv/find-by-kv :name "New Statesman")
           (oq/<-- [:published])
           (oq/property :url)
           oq/into-set!))
