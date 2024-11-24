DELIMITER //
CREATE OR REPLACE PROCEDURE fluxo_caixa_anual(IN dt_final DATE, IN dt_inicio DATE)
BEGIN
    SELECT 
        ano,
        arrecadado,
        custo,
        lucro
    FROM (
        SELECT
            dt_associada,
            ano,
            SUM(arrecadado) AS arrecadado,
            SUM(preco_custo_total) AS custo,
            SUM(lucro) AS lucro
        FROM (
            SELECT 
                dt_pago AS dt_associada,
                YEAR(dt_pago) AS ano,
                arrecadado,
                preco_custo_total,
                lucro
            FROM 
                vw_pedido_faturado
            WHERE 
                dt_pago BETWEEN dt_inicio AND dt_final
        ) pedidos_anuais
        GROUP BY ano
    ) AS faturado_anual;
END//
DELIMITER ;