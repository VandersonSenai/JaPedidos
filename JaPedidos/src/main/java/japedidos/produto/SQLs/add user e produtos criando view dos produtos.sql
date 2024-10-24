use titanw25_japedidos_hml;

desc produto;
SELECT  * from produto;
SELECT  * from categoria;
SELECT  * from unidade;
SELECT  * from produto;
SELECT  * from usuario;

       
INSERT INTO produto( id_categoria, id_unidade, nome, preco_venda, preco_custo, id_usuario_alt )
VALUES	("5"	, "1"	,	"COXINHA DE FRANGO"	, "0.75"	, "0.15",	"1"),
		("5"	, "1"	,	"RIZOLE DE PIZZA"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"BOLINHA DE QUEIJO"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"KIBE DE CARNE"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"RISSOLE DE CAMARÃO"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"MINI CHURROS"	, "0.75"	, "0.15", 	"1"),
        ("5"	, "1"	,	"ENROLADO DE SALSICHA "	, "0.75"	, "0.15", 	"1")
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