DELIMITER //
CREATE OR REPLACE PROCEDURE fluxo_caixa_agregado(IN dt_final DATE, IN dt_inicio DATE)
BEGIN
--     DECLARE dt_final DATE DEFAULT CURRENT_DATE();
--     DECLARE dt_inicio DATE DEFAULT DATE_SUB(dt_final, INTERVAL 1 WEEK);

    DECLARE concluido INT DEFAULT 8;
    DECLARE cancelado INT DEFAULT 9;
    DECLARE pago INT DEFAULT 3;

    SELECT 
        SUM(arrecadado) AS arrecadado,
        SUM(preco_custo_total) AS custo,
        SUM(lucro) AS lucro
    FROM vw_pedido_faturado
    WHERE
        dt_pago BETWEEN dt_inicio AND dt_final;
END//
DELIMITER ;