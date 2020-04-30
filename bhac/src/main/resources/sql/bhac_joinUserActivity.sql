/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_joinuseractivity` (
	`id` int (11),
	`uid` int (11),
	`aid` int (11),
	`state` int (11)
); 
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('1','1','1','0');
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('2','1','2','0');
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('3','1','3','1');
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('4','2','2','1');
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('5','2','3','1');
insert into `bhac_joinuseractivity` (`id`, `uid`, `aid`, `state`) values('6','3','3','1');
