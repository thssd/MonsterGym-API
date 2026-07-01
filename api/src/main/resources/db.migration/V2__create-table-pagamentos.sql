CREATE TABLE pagamentos (

    id BIGSERIAL PRIMARY KEY,
    valor DOUBLE PRECISION NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    aluno_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_pagamentos_aluno
                        FOREIGN KEY (aluno_id)
                        REFERENCES aluno(id)
                        ON DELETE RESTRICT
);