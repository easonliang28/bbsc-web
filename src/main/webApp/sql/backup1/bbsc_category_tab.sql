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
-- Table structure for table `category_tab`
--

DROP TABLE IF EXISTS `category_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_tab` (
  `category_category_id` bigint DEFAULT NULL,
  `tab` varchar(255) DEFAULT NULL,
  KEY `FKol2tlatgycsnc24j9glb65gvy` (`category_category_id`),
  FULLTEXT KEY `tab` (`tab`),
  CONSTRAINT `FKol2tlatgycsnc24j9glb65gvy` FOREIGN KEY (`category_category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_tab`
--

LOCK TABLES `category_tab` WRITE;
/*!40000 ALTER TABLE `category_tab` DISABLE KEYS */;
INSERT INTO `category_tab` VALUES (1,'omg1'),(1,'omg2'),(1,'omg3'),(1,'omg4'),(22,'FAQ'),(22,'FAQ'),(22,'FAQ'),(22,'FAQ'),(42,'new'),(47,'Fragrance'),(47,'Hair Care &amp; Styling'),(47,'Makeup'),(47,'Men\'s Grooming'),(47,'Nail Care &amp; Other'),(47,'Skin, Bath &amp; Body'),(48,'Accessories'),(48,'Apparel'),(48,'Bag &amp; Wallets'),(48,'Watches'),(49,'Books'),(49,'Children\'s Books'),(49,'Comics &amp; Manga'),(49,'Magazines'),(49,'Others'),(49,'Stationery'),(45,'Accessories'),(45,'Bag &amp; Wallets'),(45,'Clothes'),(45,'Footwear'),(45,'Watches'),(46,'Accessories'),(46,'Bag &amp; Wallets'),(46,'Clothes'),(46,'Jewelry'),(46,'Shoes'),(46,'Watches'),(50,'Audio'),(50,'Computer Parts &amp; Accessories'),(50,'Computers &amp; Tablets'),(50,'Mobile &amp; Tablet Accessories'),(50,'Mobile Phones'),(50,'Others'),(50,'TVs &amp; Entertainment Systems'),(54,'Baby Apparel'),(54,'Boy\'s Apparel'),(54,'Children\'s Toys'),(54,'Girl\'s Apparel'),(54,'Maternity'),(54,'Others'),(54,'Prams, Strollers &amp; Car Seats'),(55,'Furniture'),(55,'Home Decor'),(55,'Others'),(55,'Tools &amp; DIY'),(57,'Gaming Accessories'),(57,'Gaming Consoles'),(57,'Video Games'),(59,'Artworks'),(59,'Craft Supplies &amp; Tools'),(59,'Handmade Goods &amp; Accessories'),(59,'Others'),(60,'Music Accessories'),(60,'Music Instruments'),(60,'Others'),(61,'Art'),(61,'Collectibles'),(61,'Furniture'),(61,'Others'),(62,'Board Games &amp; Cards'),(62,'Collectibles'),(62,'Others'),(63,'Others'),(63,'Pet Accessories'),(63,'Pet Clothing'),(63,'Pet Furniture'),(63,'Pet Leashes &amp; Harnesses'),(63,'Pet Toys'),(64,'Athletic Clothing'),(64,'Bicycles'),(64,'Camping &amp; Hiking'),(64,'Fishing'),(64,'Golf'),(64,'Gym &amp; Fitness'),(64,'Others'),(64,'Scooters, Skateboards &amp; Skates'),(64,'Snow Sports'),(64,'Surfing');
/*!40000 ALTER TABLE `category_tab` ENABLE KEYS */;
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
