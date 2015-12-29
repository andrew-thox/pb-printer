-- name: create-article<!
INSERT INTO article (title, author, publication, publish_date, link, acquistion_date, hash) VALUES (:title, :author, :publication, to_timestamp(:publish_date/1000), :link,  to_timestamp(:acquistion_date/1000), :hash)

-- name: article-by-hash
SELECT * FROM article WHERE hash = :hash