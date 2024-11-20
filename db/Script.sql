CREATE DATABASE  IF NOT EXISTS `titanw25_japedidos_hml`;
USE `titanw25_japedidos_hml`;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--
-- Dumping data for table `categoria`
--

ALTER TABLE `categoria` DISABLE KEYS;
INSERT INTO `categoria` VALUES (1,'Bebida','Produtos como refrigerantes, água, sucos.'),(2,'Lanche','Produtos como hambúrger e similares.'),(3,'Fritas','Produtos com batata-frita.'),(4,'Sorvete','Produtos com sorvete.'),(5,'Salgado','Produtos como coxinha, enrroladinho e outros.'),(6,'Bolo',NULL),(7,'Pão',NULL),(8,'Torta',NULL),(9,'Refeição',NULL),(10,'Bala','Produtos doces e pequenos.'),(11,'Gelatina',NULL);
ALTER TABLE `categoria` ENABLE KEYS;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `id_usuario_alt` int(11) DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  `dthr_alt` datetime DEFAULT NULL COMMENT 'Necessário para controle de alterações, conforme [RNF 06] Controle de alteração de dados',
  PRIMARY KEY (`id`),
  KEY `id_usuario_alt` (`id_usuario_alt`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_usuario_alt`) REFERENCES `usuario` (`id`)
)

--
-- Dumping data for table `cliente`
--

ALTER TABLE `cliente` DISABLE KEYS;
INSERT INTO `cliente` VALUES (1,'Yudy e Priscila','40028922',NULL,NULL),(2,'Thiago Moura','33365759',NULL,NULL),(3,'Maria Antônia Zeneide','33365758',NULL,NULL),(4,'Roberto Marinho','33875845',NULL,NULL),(5,'Mário Elias Silva','33335566',NULL,NULL),(6,'Maria Conceição da Barra','34395548',NULL,NULL),(7,'Gilberto Farias Júnio','27999449548',NULL,NULL),(8,'Miriã Dias','27999457878',NULL,NULL),(9,'Gisleide Andrade Pulquerio','27999147858',NULL,NULL),(11,'Tenário Antônio Márcio','27999456473',NULL,NULL),(12,'Richard Rodriges Meirelli','28998447868',NULL,NULL),(13,'Walmira Andrade Ritchtofen','11999694578',NULL,NULL),(14,'Antônio Márcio','27998485766',NULL,NULL),(15,'Roberto Marciano Gaulês','2833245968',NULL,NULL),(16,'Thiago Moura Baiense','27999483254',NULL,NULL),(17,'José María Trindade','27998875468',NULL,NULL),(18,'Rosemilda Andrade','40085922',NULL,NULL);
ALTER TABLE `cliente` ENABLE KEYS;

--
-- Table structure for table `destinatario`
--

DROP TABLE IF EXISTS `destinatario`;
CREATE TABLE `destinatario` (
  `id_pedido` int(11) NOT NULL,
  `info` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `destinatario_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
)

--
-- Dumping data for table `destinatario`
--

ALTER TABLE `destinatario` DISABLE KEYS;
INSERT INTO `destinatario` VALUES (1,'Dados destinatário, ponto de referência...'),(3,'Entregar ao porteiro'),(5,'Em cima da Padaria Renovada, ao lado do supermercado.'),(6,'Dados destinatário, ponto de referência...'),(16,'Entregar ao porteiro do condomínio'),(17,'Entregar em horário comercial'),(18,'Dados destinatário, ponto de referência...');
ALTER TABLE `destinatario` ENABLE KEYS;

--
-- Table structure for table `destino`
--

DROP TABLE IF EXISTS `destino`;
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
)

--
-- Dumping data for table `destino`
--

