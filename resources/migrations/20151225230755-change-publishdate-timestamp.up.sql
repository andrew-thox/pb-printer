ALTER TABLE article
    ALTER COLUMN publish_date SET DATA TYPE TIMESTAMP USING publish_date::timestamp without time zone;