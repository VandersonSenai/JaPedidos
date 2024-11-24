DELIMITER //
CREATE OR REPLACE PROCEDURE produtos_mais_vendidos(IN dt_final DATE, IN dt_inicio DATE, IN result_limit INT)
    BEGIN
        SELECT 
            id_produto,
            SUM(quantidade) AS soma_quantidade, 
            SUM(preco_venda * quantidade) AS soma_venda, 
            SUM(preco_custo * quantidade) AS soma_custo
        FROM 
            produto_pedido pp 
        WHERE 
            pp.id_pedido IN (SELECT vw_pedido_faturado.id_pedido FROM vw_pedido_faturado WHERE dt_pago BETWEEN dt_inicio AND dt_final)
        GROUP BY pp.id_produto
        ORDER BY soma_quantidade DESC, id_produto ASC, soma_venda DESC
        LIMIT result_limit;
    END//
DELIMITER ;