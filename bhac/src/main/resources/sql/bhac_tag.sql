/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bhac_tag` (
	`id` int (11),
	`name` varchar (150),
	`state` int (11),
	`parent_id` int (11)
); 
insert into `bhac_tag` (`id`, `name`, `state`, `parent_id`) values('1','测试','0',NULL);
insert into `bhac_tag` (`id`, `name`, `state`, `parent_id`) values('2','音乐','0',NULL);
insert into `bhac_tag` (`id`, `name`, `state`, `parent_id`) values('3','学习','0',NULL);
insert into `bhac_tag` (`id`, `name`, `state`, `parent_id`) values('4','角色1','0',NULL);
insert into `bhac_tag` (`id`, `name`, `state`, `parent_id`) values('5','角色2','0',NULL);
