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

CREATE TABLE produto(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_categoria INT NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria(id),
	id_unidade INT NOT NULL,
	FOREIGN KEY (id_unidade) REFERENCES unidade(id),
	nome VARCHAR(32) NOT NULL,
	preco_venda DECIMAL(8,2) NOT NULL DEFAULT 0,
	preco_custo DECIMAL(8,2) NOT NULL DEFAULT 0,
	estado BIT(1) NOT NULL DEFAULT b'1',
	id_usuario_alt INT,
	dthr_alt DATETIME
);

INSERT INTO 
	produto(id_categoria, id_unidade, nome, preco_venda)
VALUE
	(52, 13, "Primeiro produto :D", 25.00);



