INSERT INTO transactions (id, user_id, amount, product_id, type) VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 100, '00000000-0000-0000-0000-000000000001', 'DEPOSIT');
INSERT INTO transactions (id, user_id, amount, product_id, type) VALUES ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', 200, '00000000-0000-0000-0000-000000000002', 'WITHDRAW');

INSERT INTO products (id, type) VALUES ('00000000-0000-0000-0000-000000000001', 'DEBIT');
INSERT INTO products (id, type) VALUES ('00000000-0000-0000-0000-000000000002', 'INVEST');
INSERT INTO products (id, type) VALUES ('00000000-0000-0000-0000-000000000003', 'CREDIT');
INSERT INTO products (id, type) VALUES ('00000000-0000-0000-0000-000000000004', 'SAVING');