ALTER TABLE `destino` DISABLE KEYS;
INSERT INTO `destino` VALUES (1,'Rua Antônio Roberto Braga','2','Bairro Legal','Cidade Antiga','ES','Brasil'),(3,'Rua das Caieiras','79','Jacarandá','Carapicuíba','GO','Brasil'),(5,'Rua dos Anjos Mirins','32','Marataízes','Cidade Alta','RJ','Brasil'),(8,'Avenida São João','323','Vila Joana','Jundiaí','SP','Brasil'),(9,'Rua Serra de Bragança','454','Vila Gomes Cardim','São Paulo','ES','Brasil'),(11,'Rua Carlos Augusto Cornelsen','342','Bom Retiro','Curitiba','PR','Brasil'),(13,'Rua Serra de Bragança','32','Vila Gomes Cardim','São Paulo','SP','Brasil'),(16,'Rua das Mariposas Ciganas','213','Itamarajó','Capiúba','ES','Brasil'),(17,'Belmira Altares','232','Itararé','Campo Belo','RJ','Brasil'),(18,'Palmeiras Cintilantes','32','Planalto Mineiro','Carapicuíba','SC','Brasil');
ALTER TABLE `destino` ENABLE KEYS;

--
-- Table structure for table `est_andamento`
--

DROP TABLE IF EXISTS `est_andamento`;
CREATE TABLE `est_andamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(24) NOT NULL,
  PRIMARY KEY (`id`)
)

--
-- Dumping data for table `est_andamento`
--

ALTER TABLE `est_andamento` DISABLE KEYS;
INSERT INTO `est_andamento` VALUES (1,'Em aberto'),(2,'Aguardando pagamento'),(3,'Pago'),(4,'Em preparo/separação'),(5,'Aguardando envio'),(6,'Aguardando retirada'),(7,'Saiu para entrega'),(8,'Concluído'),(9,'Cancelado');
ALTER TABLE `est_andamento` ENABLE KEYS;

--
-- Table structure for table `est_andamento_pedido`
--

DROP TABLE IF EXISTS `est_andamento_pedido`;
CREATE TABLE `est_andamento_pedido` (
  `id_est_andamento` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_usuario_autor` int(11) NOT NULL COMMENT 'Usuário responsável pela atribuição do estado ao pedido',
  `dthr_criacao` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id_est_andamento`,`id_pedido`),
  KEY `id_pedido` (`id_pedido`),
  CONSTRAINT `est_andamento_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `est_andamento_pedido_ibfk_2` FOREIGN KEY (`id_est_andamento`) REFERENCES `est_andamento` (`id`)
)

--
-- Dumping data for table `est_andamento_pedido`
--

ALTER TABLE `est_andamento_pedido` DISABLE KEYS;
INSERT INTO `est_andamento_pedido` VALUES (1,1,1,'2024-11-15 15:21:32.000'),(1,2,1,'2024-11-16 11:37:28.000'),(1,3,1,'2024-11-14 18:45:10.000'),(1,4,1,'2024-11-14 18:45:10.000'),(1,5,1,'2024-11-16 14:09:29.000'),(1,6,1,'2024-11-16 14:14:23.000'),(1,7,1,'2024-11-16 14:23:05.000'),(1,8,1,'2024-11-16 14:35:59.000'),(1,9,1,'2024-11-16 14:36:33.000'),(1,11,1,'2024-11-16 14:38:36.000'),(1,12,1,'2024-11-16 14:39:36.000'),(1,13,1,'2024-11-16 14:40:42.000'),(1,14,1,'2024-11-16 14:42:04.000'),(1,15,1,'2024-11-17 11:57:45.000'),(1,16,1,'2024-11-17 12:05:10.000'),(1,17,1,'2024-11-18 08:58:27.543'),(1,18,1,'2024-11-18 16:27:56.001'),(2,1,1,'2024-11-16 11:17:43.000'),(2,6,1,'2024-11-16 14:14:40.000'),(2,9,1,'2024-11-16 14:43:15.000'),(2,12,1,'2024-11-16 14:42:52.000'),(3,1,1,'2024-11-16 11:22:23.000'),(3,2,1,'2024-11-16 11:37:53.000'),(3,3,1,'2024-11-14 18:45:10.000'),(3,4,1,'2024-11-16 13:09:00.000'),(3,5,1,'2024-11-16 14:09:53.000'),(3,6,1,'2024-11-16 14:16:12.000'),(3,7,1,'2024-11-16 14:23:17.000'),(3,12,1,'2024-11-16 14:43:28.000'),(3,14,1,'2024-11-16 14:43:54.000'),(3,15,1,'2024-11-18 19:54:14.878'),(3,16,1,'2024-11-17 16:04:26.836'),(4,1,1,'2024-11-16 11:27:26.000'),(4,14,1,'2024-11-16 14:43:54.000'),(4,15,1,'2024-11-18 19:54:14.885'),(5,1,1,'2024-11-16 11:27:47.000'),(5,4,1,'2024-11-16 13:09:00.000'),(6,7,1,'2024-11-16 14:23:38.000'),(7,1,1,'2024-11-16 11:27:54.000'),(7,2,1,'2024-11-16 11:38:58.000'),(7,4,1,'2024-11-16 13:12:08.000'),(7,5,1,'2024-11-16 14:12:12.000'),(7,6,1,'2024-11-16 14:16:40.000'),(8,1,1,'2024-11-16 13:07:53.000'),(8,2,1,'2024-11-16 13:07:53.000'),(8,3,1,'2024-11-16 13:07:53.000'),(8,5,1,'2024-11-16 14:12:29.000'),(8,6,1,'2024-11-16 14:21:46.000'),(9,4,1,'2024-11-16 13:13:11.000');
ALTER TABLE `est_andamento_pedido` ENABLE KEYS;

