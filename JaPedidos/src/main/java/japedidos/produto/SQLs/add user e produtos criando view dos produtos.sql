use titanw25_japedidos_hml;

desc produto;
SELECT  * from produto;
SELECT  * from categoria;
SELECT  * from unidade;
SELECT  * from produto ORDER BY nome asc;
desc produto;
SELECT  * from usuario;
SELECT  * from listaTodosProdutos;

select * from listatodosprodutos group by nome or categoria having nome LIKE "%pizza%" or categoria LIKE "%pizz%";

select * from listatodosprodutos having nome LIKE "%pizza%" or categoria LIKE "%bebi%" ORDER BY nome ASC;
       
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	("4"	, "8"	,	"SORVETE LUIGI 1,5lt"	, "19.50"	, "12.45",	"2"),
		("4"	, "8"	,	"SORVETE LUIGI 1,7lt"	, "21,75"	, "14.15", 	"2"),
        ("4"	, "8"	,	"AÇAI 1,2lt"			, "18.75"	, "13.15", 	"2")
        ;
        
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	("1"	, "11"	,	"COCA-COLA 2lts				"	, "14.00"	, "9.00",	"1"),
		("1"	, "11"	,	"COCA-COLA 600ml			"	, "9.00"	, "6.50",	"1"),
        ("1"	, "11"	,	"GUARANA ANTARTICA 2lts		"	, "14.00"	, "9.00", 	"1"),
        ("1"	, "11"	,	"GUARANA ANTARTICA 600ml	"	, "9.00"	, "6.50", 	"1"),
        ("1"	, "11"	,	"HEINIKEN 600lm				"	, "16.50"	, "10.50", 	"1"),
        ("1"	, "11"	,	"FANTA UVA 2lT 				"	, "14.00"	, "9.50", 	"1")
        ;
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	("5"	, "1"	,	"COXINHA DE FRANGO"		, "0.75"	, "0.15",	"1"),
		("5"	, "1"	,	"RIZOLE DE PIZZA"		, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"RISSOLE DE CAMARÃO"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"BOLINHA DE QUEIJO"		, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"KIBE DE CARNE"			, "0.75"	, "0.15", 	"1"),
		("5"	, "1"	,	"MILHO COM REQUEIJÃO"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"MINI CHURROS"			, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"ENTROLADO DE SALSICHA"	, "0.75"	, "0.15", 	"1")
        ;
        
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	("4"	, "8"	,	"AÇAI 1,2lt"			, "500.75"	, "350.15", 	"2")
        ;	

        
INSERT INTO usuario( nome, login, senha, tipo )
VALUES	("Vanderson da Silva"	, "vanderson"	,	"silva", "administrador");
        
DELETE FROM  produto where id_categoria="5";
SELECT * from produto;

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