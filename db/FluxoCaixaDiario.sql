DELIMITER //
CREATE OR REPLACE PROCEDURE fluxo_caixa_diario(IN dt_final DATE, IN dt_inicio DATE)
BEGIN
    DECLARE total_arrecadado DECIMAL(12,2);
    DECLARE total_custo DECIMAL(12,2);
    DECLARE lucro_total DECIMAL(12,2);

    SELECT 
        dt_associada,
        arrecadado,
        custo,
        lucro
    FROM (
        SELECT 
            dt_pago AS dt_associada,
            SUM(arrecadado) AS arrecadado,
            SUM(preco_custo_total) AS custo,
            SUM(lucro) AS lucro
        FROM vw_pedido_faturado
        WHERE 
            dt_pago BETWEEN dt_inicio AND dt_final
        GROUP BY dt_pago
    ) AS faturado_diario;
END//
DELIMITER ;