--
-- Table structure for table `info_cancelamento`
--

DROP TABLE IF EXISTS `info_cancelamento`;
CREATE TABLE `info_cancelamento` (
  `id_pedido` int(11) NOT NULL,
  `justificativa` varchar(120) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_cancelamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
)

--
-- Dumping data for table `info_cancelamento`
--

ALTER TABLE `info_cancelamento` DISABLE KEYS;
INSERT INTO `info_cancelamento` VALUES (4,'Infelizmente, o cliente não estava presente para receber o pedido.');
ALTER TABLE `info_cancelamento` ENABLE KEYS;

--
-- Table structure for table `info_pagamento`
--

DROP TABLE IF EXISTS `info_pagamento`;
CREATE TABLE `info_pagamento` (
  `id_pedido` int(11) NOT NULL,
  `dt_vencimento` date DEFAULT NULL,
  `dt_pago` date DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pagamento_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
)

--
-- Table structure for table `info_pf`
--

DROP TABLE IF EXISTS `info_pf`;
CREATE TABLE `info_pf` (
  `id_pedido` int(11) NOT NULL,
  `nome_cliente` varchar(45) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pf_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
)

--
-- Dumping data for table `info_pf`
--
ALTER TABLE `info_pf` DISABLE KEYS;
INSERT INTO `info_pf` VALUES (3,'Carlos Antônio Roberto','40237265087'),(14,'Antônio Márcio','53354825081');
ALTER TABLE `info_pf` ENABLE KEYS;

--
-- Table structure for table `info_pj`
--

