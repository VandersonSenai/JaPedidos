DROP DATABASE IF EXISTS `japedidos`;
CREATE DATABASE `japedidos`;

USE `japedidos`;

--
-- Table structure for table `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
INSERT INTO `categoria` (`id`, `nome`, `descricao`) VALUES (1,'Bebida','Produtos como refrigerantes, água, sucos.'),
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
UNLOCK TABLES;


--
-- Table structure for table `unidade`
--

CREATE TABLE `unidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(16) NOT NULL,
  `abreviacao` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
INSERT INTO `unidade` (`id`, `nome`, `abreviacao`) VALUES (1,'Unidade','UND'),
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
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(48) NOT NULL,
  `login` varchar(20) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `tipo` enum('administrador','atendente') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` (`id`, `nome`, `login`, `senha`, `tipo`) VALUES (1,'Administrador','admin','admin','administrador');
UNLOCK TABLES;


--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `id_usuario_alt` int(11) DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  `dthr_alt` datetime DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  PRIMARY KEY (`id`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `est_andamento`
--

CREATE TABLE `est_andamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `est_andamento`
--

LOCK TABLES `est_andamento` WRITE;
INSERT INTO `est_andamento` (`id`, `nome`) VALUES (1,'Em aberto'),
(2,'Aguardando pagamento'),
(3,'Pago'),
(4,'Em preparo/separação'),
(5,'Aguardando envio'),
(6,'Aguardando retirada'),
(7,'Saiu para entrega'),
(8,'Concluído'),
(9,'Cancelado');
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

CREATE TABLE `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) NOT NULL,
  `id_usuario_autor` int(11) NOT NULL,
  `dthr_criacao` datetime NOT NULL DEFAULT current_timestamp(),
  `id_usuario_alt` int(11) DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados\nAo haver alteração em qualquer informação do pedido, deverá haver o registro do autor da alteração e do horário. Alterações no estado do pedido serão registrados independentemente\n',
  `dthr_alt` datetime DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados\nAo haver alteração em qualquer informação do pedido, deverá haver o registro do autor da alteração e do horário. Alterações no estado do pedido serão registrados independentemente\n',
  `tipo_entrega` enum('ENVIO','RETIRADA') NOT NULL,
  `dthr_entregar` datetime NOT NULL,
  `preco_frete` decimal(8,2) NOT NULL DEFAULT 0.00,
  `tx_desconto` int(11) NOT NULL DEFAULT 0,
  `preco_final` decimal(10,2) NOT NULL,
  `dt_venc_pagamento` date DEFAULT NULL,
  `dt_pago` date DEFAULT NULL,
  `preco_custo_total` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`,`id_cliente`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


--
-- Table structure for table `produto`
--

