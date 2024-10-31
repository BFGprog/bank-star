CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    user_id UUID,
    amount INTEGER,
    product_id UUID,
    type VARCHAR(255)
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    type VARCHAR(255)
);