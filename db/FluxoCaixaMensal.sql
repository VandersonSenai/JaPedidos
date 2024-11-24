DELIMITER //
CREATE OR REPLACE PROCEDURE fluxo_caixa_mensal(IN dt_final DATE, IN dt_inicio DATE)
BEGIN
    SELECT 
        DATE_ADD(DATE(CONCAT(YEAR(dt_associada),"-01-01")), INTERVAL mes-1 MONTH) AS dt_inicio_mes,
        DATE_SUB(DATE_ADD(DATE_ADD(DATE(CONCAT(YEAR(dt_associada),"-01-01")), INTERVAL mes-1 MONTH), INTERVAL 1 MONTH), INTERVAL 1 DAY) AS dt_limite_mes,
        mes,
        arrecadado,
        custo,
        lucro
    FROM (
        SELECT
            dt_associada,
            mes,
            SUM(arrecadado) AS arrecadado,
            SUM(preco_custo_total) AS custo,
            SUM(lucro) AS lucro
        FROM (
            SELECT 
                dt_pago AS dt_associada,
                MONTH(dt_pago) AS mes,
                arrecadado,
                preco_custo_total,
                lucro
            FROM 
                vw_pedido_faturado
            WHERE 
                dt_pago BETWEEN dt_inicio AND dt_final
        ) pedidos_mensais
        GROUP BY mes
    ) AS faturado_mensal;
END//
DELIMITER ;