DELIMITER //
CREATE OR REPLACE PROCEDURE fluxo_caixa_semanal(IN dt_final DATE, IN dt_inicio DATE)
BEGIN
    SELECT 
        DATE_ADD(DATE(CONCAT(YEAR(dt_associada),"-01-01")), INTERVAL semana-1 WEEK) AS dt_inicio_semana,
        DATE_SUB(DATE_ADD(DATE_ADD(DATE(CONCAT(YEAR(dt_associada),"-01-01")), INTERVAL semana-1 WEEK), INTERVAL 1 WEEK), INTERVAL 1 DAY) AS dt_limite_semana,
        semana,
        arrecadado,
        custo,
        lucro
    FROM (
        SELECT
            dt_associada,
            semana,
            SUM(arrecadado) AS arrecadado,
            SUM(preco_custo_total) AS custo,
            SUM(lucro) AS lucro
        FROM (
            SELECT 
                dt_pago AS dt_associada,
                WEEKOFYEAR(dt_pago) AS semana,
                arrecadado,
                preco_custo_total,
                lucro
            FROM 
                vw_pedido_faturado
            WHERE 
                dt_pago BETWEEN dt_inicio AND dt_final
        ) pedidos_semanais
        GROUP BY semana
    ) AS faturado_semanal;
END//
DELIMITER ;