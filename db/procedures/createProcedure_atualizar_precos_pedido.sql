DELIMITER //
CREATE PROCEDURE atualizar_precos_pedido(IN id_ped INT)
MODIFIES SQL DATA
    BEGIN
        DECLARE soma_custo DECIMAL(8,2);
        DECLARE soma_venda DECIMAL(8,2);
        DECLARE valor_preco_final DECIMAL(8,2);
        DECLARE valor_preco_frete DECIMAL (8,2);
        DECLARE valor_tx_desconto INT;
    
        -- Definição de valores auxilires
        SELECT 
            p.preco_frete, p.tx_desconto 
        INTO valor_preco_frete, valor_tx_desconto 
        FROM pedido p
        WHERE 
            p.id = id_ped 
        LIMIT 1;
        
        -- Soma precos dos produtos
        SELECT 
            SUM(pp.preco_venda * pp.quantidade) AS pv_total, 
            SUM(pp.preco_custo * pp.quantidade) AS pc_total
        INTO
            soma_venda,
            soma_custo
        FROM produto_pedido pp 
        WHERE
            pp.id_pedido = id_ped;
        
        -- Cálculos finais
        SET valor_preco_final = soma_venda - (soma_venda * CAST(valor_tx_desconto/100 AS DECIMAL(3,2))) + valor_preco_frete;
    
        UPDATE 
            pedido 
        SET 
            preco_final = valor_preco_final, 
            preco_custo_total = soma_custo 
        WHERE id = id_ped;
    END;
//
DELIMITER ;
