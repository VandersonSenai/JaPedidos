CREATE VIEW vw_pedido_faturado AS
    SELECT 
        ped.id AS id_pedido,
        ped.dt_pago,
        ped.preco_custo_total,
        ped.preco_frete,
        ped.preco_final,
        (ped.preco_final - ped.preco_frete) AS arrecadado,
        (ped.preco_final - ped.preco_frete) - preco_custo_total AS lucro
    FROM pedido ped 
        LEFT JOIN est_andamento_pedido eap ON eap.id_pedido = ped.id
    WHERE
        id_pedido IN (
            SELECT 
                pedido.id AS id_pedido
            FROM pedido 
                LEFT JOIN est_andamento_pedido eap ON eap.id_pedido = pedido.id
            WHERE 
                eap.id_est_andamento = 3)
        AND id_pedido NOT IN (
            SELECT 
                pedido.id
            FROM pedido 
                LEFT JOIN est_andamento_pedido eap ON eap.id_pedido = pedido.id
            WHERE eap.id_est_andamento = 9)
    GROUP BY ped.id
    ORDER BY dt_pago DESC;