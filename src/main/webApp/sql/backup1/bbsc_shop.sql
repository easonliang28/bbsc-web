-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: bbsc
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `sid` bigint NOT NULL AUTO_INCREMENT,
  `id` bigint NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `registered_date` date DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `customer_shop_idx` (`id`),
  FULLTEXT KEY `name` (`name`),
  CONSTRAINT `customer_shop` FOREIGN KEY (`id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,1,'Eason Liang','addresss','easonliang28@gmail.com','67523644','/upload/img/shop/58.png','2021-04-23','I am so handsome!'),(2,2,'Not Eason','Hong Kong','NotEason@NotEason.com','99999999','/upload/img/shop/58.png','2021-04-24',NULL),(3,10,'Zapdos','Suite 843 806 Julio Cliff, Yadiraville, RI 94965','christoper.parker@example.com','1-815-415-3564 x5072','/upload/img/shop/58.png','2021-04-24',NULL),(4,11,'Persian','135 White Points, New Bartonside, VA 93859-4396','jim.homenick@example.com','1-714-202-4282','/upload/img/shop/58.png','2021-04-24',NULL),(5,12,'Arcanine','587 Madison Inlet, Dannietown, NC 13982','lupe.bins@example.com','1-478-815-8619 x9997','/upload/img/shop/58.png','2021-04-24',NULL),(6,13,'Caterpie','954 Maynard Cape, Goodwinchester, AK 46695-0508','armandina.ward@example.com','1-507-412-9265','/upload/img/shop/58.png','2021-04-24',NULL),(7,14,'Voltorb','71368 Ebert Branch, South Jesse, KY 36507-0198','maryellen.blanda@example.com','641.520.1153','/upload/img/shop/58.png','2021-04-24',NULL),(8,15,'Persian','021 Walsh Crossing, Port Glen, WI 83872','birgit.bogisich@example.com','606-414-8357 x2749','/upload/img/shop/58.png','2021-04-24',NULL),(9,16,'Magikarp','Apt. 248 386 Lindgren Corners, North Bette, LA 86879','antoine.reichert@example.com','479.714.6269','/upload/img/shop/58.png','2021-04-24',NULL),(10,17,'Nidoran','32732 Toy Mountain, North Okfort, OH 57766','marcellus.oberbrunner@example.com','646.907.5072','/upload/img/shop/58.png','2021-04-24',NULL),(11,18,'Snorlax','00395 Blair Burgs, North Fabiola, WA 30819-5480','flo.walter@example.com','661-906-8840 x6952','/upload/img/shop/58.png','2021-04-24',NULL),(12,19,'Lickitung','692 Howell Drive, Skileshaven, GA 07565-7523','mckenzie.kreiger@example.com','262.321.6367 x6108','/upload/img/shop/58.png','2021-04-24',NULL),(13,20,'Seadra','765 Lemuel Plains, Numbersport, MI 09831-3688','august.upton@example.com','1-559-904-1660','/upload/img/shop/58.png','2021-04-24',NULL),(14,21,'Ivysaur','Apt. 769 05051 Christiansen Inlet, North Parismouth, MI 21262','sabrina.koch@example.com','580-504-8915 x1967','/upload/img/shop/58.png','2021-04-24',NULL),(15,22,'Exeggutor','3992 Nicolas Springs, Handbury, CA 84448-8059','chester.okeefe@example.com','218-234-9862 x9984','/upload/img/shop/58.png','2021-04-24',NULL),(16,23,'Articuno','Apt. 262 348 Romaguera Turnpike, Greenfelderburgh, NC 45623','felton.ritchie@example.com','801-980-6408 x3066','/upload/img/shop/58.png','2021-04-24',NULL),(17,24,'Nidoran','Suite 550 71436 Nestor Roads, Andersonstad, VA 94024-6170','santos.greenfelder@example.com','678.615.8632','/upload/img/shop/58.png','2021-04-24',NULL),(18,25,'Mankey','Apt. 130 66578 Wilderman Islands, Ruthberg, LA 61639','magnolia.ondricka@example.com','(605) 254-1515 x7065','/upload/img/shop/58.png','2021-04-24',NULL),(19,26,'Exeggutor','Suite 915 42407 Yundt Roads, Stephineshire, PA 57965-8413','felton.botsford@example.com','607.757.8296 x7930','/upload/img/shop/58.png','2021-04-24',NULL),(20,27,'Magmar','89495 Mimi Forest, Lilialand, VT 52372-8690','tim.ward@example.com','1-731-281-3028 x8118','/upload/img/shop/58.png','2021-04-24',NULL),(21,28,'Haunter','56512 Marcy Haven, Bergeview, WY 28609-8524','zetta.feest@example.com','502.904.7834 x5826','/upload/img/shop/58.png','2021-04-24',NULL),(22,29,'Exeggcute','Suite 328 5882 Zemlak Valley, Torpburgh, TN 25156-8948','bibi.hagenes@example.com','1-619-785-9367','/upload/img/shop/58.png','2021-04-24',NULL),(23,30,'Nidoran','Apt. 069 32513 Ted Mill, Annetteton, MD 78815','ailene.schaefer@example.com','904.878.9886 x7959','/upload/img/shop/58.png','2021-04-24',NULL),(24,31,'Graveler','66888 Freddie Landing, D\'Amoreshire, KS 28006','elayne.hettinger@example.com','626-724-6316','/upload/img/shop/58.png','2021-04-24',NULL),(25,32,'Doduo','Suite 756 15869 Rowe Turnpike, Lake Reinaldofort, SC 56111-4521','estell.klein@example.com','947.878.5889 x3407','/upload/img/shop/58.png','2021-04-24',NULL),(26,33,'Rhydon','37476 Welch Well, Andersonview, WY 93322-1174','ned.brekke@example.com','1-606-848-1137','/upload/img/shop/58.png','2021-04-24',NULL),(27,34,'Haunter','Apt. 984 25876 Karoline Dale, North Dongville, NY 47958-7838','yajaira.oconner@example.com','(605) 415-8360','/upload/img/shop/58.png','2021-04-24',NULL),(28,35,'Kakuna','73289 Heath Shore, Johnnyberg, WA 06566','anjanette.brown@example.com','(503) 305-9295','/upload/img/shop/58.png','2021-04-24',NULL),(29,36,'Seaking','Suite 282 7613 Denese Plains, Kundeville, MI 71804','abel.koelpin@example.com','417-574-6271 x2551','/upload/img/shop/58.png','2021-04-24',NULL),(30,37,'Starmie','46169 Brooks Key, Port Juliannview, NH 15769','hermelinda.mohr@example.com','414-281-1599','/upload/img/shop/58.png','2021-04-24',NULL),(31,38,'Hitmonchan','Suite 524 16789 Ledner Cove, New Hugh, MI 32419-1738','maria.kerluke@example.com','(947) 904-4736 x8947','/upload/img/shop/58.png','2021-04-24',NULL),(32,39,'Magmar','Apt. 548 107 Grimes Orchard, Gerlachborough, IN 63408-0068','jan.murphy@example.com','615.267.0986 x3307','/upload/img/shop/58.png','2021-04-24',NULL),(33,40,'Jolteon','Suite 188 30652 Jeffie Causeway, East Gretamouth, VA 29167','caleb.blick@example.com','206-626-1324','/upload/img/shop/58.png','2021-04-24',NULL),(34,41,'Scyther','Apt. 337 90622 Adams Rue, Luiseberg, MA 43868-5688','bobby.moore@example.com','(717) 919-7230','/upload/img/shop/58.png','2021-04-24',NULL),(35,42,'Ponyta','181 Temeka Stream, Lake Josef, NH 36231-8730','zenaida.kiehn@example.com','1-301-763-5335 x1091','/upload/img/shop/58.png','2021-04-24',NULL),(36,43,'Dragonite','22934 Gerardo Flat, South Monteland, IN 64723-3866','shani.langosh@example.com','(903) 605-3425','/upload/img/shop/58.png','2021-04-24',NULL),(37,44,'Golem','Suite 314 503 Coletta Fall, Harveyburgh, WY 07800-7353','james.windler@example.com','412.281.4763 x2343','/upload/img/shop/58.png','2021-04-24',NULL),(38,45,'Pidgeotto','Suite 664 2901 Murray Rue, Wilfordmouth, WV 27366','alysha.beatty@example.com','1-412-323-0070 x4915','/upload/img/shop/58.png','2021-04-24',NULL),(39,46,'Vulpix','Apt. 968 45921 Murphy Row, North Marty, FL 29875-4478','aubrey.schinner@example.com','(914) 815-6501','/upload/img/shop/58.png','2021-04-24',NULL),(40,47,'Paras','47759 Reynolds Pike, Shinhaven, MA 61279-6253','edison.abernathy@example.com','(941) 234-0288','/upload/img/shop/58.png','2021-04-24',NULL),(41,48,'Koffing','Apt. 054 29288 Gottlieb Oval, South Margretstad, TX 34152-1704','gerardo.rempel@example.com','1-256-612-0080','/upload/img/shop/58.png','2021-04-24',NULL),(42,49,'Kadabra','Apt. 984 96879 Wisozk Cape, Lake Millard, NJ 02846','jarrett.schinner@example.com','913-408-1037','/upload/img/shop/58.png','2021-04-24',NULL),(43,50,'Marowak','Suite 917 8223 Bill Vista, Staceeview, WA 32711','zona.cassin@example.com','940-740-9329 x6887','/upload/img/shop/58.png','2021-04-24',NULL),(44,51,'Persian','068 Reichel Highway, Joniemouth, RI 15858','mirian.conroy@example.com','1-831-551-0404','/upload/img/shop/58.png','2021-04-24',NULL),(45,52,'Rapidash','Apt. 780 761 Maire Locks, Freddyfort, CT 87470','roger.wintheiser@example.com','231-517-0876 x8362','/upload/img/shop/58.png','2021-04-24',NULL),(46,53,'Tentacool','6560 Conroy Viaduct, South Han, RI 39371-0982','wes.oreilly@example.com','(707) 601-7373 x6000','/upload/img/shop/58.png','2021-04-24',NULL),(47,54,'Magikarp','Suite 193 3241 Gusikowski Landing, Morganberg, GA 50392-6992','boyce.harvey@example.com','870.915.5277','/upload/img/shop/58.png','2021-04-24',NULL),(48,55,'Magmar','30628 Ruben Plaza, West Georgann, NH 25707','obdulia.jakubowski@example.com','351.508.1434 x2513','/upload/img/shop/58.png','2021-04-24',NULL),(49,56,'Marowak','Suite 639 28616 Sanford Park, Schaefermouth, IN 52063-6904','lady.graham@example.com','1-315-978-7057','/upload/img/shop/58.png','2021-04-24',NULL),(50,57,'Tentacool','93417 Otto Throughway, Russellton, NJ 81783','arron.dickinson@example.com','1-954-559-6265','/upload/img/shop/58.png','2021-04-24',NULL),(51,58,'Flareon','Suite 560 72875 Kiehn Point, Windlermouth, HI 36901-8863','carey.reilly@example.com','409.858.8953 x6243','/upload/img/shop/58.png','2021-04-24',NULL),(52,59,'Exeggutor','Suite 390 24994 Marks Meadow, South Lindseyview, TX 18818','estela.witting@example.com','1-281-734-7070','/upload/img/shop/58.png','2021-04-24',NULL),(53,60,'Sandshrew','2675 Stamm Flat, Port Tommyview, NE 83711','lacie.greenholt@example.com','1-703-971-4467 x6510','/upload/img/shop/58.png','2021-04-24',NULL),(54,61,'Flareon','92808 Virgil Grove, West Clark, MN 21415','logan.becker@example.com','502.630.2620 x8980','/upload/img/shop/58.png','2021-04-24',NULL),(55,62,'Kakuna','20723 Klocko Ville, Rolfsonchester, IN 61647-5239','mitch.howell@example.com','712.253.2019 x2062','/upload/img/shop/58.png','2021-04-24',NULL),(60,1,'GG','GG','GG@GG.com','9999','/upload/img/shop/60.png','2021-05-08','YO! I know you are so handsome!'),(62,2,'AAAAAAAAAAAAAAAAAAAAAAAAAAAA','AAAAAAAAAAAAAAAAAAAAAA','AAAA@AAA.com','11111111','/upload/img/shop/62.png','2021-05-08','YO! I know you are so handsome!'),(63,63,'bob','hongkong','harry@gmail.com','12345678','/itemNull.jpg','2021-05-08','YO! I know you are so handsome!');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-09 15:29:52
