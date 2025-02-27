-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: ivfzhou-ubuntu    Database: db_reserve_platform
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_city`
--

DROP TABLE IF EXISTS `t_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_city` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pinyin` varchar(64) DEFAULT NULL,
  `hotel_number` int unsigned DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint unsigned DEFAULT '0' COMMENT '0正常 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_coupon`
--

DROP TABLE IF EXISTS `t_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_coupon` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(256) DEFAULT NULL,
  `utype` tinyint unsigned DEFAULT NULL,
  `priority` tinyint DEFAULT NULL,
  `content` text,
  `rule_info` text,
  `limit_info` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT NULL,
  `sub_title` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_coupon_issue`
--

DROP TABLE IF EXISTS `t_coupon_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_coupon_issue` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `cid` bigint unsigned DEFAULT NULL,
  `type` tinyint unsigned DEFAULT NULL,
  `begin_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `days` tinyint unsigned DEFAULT NULL,
  `numbers` int DEFAULT NULL,
  `go_start_time` timestamp NULL DEFAULT NULL,
  `go_stop_time` timestamp NULL DEFAULT NULL,
  `method` int DEFAULT NULL,
  `number` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_coupon_receive`
--

DROP TABLE IF EXISTS `t_coupon_receive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_coupon_receive` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint unsigned DEFAULT NULL,
  `cid` bigint unsigned DEFAULT NULL,
  `get_time` timestamp NULL DEFAULT NULL,
  `get_type` int DEFAULT NULL,
  `timeout` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_coupon_template`
--

DROP TABLE IF EXISTS `t_coupon_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_coupon_template` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tname` varchar(256) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT NULL,
  `template_class` varchar(256) DEFAULT NULL,
  `template_dynamic` varchar(256) DEFAULT NULL,
  `template_type` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_dictionaries_content`
--

DROP TABLE IF EXISTS `t_dictionaries_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dictionaries_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `value` text,
  `dictionary_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_dictionary`
--

DROP TABLE IF EXISTS `t_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dictionary` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_history_price`
--

DROP TABLE IF EXISTS `t_history_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_history_price` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `rid` bigint unsigned DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `number` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_hotel`
--

DROP TABLE IF EXISTS `t_hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_hotel` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(256) DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `hotel_info` text,
  `keyword` varchar(256) DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `brand` varchar(256) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `cid` bigint unsigned DEFAULT NULL,
  `district` varchar(256) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `oid` varchar(256) DEFAULT NULL,
  `uid` bigint unsigned DEFAULT NULL,
  `hid` bigint unsigned DEFAULT NULL,
  `number` int DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `begin_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `days` int DEFAULT NULL,
  `all_price` decimal(5,2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_order_price_detail`
--

DROP TABLE IF EXISTS `t_order_price_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_price_detail` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `oid` varchar(256) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_order_price_param`
--

DROP TABLE IF EXISTS `t_order_price_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_price_param` (
  `hid` bigint unsigned DEFAULT NULL,
  `rid` bigint unsigned DEFAULT NULL,
  `begin_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `rnumber` int unsigned DEFAULT NULL,
  `cid` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_room_price`
--

DROP TABLE IF EXISTS `t_room_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_room_price` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `rid` bigint unsigned DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `type` tinyint unsigned DEFAULT NULL,
  `number` int unsigned DEFAULT NULL,
  `has_number` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-31 20:05:47
