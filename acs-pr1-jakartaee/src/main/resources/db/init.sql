INSERT INTO categories(full_name) VALUES
('Фёдор Достоевский'),
('Агата Кристи');

INSERT INTO jewelry(title, category_id) VALUES
(
  'Преступление и наказание',
  (SELECT id FROM categories WHERE full_name = 'Фёдор Достоевский')
),
(
  'Убийство в "Восточном экспрессе"',
  (SELECT id FROM categories WHERE full_name = 'Агата Кристи')
);
