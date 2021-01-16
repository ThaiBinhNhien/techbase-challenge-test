CREATE DATABASE  IF NOT EXISTS `db_techbase_challenge_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_techbase_challenge_test`;
-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: db-service    Database: db_techbase_challenge_test
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `head_department_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3b1di2vn8hbwb4u6749rntte6` (`head_department_id`),
  CONSTRAINT `FK8538159s8scims5p3mxr74311` FOREIGN KEY (`head_department_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('10b21c26-7478-45ea-9921-3edb248dd95c','2021-01-16 15:28:53.742000','2021-01-16 15:28:53.742000','Department A','b239fad9-ea78-4990-bddc-372118dc380f'),('788285a6-4955-4a45-bcde-4a7023b6cce5','2021-01-16 15:29:02.039000','2021-01-16 15:29:02.039000','Department B','0c63b543-610a-4e3c-b0b4-7d53faa25725');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mpps3d3r9pdvyjx3iqixi96fi` (`user_id`),
  CONSTRAINT `FKhal2duyxxjtadykhxos7wd3wg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('07fc7b7f-6a51-461f-bbbd-8a024397031e','2021-01-16 15:42:40.276000','2021-01-16 15:42:40.276000','Nguyen Van D','0fb1efb2-931f-4098-9ca0-bfcbd12edb84'),('0c63b543-610a-4e3c-b0b4-7d53faa25725','2021-01-16 15:28:33.505000','2021-01-16 15:28:33.505000','Nguyen Van B','c19f44b5-414f-4634-9978-91c987c7f6a4'),('99b9845a-37bd-43db-b958-e0f165086ac4','2021-01-16 15:26:12.120000','2021-01-16 15:26:12.120000','Admin','a167ba85-1f51-433b-9f35-2457eab17591'),('b239fad9-ea78-4990-bddc-372118dc380f','2021-01-16 15:28:16.583000','2021-01-16 15:28:16.583000','Nguyen Van A','83f1ae5f-4272-4e4c-b4cd-0fd88ec526fa'),('e2947e23-32e2-4e0e-91d2-d29c3be4c40d','2021-01-16 15:28:42.013000','2021-01-16 15:28:42.013000','Nguyen Van C','327f6820-3267-4600-ab82-fd95b972a0ba');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('59157129-5717-11eb-ae8e-0242ac1f0002','2021-01-14 20:16:56.000000','2021-01-14 20:16:56.000000','TEAMLEADER'),('7302a7bd-56a5-11eb-aefe-0242ac1b0002','2021-01-14 20:16:56.000000','2021-01-14 20:16:56.000000','DIRECTOR'),('73036931-56a5-11eb-aefe-0242ac1b0002','2021-01-14 20:16:56.000000','2021-01-14 20:16:56.000000','MANAGER'),('73040cef-56a5-11eb-aefe-0242ac1b0002','2021-01-14 20:16:56.000000','2021-01-14 20:16:56.000000','EMPLOYEE');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `department_id` varchar(255) NOT NULL,
  `team_leader_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5vs102ao3g1d1bfmff2h0pxcv` (`team_leader_id`),
  KEY `FKcitsl0ygrf7nbmydhlcqorb3p` (`department_id`),
  CONSTRAINT `FK2y5sau22rxdtbj85hjdt7psb8` FOREIGN KEY (`team_leader_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKcitsl0ygrf7nbmydhlcqorb3p` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES ('7569e3ee-6305-4421-9c8f-75bef2c5156a','2021-01-16 15:29:25.160000','2021-01-16 15:29:25.160000','Team B','788285a6-4955-4a45-bcde-4a7023b6cce5','e2947e23-32e2-4e0e-91d2-d29c3be4c40d');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_join`
--

DROP TABLE IF EXISTS `team_join`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_join` (
  `team_id` varchar(255) NOT NULL,
  `employee_id` varchar(255) NOT NULL,
  PRIMARY KEY (`team_id`,`employee_id`),
  KEY `FKllv1hng3sturkqfwpo75fp1tw` (`employee_id`),
  CONSTRAINT `FKkujiopfr5tvd0r3pg3up4u9iq` FOREIGN KEY (`team_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKllv1hng3sturkqfwpo75fp1tw` FOREIGN KEY (`employee_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_join`
--

LOCK TABLES `team_join` WRITE;
/*!40000 ALTER TABLE `team_join` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_join` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('0fb1efb2-931f-4098-9ca0-bfcbd12edb84','2021-01-16 15:42:40.382000','2021-01-16 15:42:40.382000','$2a$10$GXhO8EHhSNp8oJcChsbCGeSinkjxUw3FOrlqqgiKULepZ13d0gNt6','nguyenvand','73040cef-56a5-11eb-aefe-0242ac1b0002'),('327f6820-3267-4600-ab82-fd95b972a0ba','2021-01-16 15:28:42.105000','2021-01-16 15:28:42.105000','$2a$10$pnHkx3nd6YeWPSTNZ1OrwuaqPaxUnWmUeCk07OHGtNdEhBWSt0Pfa','nguyenvanc','59157129-5717-11eb-ae8e-0242ac1f0002'),('83f1ae5f-4272-4e4c-b4cd-0fd88ec526fa','2021-01-16 15:28:16.685000','2021-01-16 15:28:16.685000','$2a$10$t42Q6Bt6JpKFfGNMp42lg.8D2vSaYI7JL71k5IXWr8Hyb2hLfmE5.','nguyenvana','73036931-56a5-11eb-aefe-0242ac1b0002'),('a167ba85-1f51-433b-9f35-2457eab17591','2021-01-16 15:26:12.290000','2021-01-16 15:26:12.290000','$2a$10$e3fKKtjPHd1pwFJ4PesBZetTP0YJVPRhxVesewgxfM.DyTU/MfLF6','admin','7302a7bd-56a5-11eb-aefe-0242ac1b0002'),('c19f44b5-414f-4634-9978-91c987c7f6a4','2021-01-16 15:28:33.610000','2021-01-16 15:28:33.610000','$2a$10$vdgi.K9WId8DbcF7I8xCk.G2OIMqw8/WKaHkrPsbctG9lEjP2/7i2','nguyenvanb','73036931-56a5-11eb-aefe-0242ac1b0002');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 23:16:22
