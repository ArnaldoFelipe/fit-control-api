CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE plano_treino (
    plano_id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    objetivo_fitness VARCHAR(50) NOT NULL,
    volume_treino VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,

    CONSTRAINT fk_plano_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario (id)
        ON DELETE CASCADE
);

CREATE TABLE dia_treino (
    dia_id BIGSERIAL PRIMARY KEY,
    plano_id BIGINT NOT NULL,
    dia_da_semana VARCHAR(20) NOT NULL,

    CONSTRAINT fk_dia_plano
        FOREIGN KEY (plano_id)
        REFERENCES plano_treino (plano_id)
        ON DELETE CASCADE
);

CREATE TABLE exercicio (
    exercicio_id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    grupo_muscular VARCHAR(50) NOT NULL
);

CREATE TABLE treino (
    id BIGSERIAL PRIMARY KEY,
    dia_id BIGINT NOT NULL,
    exercicio_id BIGINT NOT NULL,
    series INTEGER NOT NULL,
    repeticoes INTEGER NOT NULL,

    CONSTRAINT fk_treino_dia
        FOREIGN KEY (dia_id)
        REFERENCES dia_treino (dia_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_treino_exercicio
        FOREIGN KEY (exercicio_id)
        REFERENCES exercicio (exercicio_id)
        ON DELETE RESTRICT
);
