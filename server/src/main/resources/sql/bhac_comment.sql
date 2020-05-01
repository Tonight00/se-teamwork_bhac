/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_comment` (
	`id` int (11),
	`pid` int (11),
	`seqNum` int (11),
	`postedBy` int (11),
	`parentId` int (11),
	`content` blob ,
	`date` datetime 
); 
insert into `bhac_comment` (`id`, `pid`, `seqNum`, `postedBy`, `parentId`, `content`, `date`) values('1','1','1','1',NULL,'first_comment','2020-04-30 18:06:28');
insert into `bhac_comment` (`id`, `pid`, `seqNum`, `postedBy`, `parentId`, `content`, `date`) values('2','2','2','1',NULL,'second_comment','2020-04-30 18:06:28');
insert into `bhac_comment` (`id`, `pid`, `seqNum`, `postedBy`, `parentId`, `content`, `date`) values('3','3','3','1',NULL,'third_comment','2020-04-30 18:06:28');
