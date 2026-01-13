INSERT INTO categories(full_name) VALUES
('Фёдор Достоевский'),
('Агата Кристи');

INSERT INTO jewelry(title, published_year, price, author_id) VALUES
(
  'Преступление и наказание',
  1866,
  12.50,
  (SELECT id FROM categories WHERE full_name = 'Фёдор Достоевский')
),
(
  'Убийство в "Восточном экспрессе"',
  1934,
  9.90,
  (SELECT id FROM categories WHERE full_name = 'Агата Кристи')
);
