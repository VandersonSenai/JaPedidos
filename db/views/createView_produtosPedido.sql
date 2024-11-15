CREATE OR REPLACE VIEW vw_produtos_pedido
AS SELECT 
    prod_ped.id_pedido AS id_pedido,
    prod_ped.id_produto,
    prod.nome AS nome_produto,
    prod_ped.quantidade AS quantidade_produto,
    prod_ped.info_adicional AS info_adicional_produto_pedido,
    prod.id_categoria,
    cat.nome AS nome_categoria,
    cat.descricao AS descricao_categoria,
    prod.id_unidade,
    unid.nome AS nome_unidade,
    unid.abreviacao AS abreviacao_unidade,
    prod_ped.preco_venda,
    prod_ped.preco_custo,
    prod.id_usuario_alt,
    usr.nome AS nome_usuario_alt,
    usr.tipo AS tipo_usuario,
    prod.dthr_alt,
    prod.estado
    FROM produto_pedido AS prod_ped
        INNER JOIN produto AS prod ON prod.id = prod_ped.id_produto
        INNER JOIN categoria AS cat ON cat.id = prod.id_categoria
        INNER JOIN unidade AS unid ON unid.id = prod.id_unidade
        LEFT JOIN usuario AS usr ON usr.id = prod.id_usuario_alt;