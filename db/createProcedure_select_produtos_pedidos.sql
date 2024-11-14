-- Seleciona todos os produtos a partir do id do pedido em questão.

DELIMITER //
CREATE PROCEDURE select_produtos_pedido(IN id_ped INT) READS SQL DATA
    BEGIN
        SELECT * FROM vw_produtos_pedido WHERE id_pedido = id_ped;
    END
//
DELIMITER ;