CREATE TABLE conta (
    id BIGINT PRIMARY KEY,
    vencimento DATE,
    pagamento DATE,
    valor NUMERIC(15, 2),
    descricao TEXT,
    situacao TEXT
);

CREATE SEQUENCE conta_id_seq START 1;