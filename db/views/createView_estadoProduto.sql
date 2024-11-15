CREATE OR REPLACE VIEW vw_estadoProduto
AS
	SELECT 
		p.id, 
		p.nome, 
		c.nome AS categoria, 
		u.nome AS unidade, 
		CASE WHEN p.estado = b'1'
			THEN "ATIVO"
			ELSE "DESATIVADO"
		END AS estado
	FROM produto AS p
		INNER JOIN categoria AS c
			ON c.id = p.id_categoria
		INNER JOIN unidade AS u
			ON u.id = p.id_unidade;