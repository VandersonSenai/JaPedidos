SELECT 
    * 
FROM 
    pedido AS p
    LEFT JOIN usuario ON usuario.id = p.id_usuario_autor 
    LEFT JOIN cliente ON cliente.id = p.id_cliente 
    LEFT JOIN destino ON destino.id_pedido = p.id 
    LEFT JOIN destinatario ON destinatario.id_pedido = p.id 
    LEFT JOIN info_pf ON info_pf.id_pedido = p.id 
    LEFT JOIN info_pj ON info_pj.id_pedido = p.id 
    LEFT JOIN est_andamento_pedido ON est_andamento_pedido.id_pedido = p.id 
    LEFT JOIN est_andamento ON est_andamento.id = est_andamento_pedido.id_est_andamento 
    LEFT JOIN usuario AS usr_est ON usr_est.id = est_andamento_pedido.id_usuario_autor 
ORDER BY p.id 
DESC LIMIT 1;
