DROP DATABASE IF EXISTS japedidos;

CREATE DATABASE japedidos;
USE japedidos;

CREATE TABLE categoria(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(24) NOT NULL,
	descricao VARCHAR(120)
);

SELECT * FROM categoria;

CREATE TABLE unidade(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(16) NOT NULL,
	abreviacao CHAR(5) NOT NULL
);

SELECT * FROM unidade;

CREATE TABLE est_andamento(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(22) NOT NULL
);

INSERT INTO est_andamento(nome) VALUES 
	("Em aberto"),
	("Aguardando pagamento"),
	("Pago"),
	("Em preparo/separação"),
	("Aguardando envio"),
	("Aguardando retirada"),
	("Saiu para entrega"),
	("Concluído"),
	("Cancelado");

SELECT * FROM est_andamento;



