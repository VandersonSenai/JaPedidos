CREATE DATABASE  IF NOT EXISTS `titanw25_japedidos_hml` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `titanw25_japedidos_hml`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: titanw25_japedidos_hml
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Bebida','Produtos como refrigerantes, água, sucos.'),(2,'Lanche','Produtos como hambúrger e similares.'),(3,'Fritas','Produtos com batata-frita.'),(4,'Sorvete','Produtos com sorvete.'),(5,'Salgado','Produtos como coxinha, enrroladinho e outros.'),(6,'Bolo',NULL),(7,'Pão',NULL),(8,'Torta',NULL),(9,'Refeição',NULL),(10,'Bala','Produtos doces e pequenos.'),(11,'Gelatina',NULL);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_andamento`
--

DROP TABLE IF EXISTS `est_andamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `est_andamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(22) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_andamento`
--

LOCK TABLES `est_andamento` WRITE;
/*!40000 ALTER TABLE `est_andamento` DISABLE KEYS */;
INSERT INTO `est_andamento` VALUES (1,'Em aberto'),(2,'Aguardando pagamento'),(3,'Pago'),(4,'Em preparo/separação'),(5,'Aguardando envio'),(6,'Aguardando retirada'),(7,'Saiu para entrega'),(8,'Concluído'),(9,'Cancelado');
/*!40000 ALTER TABLE `est_andamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `listatodosprodutos`
--

DROP TABLE IF EXISTS `listatodosprodutos`;
/*!50001 DROP VIEW IF EXISTS `listatodosprodutos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `listatodosprodutos` AS SELECT 
 1 AS `id`,
 1 AS `nome`,
 1 AS `categoria`,
 1 AS `preco_venda`,
 1 AS `preco_custo`,
 1 AS `unidade`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `id_unidade` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL,
  `preco_venda` decimal(8,2) NOT NULL DEFAULT 0.00,
  `preco_custo` decimal(8,2) NOT NULL DEFAULT 0.00,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `id_usuario_alt` int(11) DEFAULT NULL,
  `dthr_alt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_categoria` (`id_categoria`),
  KEY `id_unidade` (`id_unidade`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `produto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `produto_ibfk_2` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`id`),
  CONSTRAINT `produto_ibfk_3` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (9,5,1,'COXINHA DE FRANGO',0.75,0.15,_binary '',1,NULL),(10,5,1,'RIZOLE DE PIZZA',0.75,0.15,_binary '',1,NULL),(11,5,1,'BOLINHA DE QUEIJO',100.75,90.15,_binary '',1,NULL),(12,5,1,'KIBE DE CARNE',0.75,0.15,_binary '',1,NULL),(13,5,1,'RISSOLE DE CAMARÃO',0.75,0.15,_binary '',1,NULL),(14,5,1,'MINI CHURROS',0.75,0.15,_binary '',1,NULL),(15,5,1,'ENROLADO DE SALSICHA ',0.75,0.15,_binary '',1,NULL),(16,1,11,'COCA-COLA 2lts',14.00,9.00,_binary '',1,NULL),(17,1,11,'COCA-COLA 600ml',9.00,6.50,_binary '',1,NULL),(18,1,11,'GUARANA ANTARTICA 2lt',14.00,9.00,_binary '',1,NULL),(19,1,11,'GUARANA ANTARTICA 600ml',9.00,6.50,_binary '',1,NULL),(20,1,11,'HEINIKEN 600lm',16.50,10.50,_binary '',1,NULL),(21,1,11,'FANTA UVA 2lt',14.00,9.50,_binary '',1,NULL),(22,5,8,'SORVETE LUIGI 1,5lt',19.50,12.45,_binary '',2,NULL),(23,5,8,'SORVETE LUIGI 1,7lt',21.00,14.15,_binary '',2,NULL),(24,4,8,'AÇAI 1,2lt',1000.00,9999.09,_binary '\0',2,NULL);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade`
--

DROP TABLE IF EXISTS `unidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(16) NOT NULL,
  `abreviacao` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade`
--

LOCK TABLES `unidade` WRITE;
/*!40000 ALTER TABLE `unidade` DISABLE KEYS */;
INSERT INTO `unidade` VALUES (1,'Unidade','UND'),(2,'Cento','CENTO'),(3,'Dúzia','DUZIA'),(4,'Pacote','PCT'),(5,'Caixa','CX'),(6,'Grama','G'),(7,'Miligrama','MG'),(8,'Litros','L'),(9,'Mililitros','ML'),(10,'Kilograma','KG'),(11,'Garrafa','GRFA'),(12,'Pote','POTE');
/*!40000 ALTER TABLE `unidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(48) NOT NULL,
  `login` varchar(20) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `tipo` enum('atendente','administrador') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Thiago M. Baiense','thiago','baiense','administrador'),(2,'Vanderson da Silva','vanderson','silva','administrador');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `listatodosprodutos`
--

/*!50001 DROP VIEW IF EXISTS `listatodosprodutos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `listatodosprodutos` AS select `p`.`id` AS `id`,`p`.`nome` AS `nome`,`c`.`nome` AS `categoria`,`p`.`preco_venda` AS `preco_venda`,`p`.`preco_custo` AS `preco_custo`,`u`.`abreviacao` AS `unidade`,`p`.`estado` AS `estado` from ((`produto` `p` left join `categoria` `c` on(`p`.`id_categoria` = `c`.`id`)) left join `unidade` `u` on(`p`.`id_unidade` = `u`.`id`)) */;
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

-- Dump completed on 2024-11-06 17:19:15
