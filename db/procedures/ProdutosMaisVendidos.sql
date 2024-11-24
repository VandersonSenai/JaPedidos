DELIMITER //
CREATE OR REPLACE PROCEDURE produtos_mais_vendidos(IN dt_final DATE, IN dt_inicio DATE, IN result_limit INT)
    BEGIN
        SELECT 
            pp.id_produto,
			p.nome AS nome_produto,
            SUM(pp.quantidade) AS soma_quantidade, 
            SUM(pp.preco_venda * pp.quantidade) AS soma_venda, 
            SUM(pp.preco_custo * pp.quantidade) AS soma_custo
        FROM 
            produto_pedido pp 
			INNER JOIN produto p ON p.id = pp.id_produto
        WHERE 
            pp.id_pedido IN (SELECT vw_pedido_faturado.id_pedido FROM vw_pedido_faturado WHERE dt_pago BETWEEN dt_final AND dt_inicio)
        GROUP BY pp.id_produto
        ORDER BY soma_quantidade DESC, id_produto ASC, soma_venda DESC
        LIMIT result_limit;
    END//
DELIMITER ;