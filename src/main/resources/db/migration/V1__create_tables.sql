CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE plano_dieta (
    plano_dieta_id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    objetivo_fitness VARCHAR(100) NOT NULL,
    calorias_diarias NUMERIC(10,2) NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,

    CONSTRAINT fk_plano_dieta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);

CREATE TABLE dia_dieta (
    dia_dieta_id BIGSERIAL PRIMARY KEY,
    plano_dieta_id BIGINT NOT NULL,
    dia VARCHAR(20) NOT NULL,

    CONSTRAINT fk_dia_dieta_plano
        FOREIGN KEY (plano_dieta_id)
        REFERENCES plano_dieta(plano_dieta_id)
        ON DELETE CASCADE
);

CREATE TABLE refeicao (
    refeicao_id BIGSERIAL PRIMARY KEY,
    dia_dieta_id BIGINT NOT NULL,
    tp_refeicao VARCHAR(50) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    calorias NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_refeicao_dia
        FOREIGN KEY (dia_dieta_id)
        REFERENCES dia_dieta(dia_dieta_id)
        ON DELETE CASCADE
);
