CREATE OR REPLACE VIEW vw_produtos_pedido
AS SELECT 
    prod_ped.id_pedido AS id_pedido,
    prod.nome AS nome_produto,
    prod_ped.quantidade AS quantidade_produto
    FROM produto_pedido AS prod_ped
        INNER JOIN produto AS prod ON prod.id = prod_ped.id_produto;
    
    
    