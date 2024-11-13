SELECT 
    * 
FROM 
    pedido AS p
    -- Informações básicas do pedido
    LEFT JOIN usuario AS u 
        ON u.id = p.id_usuario_autor 
    LEFT JOIN cliente AS c 
        ON c.id = p.id_cliente 
    LEFT JOIN destino AS d 
        ON d.id_pedido = p.id 
    LEFT JOIN destinatario 
        ON destinatario.id_pedido = p.id 
    -- Informações adicionais do cliente
    LEFT JOIN info_pf 
        ON info_pf.id_pedido = p.id 
    LEFT JOIN info_pj 
        ON info_pj.id_pedido = p.id 
    -- Estado atual do pedido
    LEFT JOIN vw_ultimo_estado_pedido AS u_e_p 
        ON u_e_p.id_pedido = p.id
ORDER BY p.id 
DESC LIMIT 1;


