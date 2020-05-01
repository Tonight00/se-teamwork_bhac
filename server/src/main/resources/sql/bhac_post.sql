/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_post` (
	`id` int (11),
	`aid` int (11),
	`tid` int (11),
	`title` varchar (150),
	`postedBy` int (11),
	`numOfComment` int (11),
	`lastEdited` datetime ,
	`type` int (11)
); 
insert into `bhac_post` (`id`, `aid`, `tid`, `title`, `postedBy`, `numOfComment`, `lastEdited`, `type`) values('1','1','1',NULL,'1','1','2020-04-30 17:10:28','0');
insert into `bhac_post` (`id`, `aid`, `tid`, `title`, `postedBy`, `numOfComment`, `lastEdited`, `type`) values('2','1','2',NULL,'1','2','2020-04-30 17:10:29','0');
