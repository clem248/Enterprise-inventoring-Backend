ALTER TABLE invents
    ADD COLUMN category_id BIGINT,
    ADD COLUMN quality_id BIGINT,
    ADD COLUMN location_id BIGINT;

ALTER TABLE invents
    ADD CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES category(id);

ALTER TABLE invents
    ADD CONSTRAINT fk_quality
        FOREIGN KEY (quality_id)
            REFERENCES quality(id);

ALTER TABLE invents
    ADD CONSTRAINT fk_location
        FOREIGN KEY (location_id)
            REFERENCES location(id);

-- Удалить старые колонки, если данные уже перенесены
ALTER TABLE invents
    DROP COLUMN category,
    DROP COLUMN quality,
    DROP COLUMN location;
