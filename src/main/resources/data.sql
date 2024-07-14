INSERT INTO public.customers (id, email, name, phone)
VALUES (1, 'uanderson@gmail.com', 'Uanderson Oliveira', '08123456789'),
       (2, 'teste@gmail.com', 'teste', '213423423');

INSERT INTO public.orders (id, customer_id, delivery_date, order_date, order_number, status)
VALUES (1, 1, '2023-01-01 00:00:00', '2023-03-01 00:00:00', 'ORD-0001', 'DELIVERED'),
       (2, 1, '2023-01-01 00:00:00', '2023-03-01 00:00:00', 'ORD-0002', 'PENDING'),
       (3, 2, '2023-01-01 00:00:00', '2023-03-01 00:00:00', 'ORD-0003', 'CANCELLED'),
       (4, 2, '2024-01-01 00:00:00', '2024-04-01 00:00:00', 'ORD-0004', 'DELIVERED'),
       (5, 1, '2024-01-01 00:00:00', '2024-04-01 00:00:00', 'ORD-0005', 'PENDING'),
       (6, 1, '2024-01-01 00:00:00', '2024-04-01 00:00:00', 'ORD-0006', 'CANCELLED');

INSERT INTO public.products (id, price, code, description, name)
VALUES (1, 5.0, 'PRD-0001', 'Product 1 Description', 'Product 1'),
       (2, 15.0, 'PRD-0002', 'Product 2 Description', 'Product 2'),
       (3, 25.0, 'PRD-0003', 'Product 3 Description', 'Product 3'),
       (4, 35.0, 'PRD-0004', 'Product 4 Description', 'Product 4'),
       (5, 45.0, 'PRD-0005', 'Product 5 Description', 'Product 5'),
       (6, 55.0, 'PRD-0006', 'Product 6 Description', 'Product 6');

INSERT INTO public.orderdetails (order_id, product_id, quantity, price, total)
VALUES (1, 1, 1, 5.0, 5.0),
       (1, 2, 2, 15.0, 30.0),
       (2, 3, 3, 25.0, 75.0),
       (2, 4, 4, 35.0, 140.0),
       (3, 5, 5, 45.0, 225.0),
       (3, 6, 6, 55.0, 330.0),
       (4, 1, 1, 5.0, 5.0),
       (4, 2, 2, 15.0, 30.0),
       (5, 3, 3, 25.0, 75.0),
       (5, 4, 4, 35.0, 140.0),
       (6, 5, 5, 45.0, 225.0),
       (6, 6, 6, 55.0, 330.0);


