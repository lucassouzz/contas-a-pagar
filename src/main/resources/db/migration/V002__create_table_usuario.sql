CREATE TABLE usuario (
    id BIGINT PRIMARY KEY,
    email TEXT,
    password TEXT
);

CREATE SEQUENCE usuario_id_seq START 1;