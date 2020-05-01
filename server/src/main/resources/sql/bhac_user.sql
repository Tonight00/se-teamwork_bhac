/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_user` (
	`id` int (11),
	`username` varchar (90),
	`email` varchar (90),
	`phoneNum` varchar (60),
	`password` varchar (75),
	`state` int (11),
	`firstName` varchar (60),
	`lastName` varchar (60),
	`studentId` varchar (90),
	`avatarUrl` varchar (300),
	`gender` int (11)
); 
insert into `bhac_user` (`id`, `username`, `email`, `phoneNum`, `password`, `state`, `firstName`, `lastName`, `studentId`, `avatarUrl`, `gender`) values('1','r1','xxxxx@xx.com','18100011000','123asbv.','0',NULL,NULL,NULL,NULL,'0');
insert into `bhac_user` (`id`, `username`, `email`, `phoneNum`, `password`, `state`, `firstName`, `lastName`, `studentId`, `avatarUrl`, `gender`) values('2','r2','yyyyy@xx.com','18100011001','124asbv.','0',NULL,NULL,NULL,NULL,'1');
insert into `bhac_user` (`id`, `username`, `email`, `phoneNum`, `password`, `state`, `firstName`, `lastName`, `studentId`, `avatarUrl`, `gender`) values('3','r3','zzzzz@xx.com','18100011002','125asbv.','0',NULL,NULL,NULL,NULL,'1');
