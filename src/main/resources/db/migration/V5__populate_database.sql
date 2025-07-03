INSERT INTO categories (name)
VALUES ('Dairy'),
       ('Fruits'),
       ('Bakery'),
       ('Beverages'),
       ('Snacks');

INSERT INTO products (name, price, description, category_id)
VALUES ('Organic Whole Milk', 4.99, 'A gallon of organic whole milk from grass-fed cows.', 1),
       ('Strawberries - 1 lb', 3.49, 'Fresh, ripe strawberries packed in a 1 lb container.', 2),
       ('Bananas', 0.59, 'Fresh yellow bananas sold per pound.', 2),
       ('Sourdough Bread', 4.25, 'Freshly baked sourdough bread with a crispy crust.', 3),
       ('Chocolate Chip Cookies', 3.99, 'Bakery-style chocolate chip cookies, 12 count.', 3),
       ('Coca-Cola 12-Pack', 7.99, '12 cans of Coca-Cola Classic, 12 fl oz each.', 4),
       ('Orange Juice - 64 oz', 5.49, 'Fresh squeezed orange juice with no pulp.', 4),
       ('Lay’s Classic Potato Chips', 3.49, 'Crispy and salty classic Lay’s potato chips.', 5),
       ('Doritos Nacho Cheese', 3.99, 'Bold nacho cheese flavored tortilla chips.', 5),
       ('Greek Yogurt - Plain', 1.49, 'Individual serving of plain Greek yogurt, 5.3 oz.', 1);
