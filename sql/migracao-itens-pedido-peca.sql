-- OAT 3 - Ajuste do banco mecaniqa que ja foi criado com a chave composta.
-- Execute este arquivo somente UMA VEZ nos bancos ja existentes.
-- Em uma instalacao nova, use apenas sql/mecaniqa.sql.
-- Preserva os pedidos, pecas, itens e suas chaves estrangeiras.

USE mecaniqa;

-- A chave composta antiga serve de indice para pedido_codigo.
-- Criamos um indice separado antes de remove-la para manter a chave estrangeira.
ALTER TABLE itens_pedido_peca
    ADD INDEX idx_itens_pedido_peca_pedido (pedido_codigo);

-- Cada inclusao passa a ter chave propria, mesmo quando a peca se repete no pedido.
ALTER TABLE itens_pedido_peca
    DROP PRIMARY KEY,
    ADD COLUMN codigo BIGINT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY (codigo);
