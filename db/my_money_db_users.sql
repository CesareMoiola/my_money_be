REATE DATABASE  IF NOT EXISTS `my_money_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `my_money_db`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: my_money_db
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nome','Cognome','nome.cognome@gmail.com','123'),(8,'asd','asd','asdasd@asd.asd','$2a$20$dmVg6kevq.qNiwCb55NuO.S7m2sdp5MwqsjoJev6H4okbORImUR1W'),(10,'asd','asd','asd@asd.asd','$2a$20$kszIG.j/omHKyjSTqKFqTOXOB0fAV1l7Lf0UQI1lDznBKgfNIP0qi'),(11,'qqq','qqq','qqq@qqq.qqq','$2a$20$Jf1qqRnQFbYULnsYf/oz9.boJB.XqI6Jdw9NDH0qClB.BjEpF.VVq'),(12,'qqq','qqq','aaa@aaa.aaa','$2a$20$CfkMvuySWvU9IjTkeD4Tce/pENRluGSHTF0qECNpX7LnQR6Xl/BKK'),(13,'qqq','qqq','bbb@bbb.bbb','$2a$20$6zfLTtmfwqdGzTuS1p5nauMHwYHRjhl6Jl6FqezxQMKaRsfEa/W3K'),(14,'ccc','ccc','ccc@ccc.ccc','$2a$20$mlX0nDpQVnA6WMB5oaNDOOCt/62TYobgZ2rI3A1AwSNbIHjPy0Rc2'),(15,'ddd','ddd','ddd@ddd.ddd','$2a$20$5w9NsqI9qquXqDys5aMgJepawXiWmbLjl7nSC2CnY8PbMpXmR7xSK'),(16,'ddd','ddd','eee@eee.eee','$2a$20$muR1Huq0y264Yy8dYZVwjeZG6xGh2riFrfvICybjHlvbboAk6bYT2'),(17,'ddd','ddd','fff@fff.fff','$2a$20$bYk0gLvnr7C.NcR1xjN1AuoIKAA2Ukw0XnWqBkDGCDlLop7u4UvFC'),(18,'ggg','ggg','ggg@ggg.ggg','$2a$10$oMzUVHo8nKkCWHhtKeUIUuiqaPtbBt4/HSfjPDRHwj3HRlsq2J2Um'),(27,'hhh','hhh','hhh@hhh.com','$2a$10$Nzwo5nO9JkGVofTd0qAQyudezthNadzawlvdeSTM3lzLnLN0UVEn.'),(28,'hhh','hhh','iii@iii.com','$2a$10$R60JPgIoop7jVQgn1rkQ8e4N7mw7FqNc/hx/d5fqGmZeWzyknenqG'),(30,'hhh','hhh','jjj@jjjj.com','$2a$10$kQLP8phThmqxhTzN5eShVe9b6EGTF9pfZ8IgjQMDCBZZ2Uu1MRmXO'),(31,'asd','sdf','qwesdf@000.com','$2a$10$51Add9/cOjUC40vGyt0Lv.hX1KMtvdIWxQK7oocwYsfceNyNCfyJS'),(32,'ddd','ddd','asdasd@sdfg.com','$2a$20$t9teAAk2gkJ3V59zK3j/te255JBiHOSe.AE2mLbk4njSzB12kvt6O'),(33,'asd','asd','asd','$2a$10$MMY4CQx9pUe0eQF6SfFsi.cZLg162yqLwhTDV6UuMDtwHWWrpWLTO');
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

-- Dump completed on 2022-11-15 17:21:01