DROP TABLE IF EXISTS `info_pj`;
CREATE TABLE `info_pj` (
  `id_pedido` int(11) NOT NULL,
  `cnpj` varchar(20) NOT NULL,
  `nome_fantasia` varchar(60) NOT NULL,
  `nome_empresarial` varchar(60) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  CONSTRAINT `info_pj_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
)


--
-- Table structure for table `intercorrencia`
--

DROP TABLE IF EXISTS `intercorrencia`;
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
)

--
-- Temporary view structure for view `listacategorias`
--

DROP TABLE IF EXISTS `listacategorias`;
CREATE OR REPLACE VIEW `listaCategorias` AS    
SELECT  * from categoria order by nome;

--
-- Temporary view structure for view `listatodosprodutos`
--

DROP TABLE IF EXISTS `listatodosprodutos`;
CREATE OR REPLACE VIEW `listaTodosProdutos` AS    
SELECT p.id, p.nome, c.nome as categoria, p.preco_venda , p.preco_custo, u.abreviacao as unidade, p.estado
FROM produto as p
LeFT JOIN
	categoria as c
    on p.id_categoria = c.id
LeFT JOIN
	unidade as u
    on p.id_unidade = u.id
ORDER BY p.nome;

--
-- Temporary view structure for view `listaunidades`
--

DROP TABLE IF EXISTS `listaunidades`;
CREATE OR REPLACE VIEW `listaUnidades` AS    
SELECT  * from unidade order BY nome;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
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
)

--
-- Dumping data for table `pedido`
--

ALTER TABLE `pedido` DISABLE KEYS;
INSERT INTO `pedido` VALUES (1,1,1,'2024-11-15 15:21:32',NULL,NULL,'ENVIO','2024-11-29 16:45:00',15.00,10,52.80,'2024-11-23','2024-11-16',27.00),(2,2,1,'2024-11-16 11:37:28',NULL,NULL,'RETIRADA','2024-11-22 18:00:00',0.00,5,155.80,NULL,'2024-11-16',39.00),(3,3,1,'2024-11-14 18:45:10',NULL,NULL,'ENVIO','2024-11-26 12:00:00',15.00,10,141.90,NULL,'2024-11-16',41.45),(4,4,1,'2024-11-14 18:45:10',NULL,NULL,'RETIRADA','2024-11-19 05:00:00',0.00,0,18.75,NULL,'2024-11-16',13.15),(5,5,1,'2024-11-16 14:09:29',NULL,NULL,'ENVIO','2024-11-16 14:00:00',0.00,10,264.60,NULL,'2024-11-16',93.00),(6,6,1,'2024-11-16 14:14:23',NULL,NULL,'RETIRADA','2024-11-27 18:00:00',15.00,0,146.25,'2024-11-30','2024-11-16',54.45),(7,7,1,'2024-11-16 14:23:05',NULL,NULL,'RETIRADA','2024-11-20 15:00:00',0.00,0,37.50,NULL,'2024-11-16',7.50),(8,8,1,'2024-11-16 14:35:59',NULL,NULL,'ENVIO','2024-11-16 14:00:00',0.00,0,95.25,NULL,NULL,28.45),(9,9,1,'2024-11-16 14:36:33',NULL,NULL,'ENVIO','2024-11-16 14:00:00',0.00,0,18.75,'2024-11-17',NULL,3.75),(11,11,1,'2024-11-16 14:38:36',1,'2024-11-19 20:17:21','ENVIO','2024-11-16 14:00:00',0.00,0,112.50,NULL,NULL,41.30),(12,12,1,'2024-11-16 14:39:36',NULL,NULL,'RETIRADA','2024-11-16 14:00:00',0.00,5,88.35,'2024-11-27','2024-11-16',28.00),(13,13,1,'2024-11-16 14:40:42',NULL,NULL,'ENVIO','2024-11-16 14:00:00',0.00,5,120.18,NULL,NULL,31.50),(14,14,1,'2024-11-16 14:42:04',NULL,NULL,'RETIRADA','2024-11-28 17:00:00',0.00,0,55.50,NULL,'2024-11-16',20.50),(15,15,1,'2024-11-17 11:57:45',NULL,NULL,'RETIRADA','2024-12-30 22:00:00',0.00,0,279.00,NULL,'2024-11-18',63.00),(16,16,1,'2024-11-17 12:05:10',NULL,NULL,'ENVIO','2024-11-18 12:45:00',20.00,15,163.44,NULL,'2024-11-17',43.15),(17,17,1,'2024-11-18 08:58:28',NULL,NULL,'ENVIO','2024-11-20 10:00:00',15.00,10,466.80,NULL,NULL,157.65),(18,18,1,'2024-11-18 16:27:56',NULL,NULL,'ENVIO','2024-11-24 16:00:00',0.00,10,12.60,NULL,NULL,9.00);
ALTER TABLE `pedido` ENABLE KEYS;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
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
)

--
-- Dumping data for table `produto`
--

ALTER TABLE `produto` DISABLE KEYS;
INSERT INTO `produto` VALUES (1,4,8,'SORVETE LUIGI 1,5lt',19.50,12.45,NULL,NULL,_binary ''),(2,4,8,'SORVETE LUIGI 1,7lt',21.75,14.15,NULL,NULL,_binary ''),(3,4,8,'AÇAI 1,2lt',18.75,13.15,NULL,NULL,_binary ''),(4,1,11,'COCA-COLA 2lts',14.00,9.00,NULL,NULL,_binary ''),(5,1,11,'COCA-COLA 600ml',9.00,6.50,NULL,NULL,_binary ''),(6,1,11,'GUARANA ANTARTICA 2lts	',14.00,9.00,NULL,NULL,_binary ''),(7,1,11,'GUARANA ANTARTICA 600ml',9.00,6.50,NULL,NULL,_binary ''),(8,1,11,'HEINIKEN 600lm',16.50,10.50,NULL,NULL,_binary ''),(9,1,11,'FANTA UVA 2lT',14.00,9.50,NULL,NULL,_binary ''),(10,5,1,'COXINHA DE FRANGO',0.75,0.15,NULL,NULL,_binary ''),(11,5,1,'RIZOLE DE PIZZA',0.75,0.15,NULL,NULL,_binary ''),(12,5,1,'BOLINHA DE QUEIJO',0.75,0.15,NULL,NULL,_binary ''),(13,5,1,'RISSOLE DE CAMARÃO',0.75,0.15,NULL,NULL,_binary ''),(14,5,2,'COXINHA DE FRANGO',75.00,15.00,NULL,NULL,_binary ''),(15,5,2,'RIZOLE DE PIZZA',75.00,15.00,NULL,NULL,_binary ''),(16,5,2,'BOLINHA DE QUEIJO',75.00,15.00,NULL,NULL,_binary ''),(17,5,2,'KIBE DE CARNE',75.00,15.00,NULL,NULL,_binary ''),(18,5,2,'RISSOLE DE CAMARÃO',75.00,15.00,NULL,NULL,_binary ''),(19,5,1,'MINI CHURROS',0.75,0.15,NULL,NULL,_binary ''),(20,5,1,'ENROLADO DE SALSICHA ',0.75,0.15,NULL,NULL,_binary '');
ALTER TABLE `produto` ENABLE KEYS;

--
-- Table structure for table `produto_pedido`
--

DROP TABLE IF EXISTS `produto_pedido`;
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
)

--
-- Dumping data for table `produto_pedido`
--

ALTER TABLE `produto_pedido` DISABLE KEYS;
INSERT INTO `produto_pedido` VALUES (1,3,1,19.50,12.45,NULL),(1,17,1,19.50,12.45,NULL),(2,17,1,21.75,14.15,NULL),(3,4,1,18.75,13.15,NULL),(3,6,3,18.75,13.15,NULL),(3,11,2,18.75,13.15,NULL),(3,16,1,18.75,13.15,NULL),(3,17,1,18.75,13.15,NULL),(4,1,3,14.00,9.00,NULL),(4,13,1,14.00,9.00,NULL),(4,17,1,14.00,9.00,NULL),(4,18,1,14.00,9.00,NULL),(5,3,1,9.00,6.50,NULL),(5,5,3,9.00,6.50,NULL),(5,8,2,9.00,6.50,NULL),(5,14,2,9.00,6.50,NULL),(5,17,1,9.00,6.50,NULL),(6,2,1,14.00,9.00,NULL),(7,12,2,9.00,6.50,NULL),(7,17,1,9.00,6.50,NULL),(8,15,1,16.50,10.50,NULL),(8,17,1,16.50,10.50,NULL),(9,5,3,14.00,9.50,NULL),(9,17,1,14.00,9.50,NULL),(10,8,1,0.75,0.15,NULL),(10,14,50,0.75,0.15,NULL),(10,17,1,0.75,0.15,NULL),(12,3,50,0.75,0.15,NULL),(12,7,50,0.75,0.15,NULL),(12,9,25,0.75,0.15,NULL),(12,17,1,0.75,0.15,NULL),(13,17,1,0.75,0.15,NULL),(14,5,2,75.00,15.00,NULL),(14,12,1,75.00,15.00,NULL),(14,15,1,75.00,15.00,NULL),(14,16,1,75.00,15.00,NULL),(14,17,1,75.00,15.00,NULL),(15,17,1,75.00,15.00,NULL),(16,2,2,75.00,15.00,NULL),(16,6,1,75.00,15.00,NULL),(16,15,1,75.00,15.00,NULL),(16,17,1,75.00,15.00,NULL),(17,3,1,75.00,15.00,NULL),(17,11,1,75.00,15.00,NULL),(17,13,1,75.00,15.00,NULL),(17,15,1,75.00,15.00,NULL),(17,16,1,75.00,15.00,NULL),(17,17,1,75.00,15.00,NULL),(18,5,1,75.00,15.00,NULL),(18,17,1,75.00,15.00,NULL),(19,15,50,0.75,0.15,NULL),(19,17,1,0.75,0.15,NULL),(20,13,50,0.75,0.15,NULL),(20,17,2,0.75,0.15,NULL);
ALTER TABLE `produto_pedido` ENABLE KEYS;

--
-- Table structure for table `unidade`
--

DROP TABLE IF EXISTS `unidade`;
CREATE TABLE `unidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(16) NOT NULL,
  `abreviacao` char(5) NOT NULL,
  PRIMARY KEY (`id`)
)

