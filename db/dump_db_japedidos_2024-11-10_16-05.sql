-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: japedidos
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

/*!40000 DROP DATABASE IF EXISTS `japedidos`*/;

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES
(1,'Josefina Andrade','40028922',NULL,NULL),
(2,'Thiago Moura Baiense','40028922',NULL,NULL),
(3,'Maria Joaquina','423109433',NULL,NULL),
(4,'Antônio Andrade','99957586',NULL,NULL),
(5,'Roberto Vieira de Andrade','33365759',NULL,NULL),
(6,'Tainá da Silva Schmith','40028989',NULL,NULL),
(9,'Primeiro Cliente','11111111',NULL,NULL),
(10,'Segundo Cliente','22222222',NULL,NULL),
(11,'Terceiro Cliente','33333333',NULL,NULL),
(12,'Quarto Cliente','44444444',NULL,NULL),
(13,'Quinto Cliente','55555555',NULL,NULL),
(14,'Sexto Cliente','66666666',NULL,NULL),
(17,'Sétimo Cliente','7777777777',NULL,NULL),
(18,'Oitavo Cliente','888888888',NULL,NULL);
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
INSERT INTO `destinatario` VALUES
(17,'Dados destinatário, ponto de referência...'),
(20,'Dados destinatário, ponto de referência...'),
(22,'Dados destinatário, ponto de referência...'),
(26,'Dados destinatário, ponto de referência...');
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
INSERT INTO `destino` VALUES
(17,'Rua Primeira','1','Bairro Primerio','Cidade Primeira','ES','Brasil'),
(19,'Rua Terceira','3','Bairro Terceiro','Cidade Terceira','ES','Brasil'),
(20,'Rua Quatro','4','Bairro Quatro','Cidade Quarta','ES','Brasil'),
(26,'Rua oito','8','Bairro oito','Cidade oito','RJ','Brasil');
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
INSERT INTO `est_andamento` VALUES
(1,'Em aberto'),
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
INSERT INTO `est_andamento_pedido` VALUES
(1,25,3,'2024-11-10 15:46:26'),
(2,26,3,'2024-11-10 15:54:04');
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
INSERT INTO `info_pf` VALUES
(20,'Pessoa Aleatória','92288496061'),
(26,'Maria Sntônia','92288496061');
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
INSERT INTO `info_pj` VALUES
(21,'5616516651','Empresa Legal','EMPRESA LEGAL LTDA');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES
(1,1,3,'2024-11-09 13:38:04',NULL,NULL,'ENVIO','2024-11-09 13:38:04',0.01,0,15.51,NULL,NULL,9.30),
(2,1,3,'2024-11-09 13:42:58',NULL,NULL,'ENVIO','2024-11-09 13:42:58',0.10,0,56.35,NULL,NULL,39.45),
(3,1,3,'2024-11-09 13:46:59',NULL,NULL,'RETIRADA','2024-11-09 13:46:59',0.00,0,57.00,NULL,NULL,39.60),
(4,1,3,'2024-11-09 13:51:33',NULL,NULL,'RETIRADA','2024-11-09 13:51:33',5.00,0,44.75,NULL,NULL,26.75),
(5,1,3,'2024-11-10 00:52:18',NULL,NULL,'ENVIO','2024-11-10 00:52:18',0.00,0,46.75,NULL,NULL,31.15),
(6,1,3,'2024-11-10 00:54:17',NULL,NULL,'ENVIO','2024-11-10 00:54:17',0.00,0,46.75,NULL,NULL,31.15),
(7,1,3,'2024-11-10 01:06:17',NULL,NULL,'ENVIO','2024-11-10 01:06:17',0.00,0,96.50,NULL,NULL,62.90),
(8,1,3,'2024-11-10 01:09:08',NULL,NULL,'ENVIO','2024-11-10 01:09:08',0.00,0,163.25,NULL,NULL,109.15),
(10,2,3,'2024-11-10 13:02:26',NULL,NULL,'ENVIO','2024-11-10 13:02:26',0.00,0,191.25,NULL,NULL,132.25),
(11,3,3,'2024-11-10 13:05:27',NULL,NULL,'ENVIO','2024-11-10 13:05:27',0.00,0,192.00,NULL,NULL,132.40),
(12,4,3,'2024-11-10 13:10:00',NULL,NULL,'ENVIO','2024-11-10 13:10:00',15.00,0,206.25,NULL,NULL,132.25),
(13,5,3,'2024-11-10 13:11:47',NULL,NULL,'RETIRADA','2024-11-10 13:11:47',10.00,5,24.75,NULL,NULL,9.15),
(14,6,3,'2024-11-10 13:15:38',NULL,NULL,'ENVIO','2024-11-10 13:15:38',25.00,5,203.13,NULL,NULL,131.50),
(17,9,3,'2024-11-10 13:50:30',NULL,NULL,'ENVIO','2024-11-10 13:50:30',0.00,0,206.25,NULL,NULL,144.65),
(18,10,3,'2024-11-10 13:53:21',NULL,NULL,'RETIRADA','2024-11-10 13:53:21',20.00,15,202.96,NULL,NULL,151.15),
(19,11,3,'2024-11-10 13:56:59',NULL,NULL,'ENVIO','2024-11-10 13:56:59',0.00,0,140.25,NULL,NULL,60.95),
(20,12,3,'2024-11-10 14:48:12',NULL,NULL,'ENVIO','2024-11-10 14:48:12',0.00,0,337.50,NULL,NULL,194.40),
(21,13,3,'2024-11-10 14:51:06',NULL,NULL,'RETIRADA','2024-11-10 14:51:06',15.00,10,319.43,NULL,NULL,194.55),
(22,14,3,'2024-11-10 14:56:33',NULL,NULL,'RETIRADA','2024-11-10 14:56:33',0.00,0,109.75,NULL,NULL,71.75),
(25,17,3,'2024-11-10 15:46:26',NULL,NULL,'RETIRADA','2024-11-10 15:46:26',15.00,10,179.48,NULL,NULL,89.75),
(26,18,3,'2024-11-10 15:54:04',NULL,NULL,'ENVIO','2024-11-10 15:54:04',15.00,10,68.33,NULL,NULL,35.35);
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
INSERT INTO `produto` VALUES
(1,5,8,'SORVETE LUIGI 1,5lt',19.50,12.45,2,NULL,''),
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
INSERT INTO `produto_pedido` VALUES
(3,6,1,18.75,13.15,NULL),
(3,7,2,18.75,13.15,NULL),
(3,8,5,18.75,13.15,NULL),
(3,10,10,18.75,13.15,NULL),
(3,11,10,18.75,13.15,NULL),
(3,12,10,18.75,13.15,NULL),
(3,14,10,18.75,13.15,NULL),
(3,17,11,18.75,13.15,NULL),
(3,18,11,18.75,13.15,NULL),
(3,19,3,18.75,13.15,NULL),
(3,20,11,18.75,13.15,NULL),
(3,21,11,18.75,13.15,NULL),
(3,22,2,18.75,13.15,NULL),
(3,25,5,18.75,13.15,NULL),
(4,6,2,14.00,9.00,NULL),
(4,7,4,14.00,9.00,NULL),
(4,8,4,14.00,9.00,NULL),
(4,13,1,14.00,9.00,NULL),
(4,22,5,14.00,9.00,NULL),
(4,25,1,14.00,9.00,NULL),
(5,8,1,9.00,6.50,NULL),
(5,18,1,9.00,6.50,NULL),
(5,19,1,9.00,6.50,NULL),
(5,20,5,9.00,6.50,NULL),
(5,21,5,9.00,6.50,NULL),
(5,26,5,9.00,6.50,NULL),
(10,7,1,0.75,0.15,NULL),
(10,8,1,0.75,0.15,NULL),
(10,13,1,0.75,0.15,NULL),
(10,19,100,0.75,0.15,NULL),
(10,22,2,0.75,0.15,NULL),
(10,26,9,0.75,0.15,NULL),
(12,7,1,0.75,0.15,NULL),
(12,8,2,0.75,0.15,NULL),
(12,20,5,0.75,0.15,NULL),
(12,21,5,0.75,0.15,NULL),
(14,7,1,0.75,0.15,NULL),
(14,8,1,0.75,0.15,NULL),
(14,20,10,0.75,0.15,NULL),
(14,21,10,0.75,0.15,NULL),
(16,7,1,0.75,0.15,NULL),
(16,8,1,0.75,0.15,NULL),
(16,10,5,0.75,0.15,NULL),
(16,11,6,0.75,0.15,NULL),
(16,21,1,0.75,0.15,NULL),
(16,22,1,0.75,0.15,NULL),
(16,26,10,0.75,0.15,NULL),
(20,8,1,0.75,0.15,NULL),
(20,12,5,0.75,0.15,NULL),
(20,20,100,0.75,0.15,NULL),
(20,21,100,0.75,0.15,NULL),
(20,25,100,0.75,0.15,NULL);
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
INSERT INTO `usuario` VALUES
(1,'Valentin Gomes','valentin','admin','administrador'),
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
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
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
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
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

-- Dump completed on 2024-11-10 16:09:53
