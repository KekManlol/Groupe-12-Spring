INSERT INTO positions(position_id, ticker, quantity, unit_value) VALUES (1, 'ABCD', 2, 5);
INSERT INTO positions(position_id, ticker, quantity, unit_value) VALUES (2, 'FNAC', 3, 10);
INSERT INTO positions(position_id, ticker, quantity, unit_value) VALUES (3, 'FNAC', 4, 10);
INSERT INTO wallets(id, username, position_id) VALUES (1, 'bob', 1);
INSERT INTO wallets(id, username, position_id) VALUES (2, 'bob', 2);