--
-- Dumping data for table `unidade`
--

ALTER TABLE `unidade` DISABLE KEYS;
INSERT INTO `unidade` VALUES (1,'Unidade','UND'),(2,'Cento','CENTO'),(3,'Dúzia','DUZIA'),(4,'Pacote','PCT'),(5,'Caixa','CX'),(6,'Grama','G'),(7,'Miligrama','MG'),(8,'Litros','L'),(9,'Mililitros','ML'),(10,'Kilograma','KG'),(11,'Garrafa','GRFA'),(12,'Pote','POTE');
ALTER TABLE `unidade` ENABLE KEYS;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(48) NOT NULL,
  `login` varchar(20) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `tipo` enum('administrador','atendente') NOT NULL,
  PRIMARY KEY (`id`)
)

--
-- Dumping data for table `usuario`
--

ALTER TABLE `usuario` DISABLE KEYS;
INSERT INTO `usuario` VALUES (1,'Administrador','admin','admin','administrador');
ALTER TABLE `usuario` ENABLE KEYS;

--
-- Temporary view structure for view `vw_est_andamento_pedido`
--

CREATE OR REPLACE VIEW vw_est_andamento_pedido AS 
SELECT 
    e_a_p.id_pedido,
    e_a_p.id_est_andamento,
    e_a.nome AS nome_est_andamento,
    e_a_p.id_usuario_autor,
    u.nome AS nome_usuario_autor,
    e_a_p.dthr_criacao
