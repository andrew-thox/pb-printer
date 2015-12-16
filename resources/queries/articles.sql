-- name: create-article<!
INSERT INTO article (title, author, publication, publish_date, link, hash) VALUES (:title, :author, :publication, :publish_date, :link, :hash)

-- name: article-by-hash
SELECT * FROM article WHERE hash = :hash