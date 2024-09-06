INSERT INTO customer (username, password, role) VALUES ('admin', '$2a$10$DowJQ1XJ5pzFGmY6GfZrAue5AOWzfpwGBRqlqzqxXFWl5w1ZqMEvW', 'ADMIN');
INSERT INTO customer (username, password, role) VALUES ('user1', '$2a$10$DowJQ1XJ5pzFGmY6GfZrAue5AOWzfpwGBRqlqzqxXFWl5w1ZqMEvW', 'USER');
INSERT INTO customer (username, password, role) VALUES ('user2', '$2a$10$DowJQ1XJ5pzFGmY6GfZrAue5AOWzfpwGBRqlqzqxXFWl5w1ZqMEvW', 'USER');

INSERT INTO asset (customer_id, asset_type, size, usable_size) VALUES ((SELECT id FROM customer WHERE username='user1'), 'TRY', 10000, 10000);
INSERT INTO asset (customer_id, asset_type, size, usable_size) VALUES ((SELECT id FROM customer WHERE username='user2'), 'TRY', 10000, 10000);

INSERT INTO trade_order (customer_id, asset_id, side, size, price, status) VALUES (
                                                                                 (SELECT id FROM customer WHERE username='user1'),
                                                                                 (SELECT id FROM asset WHERE customer_id=(SELECT id FROM customer WHERE username='user1')),
                                                                                 'BUY', 0.5, 45000, 'PENDING'
                                                                             );

INSERT INTO trade_order (customer_id, asset_id, side, size, price, status) VALUES (
                                                                                 (SELECT id FROM customer WHERE username='user2'),
                                                                                 (SELECT id FROM asset WHERE customer_id=(SELECT id FROM customer WHERE username='user2')),
                                                                                 'SELL', 1.5, 3000, 'MATCHED'
                                                                             );