CREATE TABLE `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `id_unidade` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL,
  `preco_venda` decimal(8,2) NOT NULL DEFAULT 0.00,
  `preco_custo` decimal(8,2) NOT NULL DEFAULT 0.00,
  `id_usuario_alt` int(11) DEFAULT NULL,
  `dthr_alt` datetime DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT  b'1',
  PRIMARY KEY (`id`,`id_unidade`,`id_categoria`),
  KEY `id_categoria` (`id_categoria`),
  KEY `id_unidade` (`id_unidade`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `produto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `produto_ibfk_2` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id`),
  CONSTRAINT `produto_ibfk_3` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
INSERT INTO `produto` (`id`, `id_categoria`, `id_unidade`, `nome`, `preco_venda`, `preco_custo`, `id_usuario_alt`, `dthr_alt`, `estado`)
VALUES
  (1,4,8,'SORVETE LUIGI 1,5lt',19.50,12.45,NULL,NULL, b'1'),
  (2,4,8,'SORVETE LUIGI 1,7lt',21.75,14.15,NULL,NULL, b'1'),
  (3,4,8,'AÇAI 1,2lt',18.75,13.15,NULL,NULL, b'1'),
  (4,1,11,'COCA-COLA 2lts',14.00,9.00,NULL,NULL, b'1'),
  (5,1,11,'COCA-COLA 600ml',9.00,6.50,NULL,NULL, b'1'),
  (6,1,11,'GUARANA ANTARTICA 2lts	',14.00,9.00,NULL,NULL, b'1'),
  (7,1,11,'GUARANA ANTARTICA 600ml',9.00,6.50,NULL,NULL, b'1'),
  (8,1,11,'HEINIKEN 600lm',16.50,10.50,NULL,NULL, b'1'),
  (9,1,11,'FANTA UVA 2lT',14.00,9.50,NULL,NULL, b'1'),
  (10,5,1,'COXINHA DE FRANGO',0.75,0.15,NULL,NULL, b'1'),
  (11,5,1,'RIZOLE DE PIZZA',0.75,0.15,NULL,NULL, b'1'),
  (12,5,1,'BOLINHA DE QUEIJO',0.75,0.15,NULL,NULL, b'1'),
  (13,5,1,'RISSOLE DE CAMARÃO',0.75,0.15,NULL,NULL, b'1'),
  (14,5,2,'COXINHA DE FRANGO',75.00,15.00,NULL,NULL, b'1'),
  (15,5,2,'RIZOLE DE PIZZA',75.00,15.00,NULL,NULL, b'1'),
  (16,5,2,'BOLINHA DE QUEIJO',75.00,15.00,NULL,NULL, b'1'),
  (17,5,2,'KIBE DE CARNE',75.00,15.00,NULL,NULL, b'1'),
  (18,5,2,'RISSOLE DE CAMARÃO',75.00,15.00,NULL,NULL, b'1'),
  (19,5,1,'MINI CHURROS',0.75,0.15,NULL,NULL, b'1'),
  (20,5,1,'ENROLADO DE SALSICHA ',0.75,0.15,NULL,NULL, b'1');
UNLOCK TABLES;

--
-- Table structure for table `produto_pedido`
--

CREATE TABLE `produto_pedido` (
  `id_produto` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `preco_venda` decimal(8,2) NOT NULL,
  `preco_custo` decimal(8,2) NOT NULL,
  `info_adicional` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_produto`,`id_pedido`),
  KEY `id_pedido` (`id_pedido`),
  CONSTRAINT `produto_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `produto_pedido_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;



