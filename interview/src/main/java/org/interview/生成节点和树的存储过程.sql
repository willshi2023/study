/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.26 : Database - bit_shield_member
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bit_shield_member` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bit_shield_member`;

/*Table structure for table `user_family_tree` */

DROP TABLE IF EXISTS `user_family_tree`;

CREATE TABLE `user_family_tree` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `user_node_level` int(11) DEFAULT '1' COMMENT '用户是第几代',
  `user_child_number` int(11) DEFAULT '1' COMMENT '用户是第几个孩子',
  `member_node_level` int(11) DEFAULT '1' COMMENT '成员是第几代',
  `member_child_number` int(11) DEFAULT '1' COMMENT '成员是第几个孩子',
  `member_user_id` varchar(255) DEFAULT NULL COMMENT '祖谱成员id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8 COMMENT='用户祖谱树';



DROP TABLE IF EXISTS `user_inviter`;

CREATE TABLE `user_inviter` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(24) NOT NULL COMMENT '用户ID',
  `inviter_id` varchar(24) NOT NULL COMMENT '邀请人ID',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `note` varchar(64) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `USERINVITER` (`inviter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户邀请列表';

/* Procedure structure for procedure `p_generateNode` */

/*!50003 DROP PROCEDURE IF EXISTS  `p_generateNode` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `p_generateNode`(IN userId VARCHAR(255),IN parentId VARCHAR(255))
BEGIN
	-- 给一棵已存在的树挂载一个节点,需要指定自己的id以及被挂载的id,默认指定一个异常的值,以便容易发现问题
	DECLARE userNodeLevel INT DEFAULT 500;
	DECLARE userChildNumber INT DEFAULT 500;
	-- 继承父级的族谱,修改成自己的名字
	if parentId != '' then
		SET userNodeLevel = (SELECT user_node_level+1 FROM user_family_tree WHERE user_id=member_user_id AND user_id=parentId);
		SET userChildNumber = (SELECT COUNT(*)+1 FROM user_family_tree WHERE member_user_id=parentId AND user_node_level=userNodeLevel);
		INSERT INTO `user_family_tree`(user_id,user_node_level,user_child_number,member_node_level,member_child_number,member_user_id) 
		SELECT userId,userNodeLevel,userChildNumber,member_node_level,member_child_number,member_user_id 
		FROM `user_family_tree` WHERE user_id=parentId;
	else
		set userNodeLevel = 1;
		set userChildNumber = (select count(*)+1 from `user_family_tree` where user_id = member_user_id and user_node_level=1);
	end if;
	-- 记录自己的族谱
	INSERT INTO `user_family_tree`(user_id,user_node_level,user_child_number,member_node_level,member_child_number,member_user_id)
	VALUES (userId,userNodeLevel,userChildNumber,userNodeLevel,userChildNumber,userId);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `p_generateTree` */

/*!50003 DROP PROCEDURE IF EXISTS  `p_generateTree` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `p_generateTree`()
BEGIN
	DECLARE levelOneChildNumber INT DEFAULT 1;
	DECLARE userId VARCHAR(255) DEFAULT '';
	DECLARE parentId VARCHAR(255) DEFAULT '';
	DECLARE allChildNumber INT DEFAULT 1;
	declare i int default 1;
	
	-- 自动根据邀请关系生成树
	-- 先单独将每棵树的第一个节点插入
	drop temporary table if exists list_tmp;
	create temporary table list_tmp(id int AUTO_INCREMENT,user_id varchar(255),PRIMARY KEY (`id`));
	insert into list_tmp(user_id) SELECT inviter_id FROM `user_inviter` WHERE inviter_id NOT IN (SELECT user_id FROM user_inviter) GROUP BY inviter_id;
	set levelOneChildNumber = (select count(*) from list_tmp);
	while i<=levelOneChildNumber do
		set userId = (select user_id from list_tmp where id=i);
		call p_generateNode(userId,'');
		SET i=i+1;
	end while;
	-- 将临时表删除
	DROP TEMPORARY TABLE IF EXISTS list_tmp;
	-- 创建临时表2,用于存放子类的邀请关系,避免id不为1的时候出现的异常
	DROP TEMPORARY TABLE IF EXISTS list_tmp2;
	CREATE TEMPORARY TABLE list_tmp2(id INT AUTO_INCREMENT,user_id VARCHAR(255),PRIMARY KEY (`id`));
	INSERT INTO list_tmp2(user_id) SELECT user_id FROM user_inviter;
	-- 然后插入每棵树的节点
	set allChildNumber = (select count(*) from `list_tmp2`);
	set i=1;
	while i<=allChildNumber do 
	set userId = (select user_id from `list_tmp2` where id=i);
	set parentId = (select inviter_id from `user_inviter` where user_id=userId);
	call p_generateNode(userId,parentId);
	SET i=i+1;
	end while; 
	DROP TEMPORARY TABLE IF EXISTS list_tmp2;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
