-- name: create-article<!
INSERT INTO article (title, author, publication, publish_date, link, acquistion_date, hash) VALUES (:title, :author, :publication, :publish_date::timestamptz, :link, :acquistion_date::timestamptz, :hash)

-- name: article-by-hash
SELECT * FROM article WHERE hash = :hash