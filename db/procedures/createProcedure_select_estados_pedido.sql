DELIMITER //
CREATE PROCEDURE select_estados_pedido(IN id_ped INT) READS SQL DATA
BEGIN
   SELECT * FROM vw_est_andamento_pedido WHERE id_pedido = id_ped;
END
//
DELIMITER ;