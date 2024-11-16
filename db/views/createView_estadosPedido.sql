CREATE OR REPLACE VIEW vw_est_andamento_pedido AS 
SELECT 
    e_a_p.id_pedido,
    e_a_p.id_est_andamento,
    e_a.nome AS nome_est_andamento,
    e_a_p.id_usuario_autor,
    u.nome AS nome_usuario_autor,
    e_a_p.dthr_criacao
FROM 
    est_andamento_pedido AS e_a_p
    INNER JOIN est_andamento AS e_a ON e_a.id = e_a_p.id_est_andamento
    INNER JOIN usuario AS u ON u.id = e_a_p.id_usuario_autor
ORDER BY id_pedido ASC, dthr_criacao ASC, id_est_andamento ASC;