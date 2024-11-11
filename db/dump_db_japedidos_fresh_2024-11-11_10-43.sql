-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 10.0.0.109    Database: japedidos
-- ------------------------------------------------------
-- Server version	10.11.6-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `japedidos`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `japedidos` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;

USE `japedidos`;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `id_usuario_alt` int(11) DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  `dthr_alt` datetime DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  PRIMARY KEY (`id`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`id`, `nome`, `telefone`, `id_usuario_alt`, `dthr_alt`) VALUES (1,'Josefina Andrade','40028922',NULL,NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destinatario`
--

DROP TABLE IF EXISTS `destinatario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destinatario` (
  `id_pedido` int(11) NOT NULL,
  `info` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `destinatario_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinatario`
--

LOCK TABLES `destinatario` WRITE;
/*!40000 ALTER TABLE `destinatario` DISABLE KEYS */;
/*!40000 ALTER TABLE `destinatario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destino`
--

DROP TABLE IF EXISTS `destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destino`
--

LOCK TABLES `destino` WRITE;
/*!40000 ALTER TABLE `destino` DISABLE KEYS */;
/*!40000 ALTER TABLE `destino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_andamento`
--

DROP TABLE IF EXISTS `est_andamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_andamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_andamento`
--

LOCK TABLES `est_andamento` WRITE;
/*!40000 ALTER TABLE `est_andamento` DISABLE KEYS */;
INSERT INTO `est_andamento` (`id`, `nome`) VALUES (1,'Em aberto'),
(2,'Aguardando pagamento'),
(3,'Pago'),
(4,'Em preparo/separação'),
(5,'Aguardando envio'),
(6,'Aguardando retirada'),
(7,'Saiu para entrega'),
(8,'Concluído'),
(9,'Cancelado');
/*!40000 ALTER TABLE `est_andamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_andamento_pedido`
--

DROP TABLE IF EXISTS `est_andamento_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_andamento_pedido`
--

LOCK TABLES `est_andamento_pedido` WRITE;
/*!40000 ALTER TABLE `est_andamento_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_andamento_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_cancelamento`
--

DROP TABLE IF EXISTS `info_cancelamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_cancelamento` (
  `id_pedido` int(11) NOT NULL,
  `justificativa` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_cancelamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_cancelamento`
--

LOCK TABLES `info_cancelamento` WRITE;
/*!40000 ALTER TABLE `info_cancelamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_cancelamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_pagamento`
--

DROP TABLE IF EXISTS `info_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_pagamento` (
  `id_pedido` int(11) NOT NULL,
  `dt_vencimento` date DEFAULT NULL,
  `dt_pago` date DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pagamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_pagamento`
--

LOCK TABLES `info_pagamento` WRITE;
/*!40000 ALTER TABLE `info_pagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_pf`
--

DROP TABLE IF EXISTS `info_pf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_pf` (
  `id_pedido` int(11) NOT NULL,
  `nome_cliente` varchar(45) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pf_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_pf`
--

LOCK TABLES `info_pf` WRITE;
/*!40000 ALTER TABLE `info_pf` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_pf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_pj`
--

DROP TABLE IF EXISTS `info_pj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_pj` (
  `id_pedido` int(11) NOT NULL,
  `cnpj` varchar(20) NOT NULL,
  `nome_fantasia` varchar(60) NOT NULL,
  `nome_empresarial` varchar(60) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pj_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_pj`
--

LOCK TABLES `info_pj` WRITE;
/*!40000 ALTER TABLE `info_pj` DISABLE KEYS */;
/*!40000 ALTER TABLE `info_pj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intercorrencia`
--

DROP TABLE IF EXISTS `intercorrencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intercorrencia`
--

LOCK TABLES `intercorrencia` WRITE;
/*!40000 ALTER TABLE `intercorrencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `intercorrencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `listaTodosProdutos`
--

DROP TABLE IF EXISTS `listaTodosProdutos`;
/*!50001 DROP VIEW IF EXISTS `listaTodosProdutos`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `listaTodosProdutos` AS SELECT
 1 AS `id`,
  1 AS `nome`,
  1 AS `categoria`,
  1 AS `preco_venda`,
  1 AS `preco_custo`,
  1 AS `unidade`,
  1 AS `estado` */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `id_unidade` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL,
  `preco_venda` decimal(8,2) NOT NULL DEFAULT 0.00,
  `preco_custo` decimal(8,2) NOT NULL DEFAULT 0.00,
  `id_usuario_alt` int(11) DEFAULT NULL,
  `dthr_alt` datetime DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`,`id_unidade`,`id_categoria`),
  KEY `id_categoria` (`id_categoria`),
  KEY `id_unidade` (`id_unidade`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `produto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `produto_ibfk_2` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id`),
  CONSTRAINT `produto_ibfk_3` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`id`, `id_categoria`, `id_unidade`, `nome`, `preco_venda`, `preco_custo`, `id_usuario_alt`, `dthr_alt`, `estado`) VALUES (1,5,8,'SORVETE LUIGI 1,5lt',19.50,12.45,2,NULL,''),
(2,5,8,'SORVETE LUIGI 1,7lt',21.75,14.15,2,NULL,''),
(3,5,8,'AÇAI 1,2lt',18.75,13.15,2,NULL,''),
(4,1,11,'COCA-COLA 2lts',14.00,9.00,1,NULL,''),
(5,1,11,'COCA-COLA 600ml',9.00,6.50,1,NULL,''),
(6,1,11,'GUARANA ANTARTICA 2lts	',14.00,9.00,1,NULL,''),
(7,1,11,'GUARANA ANTARTICA 600ml',9.00,6.50,1,NULL,''),
(8,1,11,'HEINIKEN 600lm',16.50,10.50,1,NULL,''),
(9,1,11,'FANTA UVA 2lT',14.00,9.50,1,NULL,''),
(10,4,1,'COXINHA DE FRANGO',0.75,0.15,1,NULL,''),
(11,4,1,'RIZOLE DE PIZZA',0.75,0.15,1,NULL,''),
(12,4,1,'BOLINHA DE QUEIJO',0.75,0.15,1,NULL,''),
(13,4,1,'RISSOLE DE CAMARÃO',0.75,0.15,1,NULL,''),
(14,5,1,'COXINHA DE FRANGO',0.75,0.15,1,NULL,''),
(15,5,1,'RIZOLE DE PIZZA',0.75,0.15,1,NULL,''),
(16,5,1,'BOLINHA DE QUEIJO',0.75,0.15,1,NULL,''),
(17,5,1,'KIBE DE CARNE',0.75,0.15,1,NULL,''),
(18,5,1,'RISSOLE DE CAMARÃO',0.75,0.15,1,NULL,''),
(19,5,1,'MINI CHURROS',0.75,0.15,1,NULL,''),
(20,5,1,'ENROLADO DE SALSICHA ',0.75,0.15,1,NULL,'');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto_pedido`
--

DROP TABLE IF EXISTS `produto_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto_pedido`
--

LOCK TABLES `produto_pedido` WRITE;
/*!40000 ALTER TABLE `produto_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade`
--

DROP TABLE IF EXISTS `unidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(16) NOT NULL,
  `abreviacao` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
/*!40000 ALTER TABLE `unidade` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `unidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(48) NOT NULL,
  `login` varchar(20) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `tipo` enum('administrador','atendente') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nome`, `login`, `senha`, `tipo`) VALUES (1,'Valentin Gomes','valentin','admin','administrador'),
(2,'Vanderson da Silva','vanderson','silva','administrador'),
(3,'Thiago M.','thiago','admin','administrador');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `vw_estadoProduto`
--

DROP TABLE IF EXISTS `vw_estadoProduto`;
/*!50001 DROP VIEW IF EXISTS `vw_estadoProduto`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_estadoProduto` AS SELECT
 1 AS `id`,
  1 AS `nome`,
  1 AS `categoria`,
  1 AS `unidade`,
  1 AS `estado` */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `vw_produtos_pedido`
--

DROP TABLE IF EXISTS `vw_produtos_pedido`;
/*!50001 DROP VIEW IF EXISTS `vw_produtos_pedido`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vw_produtos_pedido` AS SELECT
 1 AS `id_pedido`,
  1 AS `nome_produto`,
  1 AS `quantidade_produto` */;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `japedidos`
--

USE `japedidos`;

--
-- Final view structure for view `listaTodosProdutos`
--

/*!50001 DROP VIEW IF EXISTS `listaTodosProdutos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`10.0.0.%` SQL SECURITY DEFINER */
/*!50001 VIEW `listaTodosProdutos` AS select `p`.`id` AS `id`,`p`.`nome` AS `nome`,`c`.`nome` AS `categoria`,`p`.`preco_venda` AS `preco_venda`,`p`.`preco_custo` AS `preco_custo`,`u`.`abreviacao` AS `unidade`,`p`.`estado` AS `estado` from ((`produto` `p` left join `categoria` `c` on(`p`.`id_categoria` = `c`.`id`)) left join `unidade` `u` on(`p`.`id_unidade` = `u`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_estadoProduto`
--

/*!50001 DROP VIEW IF EXISTS `vw_estadoProduto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`10.0.0.%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_estadoProduto` AS select `p`.`id` AS `id`,`p`.`nome` AS `nome`,`c`.`nome` AS `categoria`,`u`.`nome` AS `unidade`,case when `p`.`estado` = 0x01 then 'ATIVO' else 'DESATIVADO' end AS `estado` from ((`produto` `p` join `categoria` `c` on(`c`.`id` = `p`.`id_categoria`)) join `unidade` `u` on(`u`.`id` = `p`.`id_unidade`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_produtos_pedido`
--

/*!50001 DROP VIEW IF EXISTS `vw_produtos_pedido`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`10.0.0.%` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_produtos_pedido` AS select `prod_ped`.`id_pedido` AS `id_pedido`,`prod`.`nome` AS `nome_produto`,`prod_ped`.`quantidade` AS `quantidade_produto` from (`produto_pedido` `prod_ped` join `produto` `prod` on(`prod`.`id` = `prod_ped`.`id_produto`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-11 10:48:56