FROM 
    est_andamento_pedido AS e_a_p
    INNER JOIN est_andamento AS e_a ON e_a.id = e_a_p.id_est_andamento
    INNER JOIN usuario AS u ON u.id = e_a_p.id_usuario_autor
ORDER BY id_pedido ASC, dthr_criacao ASC, id_est_andamento ASC;

--
-- Temporary view structure for view `vw_estadoproduto`
--

DROP TABLE IF EXISTS `vw_estadoproduto`;
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
-- Temporary view structure for view `vw_pedido`
--

DROP TABLE IF EXISTS `vw_pedido`;
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
-- Temporary view structure for view `vw_produto`
--

DROP TABLE IF EXISTS `vw_produto`;
CREATE OR REPLACE VIEW vw_produto
AS SELECT 
    prod.id,
    prod.nome AS nome_produto,
    prod.id_categoria,
    cat.nome AS nome_categoria,
    cat.descricao AS descricao_categoria,
    prod.id_unidade,
    unid.nome AS nome_unidade,
    unid.abreviacao AS abreviacao_unidade,
    prod.preco_venda,
    prod.preco_custo,
    prod.id_usuario_alt,
    usr.nome AS nome_usuario_alt,
    usr.tipo AS tipo_usuario,
    prod.dthr_alt,
    prod.estado
    FROM produto AS prod
        INNER JOIN categoria AS cat ON cat.id = prod.id_categoria
        INNER JOIN unidade AS unid ON unid.id = prod.id_unidade
        LEFT JOIN usuario AS usr ON usr.id = prod.id_usuario_alt;

