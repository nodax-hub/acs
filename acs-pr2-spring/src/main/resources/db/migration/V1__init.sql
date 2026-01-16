CREATE TABLE categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE jewelry
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    material    VARCHAR(100),
    price       DECIMAL(10, 2),
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_jewelry_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE CASCADE
);
