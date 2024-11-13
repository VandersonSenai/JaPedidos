SELECT 
    p.id,
    p.id_cliente,
    -- Informações do cliente
    c.telefone AS telefone_cliente,
    c.nome AS nome_cliente,
    info_pf.nome_cliente AS nome_cliente_info_pf,
    info_pf.cpf AS cpf_info_pf,
    info_pj.cnpj AS cnpj_info_pj,
    info_pj.nome_fantasia AS nome_fantasia_info_pj,
    info_pj.nome_empresarial AS nome_empresarial_info_pj,
    -- Informações de entrega
    p.tipo_entrega,
    p.dthr_entregar,
    p.preco_frete,
    d.logradouro AS logradouro_destino,
    d.numero AS numero_destino,
    d.bairro AS bairro_destino,
    d.cidade AS cidade_destino,
    d.estado AS estado_destino,
    d.pais AS pais_destino,
    destinatario.info AS info_destinatario,
    -- Informações de controle de alteração
    p.id_usuario_autor,
    u.nome AS nome_usuario_autor,
    p.dthr_criacao,
    p.id_usuario_alt,
    u_alt.nome AS nome_usuario_alt,
    p.dthr_alt,
    -- Informações de pagamento
    p.tx_desconto,
    p.preco_final,
    p.dt_venc_pagamento,
    p.dt_pago,
    p.preco_custo_total,
    -- Informações de estado atual
    u_e_p.id_est AS id_ultimo_est,
    u_e_p.nome_est AS nome_ultimo_est,
    u_e_p.id_usuario_autor_est AS id_usuario_autor_ultimo_est,
    u_e_p.nome_usuario_autor_est AS nome_usuario_autor_ultimo_est,
    u_e_p.dthr_criacao_est AS dthr_criacao_ultimo_est
FROM 
    pedido AS p
    -- Informações básicas do pedido
    LEFT JOIN usuario AS u 
        ON u.id = p.id_usuario_autor 
    LEFT JOIN usuario AS u_alt
        ON u_alt.id = p.id_usuario_alt 
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
ORDER BY p.id;
