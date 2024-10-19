INSERT INTO seller (id,name, contact_info, registration_date) VALUES (153,'John Doe', 'john@example.com', '2023-10-15 10:15:00');
INSERT INTO seller (id,name, contact_info, registration_date) VALUES (198,'Jane Smith', 'jane@example.com', '2023-09-10 12:30:00');

INSERT INTO transaction (id, amount, payment_type, transaction_date,id_seller) VALUES (1, 100.50, 'CASH', '2023-10-16 14:45:00', 153);
INSERT INTO transaction (id, amount, payment_type, transaction_date,id_seller) VALUES (2, 200.75, 'CARD', '2023-10-16 15:00:00',198);
