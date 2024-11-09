INSERT INTO usuario( nome, login, senha, tipo )
VALUES
	("Valentin Gomes"			, "valentin"	, "admin", "administrador"),
	("Vanderson da Silva"	, "vanderson"	,	"silva", "administrador"),
	("Thiago M."					, "thiago"		,	"admin", "administrador");

INSERT INTO cliente (nome, telefone) VALUES (
	"Josefina Andrade", "40028922"
);

INSERT INTO `categoria` VALUES
	(1,'Bebida','Produtos como refrigerantes, água, sucos.'),
	(2,'Lanche','Produtos como hambúrger e similares.'),
	(3,'Fritas','Produtos com batata-frita.'),
	(4,'Sorvete','Produtos com sorvete.'),
	(5,'Salgado','Produtos como coxinha, enrroladinho e outros.'),
	(6,'Bolo',NULL),
	(7,'Pão',NULL),
	(8,'Torta',NULL),
	(9,'Refeição',NULL),
	(10,'Bala','Produtos doces e pequenos.'),
	(11,'Gelatina',NULL);
       
INSERT INTO `unidade` VALUES
	(1,'Unidade','UND'),
	(2,'Cento','CENTO'),
	(3,'Dúzia','DUZIA'),
	(4,'Pacote','PCT'),
	(5,'Caixa','CX'),
	(6,'Grama','G'),
	(7,'Miligrama','MG'),
	(8,'Litros','L'),
	(9,'Mililitros','ML'),
	(10,'Kilograma','KG'),
	(11,'Garrafa','GRFA'),
	(12,'Pote','POTE');
       
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	(5	, 8		,	"SORVETE LUIGI 1,5lt"			, 19.50	, 12.45	,	2),
				(5	, 8		,	"SORVETE LUIGI 1,7lt"			, 21.75	, 14.15	, 2),
        (5	, 8		,	"AÇAI 1,2lt"							, 18.75	, 13.15	, 2),
				(1	, 11	,	"COCA-COLA 2lts"					, 14.00	, 9.00	,	1),
				(1	, 11	,	"COCA-COLA 600ml"					, 9.00	, 6.50	,	1),
        (1	, 11	,	"GUARANA ANTARTICA 2lts	"	, 14.00	, 9.00	, 1),
        (1	, 11	,	"GUARANA ANTARTICA 600ml"	, 9.00	, 6.50	, 1),
        (1	, 11	,	"HEINIKEN 600lm"					, 16.50	, 10.50	, 1),
        (1	, 11	,	"FANTA UVA 2lT"						, 14.00	, 9.50	, 1),
				(4	, 1		,	"COXINHA DE FRANGO"				, 0.75	, 0.15	,	1),
				(4	, 1		,	"RIZOLE DE PIZZA"					, 0.75	, 0.15	, 1),
        (4	, 1		,	"BOLINHA DE QUEIJO"				, 0.75	, 0.15	, 1),
        (4	, 1		,	"RISSOLE DE CAMARÃO"			, 0.75	, 0.15	,	1),
				(5	, 1		,	"COXINHA DE FRANGO"				, 0.75	, 0.15	,	1),
				(5	, 1		,	"RIZOLE DE PIZZA"					, 0.75	, 0.15	,	1),
        (5	, 1		,	"BOLINHA DE QUEIJO"				, 0.75	, 0.15	,	1),
        (5	, 1		,	"KIBE DE CARNE"						, 0.75	, 0.15	,	1),
        (5	, 1		,	"RISSOLE DE CAMARÃO"			, 0.75	, 0.15	,	1),
        (5	, 1		,	"MINI CHURROS"						, 0.75	, 0.15	,	1),
        (5	, 1		,	"ENROLADO DE SALSICHA "		, 0.75	, 0.15	, 1);
        

SELECT p.id, p.nome, c.nome as categoria, p.preco_venda , p.preco_custo, u.abreviacao as unidade, p.estado
FROM produto as p
LeFT JOIN
	categoria as c
    on p.id_categoria = c.id
LeFT JOIN
	unidade as u
    on p.id_unidade = u.id;
    
CREATE OR REPLACE VIEW `listaTodosProdutos` AS    
SELECT p.id, p.nome, c.nome as categoria, p.preco_venda , p.preco_custo, u.abreviacao as unidade, p.estado
FROM produto as p
LeFT JOIN
	categoria as c
    on p.id_categoria = c.id
LeFT JOIN
	unidade as u
    on p.id_unidade = u.id;
