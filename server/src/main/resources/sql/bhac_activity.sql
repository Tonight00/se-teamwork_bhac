/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_activity` (
	`id` int (11),
	`category` int (11),
	`uid` int (11),
	`title` varchar (300),
	`ddl` datetime ,
	`begin` datetime ,
	`end` datetime ,
	`brief` varchar (150),
	`isOpen` int (11),
	`limitPeopleNum` int (11),
	`state` int (11),
	`extra` varchar (600)
); 
insert into `bhac_activity` (`id`, `category`, `uid`, `title`, `ddl`, `begin`, `end`, `brief`, `isOpen`, `limitPeopleNum`, `state`, `extra`) values('1','1','1','post1',NULL,'2020-04-30 13:59:02',NULL,NULL,'0','-1','0',NULL);
insert into `bhac_activity` (`id`, `category`, `uid`, `title`, `ddl`, `begin`, `end`, `brief`, `isOpen`, `limitPeopleNum`, `state`, `extra`) values('2','1','2','post2',NULL,'2020-04-30 13:59:02',NULL,NULL,'0','-1','0',NULL);
insert into `bhac_activity` (`id`, `category`, `uid`, `title`, `ddl`, `begin`, `end`, `brief`, `isOpen`, `limitPeopleNum`, `state`, `extra`) values('3','1','3','post3',NULL,'2020-04-30 13:59:02',NULL,NULL,'0','-1','0',NULL);
