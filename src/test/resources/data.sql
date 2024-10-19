INSERT INTO seller (id, name, contact_info, registration_date) VALUES (153, 'John Doe', 'john@example.com', '2023-10-15 10:15:00');
INSERT INTO seller (id, name, contact_info, registration_date) VALUES (198, 'Jane Smith', 'jane@example.com', '2023-09-10 12:30:00');
INSERT INTO seller (id, name, contact_info, registration_date) VALUES (201, 'Alice Johnson', 'alice@example.com', '2023-08-20 09:45:00');
INSERT INTO seller (id, name, contact_info, registration_date) VALUES (202, 'Bob Brown', 'bob@example.com', '2023-07-22 11:10:00');
INSERT INTO seller (id, name, contact_info, registration_date) VALUES (203, 'Eve White', 'eve@example.com', '2023-06-15 14:05:00');

INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (1, 100.50, 'CASH', '2023-10-16 14:45:00', 153);
INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (2, 200.75, 'CARD', '2023-10-16 15:00:00', 198);
INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (3, 50.00, 'CASH', '2023-09-01 10:30:00', 201);
INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (4, 300.00, 'CARD', '2023-08-30 12:15:00', 202);
INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (5, 75.25, 'CASH', '2023-07-25 09:00:00', 203);
INSERT INTO transaction (id, amount, payment_type, transaction_date, id_seller) VALUES (6, 500.00, 'CARD', '2023-10-17 16:45:00', 153);
