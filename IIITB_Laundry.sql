-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: IIITB_Laundry
-- ------------------------------------------------------
-- Server version	5.7.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `LaundryBooking`
--

DROP TABLE IF EXISTS `LaundryBooking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LaundryBooking` (
  `bookingId` int(11) NOT NULL AUTO_INCREMENT,
  `bookingDate` date DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bookingId`),
  KEY `FKp57qh3v1kf6oo0fucwpcyf4n8` (`slot_id`),
  KEY `FK311ji46w1wtqmd6f3kilhd6ud` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LaundryBooking`
--

LOCK TABLES `LaundryBooking` WRITE;
/*!40000 ALTER TABLE `LaundryBooking` DISABLE KEYS */;
/*!40000 ALTER TABLE `LaundryBooking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LaundrySlot`
--

DROP TABLE IF EXISTS `LaundrySlot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LaundrySlot` (
  `slotId` int(11) NOT NULL AUTO_INCREMENT,
  `endTime` varchar(255) DEFAULT NULL,
  `hostel` varchar(255) DEFAULT NULL,
  `noOfMachines` int(11) DEFAULT NULL,
  `startTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`slotId`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LaundrySlot`
--

LOCK TABLES `LaundrySlot` WRITE;
/*!40000 ALTER TABLE `LaundrySlot` DISABLE KEYS */;
INSERT INTO `LaundrySlot` VALUES (1,'11:00','LILAVATI',2,'10:00'),(2,'12:00','LILAVATI',2,'11:00'),(3,'13:00','LILAVATI',2,'12:00'),(4,'14:00','LILAVATI',2,'13:00'),(5,'15:00','LILAVATI',2,'14:00'),(6,'16:00','LILAVATI',2,'15:00'),(7,'17:00','LILAVATI',2,'16:00'),(8,'18:00','LILAVATI',2,'17:00'),(9,'19:00','LILAVATI',2,'18:00'),(10,'20:00','LILAVATI',2,'19:00'),(11,'11:00','BHASKARA',5,'10:00'),(12,'12:00','BHASKARA',5,'11:00'),(13,'13:00','BHASKARA',5,'12:00'),(14,'14:00','BHASKARA',5,'13:00'),(15,'15:00','BHASKARA',5,'14:00'),(16,'16:00','BHASKARA',5,'15:00'),(17,'17:00','BHASKARA',5,'16:00'),(18,'18:00','BHASKARA',5,'17:00'),(19,'19:00','BHASKARA',5,'18:00'),(20,'20:00','BHASKARA',5,'19:00');
/*!40000 ALTER TABLE `LaundrySlot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Student`
--

DROP TABLE IF EXISTS `Student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Student` (
  `studentId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `laundryCardNo` bigint(20) DEFAULT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `mobileNo` bigint(20) DEFAULT NULL,
  `rollNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`studentId`),
  UNIQUE KEY `UK_5roiylscu1x5wadyqiu0h8yd0` (`laundryCardNo`),
  UNIQUE KEY `UK_plmj78pn6x9btjjauiat1hkf1` (`mobileNo`),
  UNIQUE KEY `UK_668bbjjyk6k75x3i8d59t29aq` (`rollNo`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES (1,'Utsav','MALE',NULL,630,NULL,9038178722,'MT2019126'),(2,'Ujan','MALE',NULL,631,NULL,7044107401,'MT2019125'),(3,'Shashank','MALE',NULL,632,NULL,8604780383,'MT2019101'),(4,'Madhumanti','FEMALE',NULL,633,NULL,9433591033,'MT2019058'),(5,'Kanya','FEMALE',NULL,634,NULL,7011793323,'MT20190150');
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-02 14:13:32
