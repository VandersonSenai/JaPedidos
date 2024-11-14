DELIMITER //
CREATE OR REPLACE PROCEDURE select_pedidos_by_estado(IN id_est INT) READS SQL DATA
BEGIN
  SELECT * FROM vw_pedido WHERE id_ultimo_est = id_est;
END
//
DELIMITER ;
