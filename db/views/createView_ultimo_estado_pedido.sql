CREATE OR REPLACE VIEW vw_ultimo_estado_pedido AS
     SELECT
        ultimo_estado_pedido.id_pedido,
        ultimo_estado_pedido.id_est_andamento AS id_est,
        e_a.nome AS nome_est,
        ultimo_estado_pedido.id_usuario_autor AS id_usuario_autor_est,
        u.nome AS nome_usuario_autor_est,
        ultimo_estado_pedido.dthr_criacao AS dthr_criacao_est
    FROM
    -- Obtém data e hora do último estado associado a cada pedido
        (SELECT *
            FROM (
                SELECT
                    id_pedido,
                    id_est_andamento,
                    id_usuario_autor,
                    dthr_criacao,
                    (SELECT MAX(dthr_criacao) FROM est_andamento_pedido AS E WHERE E.id_pedido = est_andamento_pedido.id_pedido) AS dthr_ultimo
                FROM est_andamento_pedido
                ORDER BY id_pedido ASC, dthr_ultimo DESC , id_est_andamento DESC
            ) AS abc
            GROUP BY abc.id_pedido
        ) AS ultimo_estado_pedido
        INNER JOIN est_andamento AS e_a
            ON e_a.id = ultimo_estado_pedido.id_est_andamento
        INNER JOIN usuario AS u
            ON u.id = ultimo_estado_pedido.id_usuario_autor
    WHERE dthr_criacao = dthr_ultimo; -- Exibe apenas os estados de cada pedido que sejam os últimos (tenham data de criação igual à ultima encontrada para cada pedido)