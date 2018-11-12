/*
Navicat MySQL Data Transfer

Source Server         : dev_ajp
Source Server Version : 50641
Source Host           : 10.141.100.18:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50641
File Encoding         : 65001

Date: 2018-11-12 14:54:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_xgh_consume
-- ----------------------------
DROP TABLE IF EXISTS `t_xgh_consume`;
CREATE TABLE `t_xgh_consume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `amt` decimal(15,4) NOT NULL COMMENT '金额',
  `success_time` datetime DEFAULT '1900-01-01 00:00:00' COMMENT '到账成功时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '新建=0,成功=1,失败=2',
  `product_desc` varchar(512) NOT NULL DEFAULT '' COMMENT '商品',
  `memo` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  `point` decimal(15,4) DEFAULT '0.0000' COMMENT '积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='消费记录';

-- ----------------------------
-- Table structure for t_xgh_deposit
-- ----------------------------
DROP TABLE IF EXISTS `t_xgh_deposit`;
CREATE TABLE `t_xgh_deposit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `amt` decimal(15,4) NOT NULL COMMENT '金额',
  `present_amt` decimal(15,4) NOT NULL COMMENT '赠送的金额',
  `success_time` datetime DEFAULT '1900-01-01 00:00:00' COMMENT '到账成功时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '新建=0,成功=1,失败=2',
  `memo` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='充值记录';

-- ----------------------------
-- Table structure for t_xgh_member
-- ----------------------------
DROP TABLE IF EXISTS `t_xgh_member`;
CREATE TABLE `t_xgh_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `open_id` varchar(128) NOT NULL DEFAULT '' COMMENT '微信openid',
  `nick_name` varchar(128) NOT NULL DEFAULT '' COMMENT '昵称',
  `phone` varchar(128) NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar_url` varchar(256) NOT NULL DEFAULT '' COMMENT '头像url',
  `role` varchar(64) NOT NULL DEFAULT 'member' COMMENT 'admin,member',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='会员信息表';

-- ----------------------------
-- Table structure for t_xgh_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_xgh_wallet`;
CREATE TABLE `t_xgh_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `total_amt` decimal(15,4) NOT NULL COMMENT '总金额',
  `available_amt` decimal(15,4) NOT NULL COMMENT '可用金额',
  `memo` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  `point` decimal(15,4) DEFAULT '0.0000' COMMENT '积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='钱包';

-- ----------------------------
-- Procedure structure for alterTable
-- ----------------------------
DROP PROCEDURE IF EXISTS `alterTable`;
DELIMITER ;;
CREATE DEFINER=`JDev`@`%` PROCEDURE `alterTable`(IN tableName VARCHAR(10),IN columnName VARCHAR(1000),IN columnType VARCHAR(1000))
BEGIN  
  #拼接修改列名的sql语句  
  SET @sqlstr = "ALTER TABLE ";  
  SET @sqlstr = CONCAT(@sqlstr,tableName," CHANGE ",columnName," ",LCASE(columnName)," ",columnType);  
  #输出sql语句方便调试的时候查看  
  select @sqlstr;  
  PREPARE stmt FROM @sqlstr;  
  EXECUTE stmt;  
  DEALLOCATE PREPARE stmt;  
  
 END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for randCreateView
-- ----------------------------
DROP PROCEDURE IF EXISTS `randCreateView`;
DELIMITER ;;
CREATE DEFINER=`JDev`@`%` PROCEDURE `randCreateView`(IN tableName VARCHAR(10))
BEGIN  
  #如果视图已存在就删除创建  
  DROP VIEW IF EXISTS columnName_view;  
  #以下两句正是动态创建视图的关键  
  SET @sqlstr = "CREATE VIEW columnName_view as ";  
  SET @sqlstr = CONCAT(  
   @sqlstr,  
   "select COLUMN_NAME,COLUMN_TYPE  from information_schema.columns where table_schema='tom_test' and table_name=","'",  
   tableName,"'"  
  );  
  #为拼接的这条sql语句创建语句对象  
  PREPARE stmt FROM @sqlstr;  
  #执行这条sql  
  EXECUTE stmt;  
  DEALLOCATE PREPARE stmt;  
 END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for hello
-- ----------------------------
DROP FUNCTION IF EXISTS `hello`;
DELIMITER ;;
CREATE DEFINER=`JDev`@`%` FUNCTION `hello`() RETURNS varchar(255) CHARSET utf8
BEGIN
     RETURN 'Hello  world,i am mysql';
     END
;;
DELIMITER ;
