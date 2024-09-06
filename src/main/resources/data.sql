INSERT INTO customer (username, password, role) VALUES ('admin', '{noop}admin123', 'ADMIN');
INSERT INTO customer (username, password, role) VALUES ('user1', '{noop}admin123', 'USER');
INSERT INTO customer (username, password, role) VALUES ('user2', '{noop}admin123', 'USER');

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