--
-- Table structure for table `destinatario`
--
CREATE TABLE `destinatario` (
  `id_pedido` int(11) NOT NULL,
  `info` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `destinatario_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `destino`
--

CREATE TABLE `destino` (
  `id_pedido` int(11) NOT NULL,
  `logradouro` varchar(45) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `pais` varchar(45) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `destino_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


--
-- Table structure for table `est_andamento_pedido`
--

CREATE TABLE `est_andamento_pedido` (
  `id_est_andamento` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_usuario_autor` int(11) NOT NULL COMMENT 'Usuário responsável pela atribuição do estado ao pedido',
  `dthr_criacao` datetime NOT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  PRIMARY KEY (`id_est_andamento`,`id_pedido`),
  KEY `id_pedido` (`id_pedido`),
  CONSTRAINT `est_andamento_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `est_andamento_pedido_ibfk_2` FOREIGN KEY (`id_est_andamento`) REFERENCES `est_andamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `info_cancelamento`
--

CREATE TABLE `info_cancelamento` (
  `id_pedido` int(11) NOT NULL,
  `justificativa` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_cancelamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `info_pagamento`
--

CREATE TABLE `info_pagamento` (
  `id_pedido` int(11) NOT NULL,
  `dt_vencimento` date DEFAULT NULL,
  `dt_pago` date DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pagamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `info_pf`
--

CREATE TABLE `info_pf` (
  `id_pedido` int(11) NOT NULL,
  `nome_cliente` varchar(45) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pf_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `info_pj`
--

CREATE TABLE `info_pj` (
  `id_pedido` int(11) NOT NULL,
  `cnpj` varchar(20) NOT NULL,
  `nome_fantasia` varchar(60) NOT NULL,
  `nome_empresarial` varchar(60) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pj_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Table structure for table `intercorrencia`
--

CREATE TABLE `intercorrencia` (
  `id_intercorrencia` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `dthr_criacao` datetime NOT NULL DEFAULT current_timestamp(),
  `id_usuario_autor` int(11) NOT NULL,
  PRIMARY KEY (`id_intercorrencia`,`id_pedido`,`id_usuario_autor`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_usuario_autor` (`id_usuario_autor`),
  CONSTRAINT `intercorrencia_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `intercorrencia_ibfk_2` FOREIGN KEY (`id_usuario_autor`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


--
-- Table structure for view `vw_ultimo_estado_pedido`
--


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
        (SELECT
                id_pedido,
                id_est_andamento,
                id_usuario_autor,
                dthr_criacao,
                MAX(dthr_criacao) OVER (PARTITION BY id_pedido) AS dthr_ultimo
            FROM est_andamento_pedido
        ) AS ultimo_estado_pedido
        INNER JOIN est_andamento AS e_a
            ON e_a.id = ultimo_estado_pedido.id_est_andamento
        INNER JOIN usuario AS u
            ON u.id = ultimo_estado_pedido.id_usuario_autor
    WHERE dthr_criacao = dthr_ultimo; -- Exibe apenas os estados de cada pedido que sejam os últimos (tenham data de criação igual à ultima encontrada para cada pedido)

--
-- Table structure for view `vw_pedido`
--

CREATE OR REPLACE VIEW vw_pedido AS
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
        u_e_p.dthr_criacao_est AS dthr_criacao_ultimo_est,
        -- Cancelamento
        info_canc.justificativa AS info_cancelamento
    FROM
        pedido AS p
        -- Informações básicas do pedido
        INNER JOIN usuario AS u
            ON u.id = p.id_usuario_autor
        LEFT JOIN usuario AS u_alt
            ON u_alt.id = p.id_usuario_alt
        INNER JOIN cliente AS c
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
        INNER JOIN vw_ultimo_estado_pedido AS u_e_p
            ON u_e_p.id_pedido = p.id
        -- Info de cancelamento
        LEFT JOIN info_cancelamento AS info_canc ON info_canc.id_pedido = p.id
    ORDER BY p.id;


--
-- Table structure for view `listaTodosProdutos`
--

CREATE VIEW `listaTodosProdutos` AS
  select
  `p`.`id` AS `id`,
  `p`.`nome` AS `nome`,
  `c`.`nome` AS `categoria`,
  `p`.`preco_venda` AS `preco_venda`,
  `p`.`preco_custo` AS `preco_custo`,
  `u`.`abreviacao` AS `unidade`,
  `p`.`estado` AS `estado`
  from ((`produto` `p`
    left join `categoria` `c` on(`p`.`id_categoria` = `c`.`id`))
    left join `unidade` `u` on(`p`.`id_unidade` = `u`.`id`));

--
-- Table structure for view `vw_estadoProduto`
--

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


--
-- Table structure for view `vw_produtos_pedido`
--

CREATE OR REPLACE VIEW vw_produtos_pedido
AS SELECT
    prod_ped.id_pedido AS id_pedido,
    prod_ped.id_produto,
    prod.nome AS nome_produto,
    prod_ped.quantidade AS quantidade_produto,
    prod_ped.info_adicional AS info_adicional_produto_pedido,
    prod.id_categoria,
    cat.nome AS nome_categoria,
    cat.descricao AS descricao_categoria,
    prod.id_unidade,
    unid.nome AS nome_unidade,
    unid.abreviacao AS abreviacao_unidade,
    prod_ped.preco_venda,
    prod_ped.preco_custo,
    prod.id_usuario_alt,
    usr.nome AS nome_usuario_alt,
    usr.tipo AS tipo_usuario,
    prod.dthr_alt,
    prod.estado
    FROM produto_pedido AS prod_ped
        INNER JOIN produto AS prod ON prod.id = prod_ped.id_produto
        INNER JOIN categoria AS cat ON cat.id = prod.id_categoria
        INNER JOIN unidade AS unid ON unid.id = prod.id_unidade
        LEFT JOIN usuario AS usr ON usr.id = prod.id_usuario_alt;

-- Seleciona todos os produtos a partir do id do pedido em questão.

DELIMITER //
CREATE PROCEDURE select_produtos_pedido(IN id_ped INT) READS SQL DATA
    BEGIN
        SELECT * FROM vw_produtos_pedido WHERE id_pedido = id_ped;
    END
//
DELIMITER ;

-- Seleciona todos os pedidos a partir do id do estado em questão.
DELIMITER //
CREATE OR REPLACE PROCEDURE select_pedidos_by_estado(IN id_est INT) READS SQL DATA
BEGIN
  SELECT * FROM vw_pedido WHERE id_ultimo_est = id_est;
END
//
DELIMITER ;
