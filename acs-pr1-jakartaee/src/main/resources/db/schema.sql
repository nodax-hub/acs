CREATE TABLE IF NOT EXISTS categories (
  id BIGSERIAL PRIMARY KEY,
  full_name VARCHAR(200) NOT NULL,
  CONSTRAINT uq_authors_full_name UNIQUE (full_name)
);

CREATE TABLE IF NOT EXISTS jewelry (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  published_year INT,
  price NUMERIC(10,2),
  author_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
);
