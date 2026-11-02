-- OAT 3 - Persistencia de dados da MecaniQA (MySQL)
-- Tabelas correspondentes exclusivamente aos modelos existentes nas OATs 1 e 2.
-- Os codigos das entidades continuam sendo definidos pela aplicacao.
-- Nao ha insercoes iniciais previstas nos documentos.

CREATE DATABASE IF NOT EXISTS mecaniqa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mecaniqa;

CREATE TABLE IF NOT EXISTS pecas (
    codigo BIGINT NOT NULL,
    nome VARCHAR(255),
    codigo_barras VARCHAR(255) NOT NULL,
    fornecedor_marca VARCHAR(255) NOT NULL,
    quantidade_estoque INT NOT NULL,
    preco_custo DOUBLE NOT NULL,
    preco_venda DOUBLE NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    tamanho VARCHAR(255),
    cor VARCHAR(255),
    data_cadastro DATETIME NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS servicos (
    codigo BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    tempo_estimado_minutos INT NOT NULL,
    custo_tabelado DOUBLE NOT NULL,
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS ordens_servico (
    codigo BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS pedidos_pecas (
    codigo BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS itens_pedido_peca (
    pedido_codigo BIGINT NOT NULL,
    peca_codigo BIGINT NOT NULL,
    quantidade INT NOT NULL,
    PRIMARY KEY (pedido_codigo, peca_codigo),
    CONSTRAINT fk_itens_pedido_peca_pedido
        FOREIGN KEY (pedido_codigo) REFERENCES pedidos_pecas (codigo),
    CONSTRAINT fk_itens_pedido_peca_peca
        FOREIGN KEY (peca_codigo) REFERENCES pecas (codigo)
);
