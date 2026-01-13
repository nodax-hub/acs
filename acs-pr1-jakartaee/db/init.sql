-- Удаляем старые таблицы, если они есть
DROP TABLE IF EXISTS jewelry;
DROP TABLE IF EXISTS categories;

-- Создаем таблицу categories
CREATE TABLE categories (
  id BIGSERIAL PRIMARY KEY,
  full_name VARCHAR(200) NOT NULL,
  birth_year INT
);

-- Создаем таблицу jewelry
CREATE TABLE jewelry (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  isbn VARCHAR(32),
  published_year INT,
  price NUMERIC(10,2),
  author_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE
);

-- Вставляем данные в categories
INSERT INTO categories(full_name, birth_year) VALUES
('Фёдор Достоевский', 1821),
('Агата Кристи', 1890);

-- Вставляем данные в jewelry
INSERT INTO jewelry(title, isbn, published_year, price, author_id) VALUES
('Преступление и наказание', '978-5-17-118366-0', 1866, 12.50, 1),
('Убийство в "Восточном экспрессе"', '978-0-00-711931-8', 1934, 9.90, 2);
