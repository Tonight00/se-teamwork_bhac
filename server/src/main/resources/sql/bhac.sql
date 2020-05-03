/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 : Database - bhac
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `bhac` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bhac`;

/*Table structure for table `bhac_activity` */

DROP TABLE IF EXISTS `bhac_activity`;

CREATE TABLE `bhac_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `ddl` datetime DEFAULT NULL,
  `begin` datetime DEFAULT CURRENT_TIMESTAMP,
  `end` datetime DEFAULT NULL,
  `brief` varchar(50) DEFAULT NULL,
  `isOpen` int(11) DEFAULT '0',
  `limitPeopleNum` int(11) DEFAULT '-1',
  `state` int(11) DEFAULT '0',
  `extra` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_activity` */

insert  into `bhac_activity`(`id`,`category`,`uid`,`title`,`ddl`,`begin`,`end`,`brief`,`isOpen`,`limitPeopleNum`,`state`,`extra`) values (1,1,1,'post1',NULL,'2020-04-30 13:59:02',NULL,NULL,0,-1,0,NULL),(2,1,2,'post2',NULL,'2020-04-30 13:59:02',NULL,NULL,0,-1,0,NULL),(3,1,3,'post3',NULL,'2020-04-30 13:59:02',NULL,NULL,0,-1,0,NULL);

/*Table structure for table `bhac_actuserrole` */

DROP TABLE IF EXISTS `bhac_actuserrole`;

CREATE TABLE `bhac_actuserrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_actuserrole` */

insert  into `bhac_actuserrole`(`id`,`uid`,`rid`) values (1,1,4),(2,2,4),(3,1,5),(4,3,4);

/*Table structure for table `bhac_belongactivitytag` */

DROP TABLE IF EXISTS `bhac_belongactivitytag`;

CREATE TABLE `bhac_belongactivitytag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_belongactivitytag` */

insert  into `bhac_belongactivitytag`(`id`,`aid`,`tid`) values (1,1,1),(2,1,2),(3,2,2),(4,3,3);

/*Table structure for table `bhac_comment` */

DROP TABLE IF EXISTS `bhac_comment`;

CREATE TABLE `bhac_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `seqNum` int(11) NOT NULL,
  `postedBy` int(11) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `content` blob,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_comment` */

insert  into `bhac_comment`(`id`,`pid`,`seqNum`,`postedBy`,`parentId`,`content`,`date`) values (1,1,1,1,NULL,'first_comment','2020-04-30 18:06:28'),(2,2,2,1,NULL,'second_comment','2020-04-30 18:06:28'),(3,3,3,1,NULL,'third_comment','2020-04-30 18:06:28');

/*Table structure for table `bhac_joinuseractivity` */

DROP TABLE IF EXISTS `bhac_joinuseractivity`;

CREATE TABLE `bhac_joinuseractivity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `state` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_joinuseractivity` */

insert  into `bhac_joinuseractivity`(`id`,`uid`,`aid`,`state`) values (1,1,1,0),(2,1,2,0),(3,1,3,1),(4,2,2,1),(5,2,3,1),(6,3,3,1);

/*Table structure for table `bhac_manageuseractivity` */

DROP TABLE IF EXISTS `bhac_manageuseractivity`;

CREATE TABLE `bhac_manageuseractivity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_manageuseractivity` */

insert  into `bhac_manageuseractivity`(`id`,`uid`,`aid`) values (1,1,2),(2,2,3),(3,3,1);

/*Table structure for table `bhac_post` */

DROP TABLE IF EXISTS `bhac_post`;

CREATE TABLE `bhac_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `postedBy` int(11) NOT NULL,
  `numOfComment` int(11) NOT NULL,
  `lastEdited` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_post` */

insert  into `bhac_post`(`id`,`aid`,`tid`,`title`,`postedBy`,`numOfComment`,`lastEdited`,`type`) values (1,1,1,NULL,1,1,'2020-04-30 17:10:28',0),(2,1,2,NULL,1,2,'2020-04-30 17:10:29',0);

/*Table structure for table `bhac_role` */

DROP TABLE IF EXISTS `bhac_role`;

CREATE TABLE `bhac_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `state` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_role` */

insert  into `bhac_role`(`id`,`tid`,`state`) values (1,1,0),(2,2,0),(3,3,0),(4,4,0),(5,5,0);

/*Table structure for table `bhac_tag` */

DROP TABLE IF EXISTS `bhac_tag`;

CREATE TABLE `bhac_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `state` int(11) DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_tag` */

insert  into `bhac_tag`(`id`,`name`,`state`,`parent_id`) values (1,'测试',0,NULL),(2,'音乐',0,NULL),(3,'学习',0,NULL),(4,'角色1',0,NULL),(5,'角色2',0,NULL);

/*Table structure for table `bhac_user` */

DROP TABLE IF EXISTS `bhac_user`;

CREATE TABLE `bhac_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phoneNum` varchar(20) NOT NULL,
  `password` varchar(25) NOT NULL,
  `state` int(11) DEFAULT '0',
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `studentId` varchar(30) DEFAULT NULL,
  `avatarUrl` varchar(100) DEFAULT NULL,
  `gender` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phoneNum` (`phoneNum`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bhac_user` */

insert  into `bhac_user`(`id`,`username`,`email`,`phoneNum`,`password`,`state`,`firstName`,`lastName`,`studentId`,`avatarUrl`,`gender`) values (1,'r1','xxxxx@xx.com','18100011000','123asbv.',0,NULL,NULL,NULL,NULL,0),(2,'r2','yyyyy@xx.com','18100011001','124asbv.',0,NULL,NULL,NULL,NULL,1),(3,'r3','zzzzz@xx.com','18100011002','125asbv.',0,NULL,NULL,NULL,NULL,1),(5,'r4','aaaaa@xx.com','18100011003','126asbv',0,NULL,NULL,NULL,NULL,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