--
-- Temporary view structure for view `vw_produtos_pedido`
--

DROP TABLE IF EXISTS `vw_produtos_pedido`;
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

--
-- Temporary view structure for view `vw_ultimo_estado_pedido`
--

DROP TABLE IF EXISTS `vw_ultimo_estado_pedido`;
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
        (SELECT *
            FROM (
                SELECT
                    id_pedido,
                    id_est_andamento,
                    id_usuario_autor,
                    dthr_criacao,
                    (SELECT MAX(dthr_criacao) FROM est_andamento_pedido AS E WHERE E.id_pedido = est_andamento_pedido.id_pedido) AS dthr_ultimo
                FROM est_andamento_pedido
                ORDER BY id_pedido ASC, dthr_ultimo DESC , id_est_andamento DESC
            ) AS abc
            GROUP BY abc.id_pedido
        ) AS ultimo_estado_pedido
        INNER JOIN est_andamento AS e_a
            ON e_a.id = ultimo_estado_pedido.id_est_andamento
        INNER JOIN usuario AS u
            ON u.id = ultimo_estado_pedido.id_usuario_autor
    WHERE dthr_criacao = dthr_ultimo; -- Exibe apenas os estados de cada pedido que sejam os últimos (tenham data de criação igual à ultima encontrada para cada pedido)

--
-- Dumping routines for database 'titanw25_japedidos_hml'
--

DELIMITER //
CREATE FUNCTION `preco_custo_produto`(id_prod INT) RETURNS decimal(8,2)
BEGIN
    DECLARE r DECIMAL(8,2);
    SELECT preco_custo INTO r FROM produto  WHERE id = id_prod LIMIT 1;
    RETURN r;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION `preco_venda_produto`(id_prod INT) RETURNS decimal(8,2)
BEGIN
    DECLARE r DECIMAL(8,2);
    SELECT preco_venda INTO r FROM produto  WHERE id = id_prod LIMIT 1;
    RETURN r;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `atualizar_precos_pedido`(IN id_ped INT)
    MODIFIES SQL DATA
BEGIN
        DECLARE soma_custo DECIMAL(8,2);
        DECLARE soma_venda DECIMAL(8,2);
        DECLARE valor_preco_final DECIMAL(8,2);
        DECLARE valor_preco_frete DECIMAL (8,2);
        DECLARE valor_tx_desconto INT;
    
        -- Definição de valores auxilires
        SELECT 
            p.preco_frete, p.tx_desconto 
        INTO valor_preco_frete, valor_tx_desconto 
        FROM pedido p
        WHERE 
            p.id = id_ped 
        LIMIT 1;
        
        -- Soma precos dos produtos
        SELECT 
            SUM(pp.preco_venda * pp.quantidade) AS pv_total, 
            SUM(pp.preco_custo * pp.quantidade) AS pc_total
        INTO
            soma_venda,
            soma_custo
        FROM produto_pedido pp 
        WHERE
            pp.id_pedido = id_ped;
        
        -- Cálculos finais
        SET valor_preco_final = soma_venda - (soma_venda * CAST(valor_tx_desconto/100 AS DECIMAL(3,2))) + valor_preco_frete;
    
        UPDATE 
            pedido 
        SET 
            preco_final = valor_preco_final, 
            preco_custo_total = soma_custo 
        WHERE id = id_ped;
    END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `select_estados_pedido`(IN id_ped INT)
    READS SQL DATA
BEGIN
   SELECT * FROM vw_est_andamento_pedido WHERE id_pedido = id_ped;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE `select_pedidos_by_estado`(IN id_est INT)
    READS SQL DATA
BEGIN
  SELECT * FROM vw_pedido WHERE id_ultimo_est = id_est ORDER BY dthr_entregar ASC;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `select_produtos_pedido`(IN id_ped INT)
    READS SQL DATA
BEGIN
        SELECT * FROM vw_produtos_pedido WHERE id_pedido = id_ped;
    END //
DELIMITER ;