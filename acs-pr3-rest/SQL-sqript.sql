DROP TABLE IF EXISTS Jewelry CASCADE;
DROP TABLE IF EXISTS Categories CASCADE;

CREATE TABLE Categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Jewelry
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    material    VARCHAR(100),
    price       DECIMAL(10, 2),
    category_id INTEGER
        REFERENCES Categories (id)
            ON DELETE CASCADE
);

INSERT INTO Categories (id, name)
VALUES (1, 'Кольца'),
       (2, 'Серьги'),
       (3, 'Подвески и кулоны'),
       (4, 'Браслеты'),
       (5, 'Часы');

INSERT INTO Jewelry (id, title, material, price, category_id)
VALUES (1, 'Кольцо "Классика"', 'Золото 585', 25400.00, 1),
       (2, 'Кольцо с сапфиром', 'Белое золото', 48000.50, 1),
       (3, 'Кольцо "Минимализм"', 'Серебро 925', 3200.00, 1),
       (4, 'Серьги-гвоздики', 'Золото 585', 12300.00, 2),
       (5, 'Длинные серьги "Звезда"', 'Серебро с позолотой', 7500.00, 2),
       (6, 'Серьги с жемчугом', 'Золото 585', 35600.00, 2),
       (7, 'Кулон "Сердце"', 'Красное золото', 9800.00, 3),
       (8, 'Крестик православный', 'Серебро 925', 1500.00, 3),
       (9, 'Медальон для фото', 'Золото 585', 21200.00, 3),
       (10, 'Браслет "Бисмарк"', 'Золото 585', 55000.00, 4),
       (11, 'Кожаный браслет', 'Кожа, сталь', 2500.00, 4),
       (12, 'Жесткий браслет "Обруч"', 'Серебро 925', 6800.00, 4),
       (13, 'Женские часы "Элегант"', 'Сталь, кожа', 18900.00, 5),
       (14, 'Золотые часы "Престиж"', 'Золото, сапфировое стекло', 145000.00, 5);

-- Исправленные имена последовательностей
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM Categories));
SELECT setval('jewelry_id_seq', (SELECT MAX(id) FROM Jewelry));