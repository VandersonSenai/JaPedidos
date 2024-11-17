CREATE OR REPLACE VIEW vw_produto
AS SELECT 
    prod.id,
    prod.nome AS nome_produto,
    prod.id_categoria,
    cat.nome AS nome_categoria,
    cat.descricao AS descricao_categoria,
    prod.id_unidade,
    unid.nome AS nome_unidade,
    unid.abreviacao AS abreviacao_unidade,
    prod.preco_venda,
    prod.preco_custo,
    prod.id_usuario_alt,
    usr.nome AS nome_usuario_alt,
    usr.tipo AS tipo_usuario,
    prod.dthr_alt,
    prod.estado
    FROM produto AS prod
        INNER JOIN categoria AS cat ON cat.id = prod.id_categoria
        INNER JOIN unidade AS unid ON unid.id = prod.id_unidade
        LEFT JOIN usuario AS usr ON usr.id = prod.id_usuario_alt;