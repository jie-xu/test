/*
Navicat MySQL Data Transfer
Source Server         : localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : lsm
Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001
Date: 2018-09-17 19:48:28
*/
-- ----------------------------
-- Table structure for `t_rbac_department`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_department` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '联系地址',
  `phone` varchar(50) NOT NULL DEFAULT '' COMMENT '联系电话',
  `remarks` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '上级部门',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';
-- ----------------------------
-- Table structure for `t_rbac_menu`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `text` varchar(50) NOT NULL DEFAULT '' COMMENT '文本标题',
  `icon_cls` varchar(50) NOT NULL DEFAULT '' COMMENT '图标样式',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '打开地址',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父节点',
  `display` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
-- ----------------------------
-- Table structure for `t_rbac_permission`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_code` varchar(100) NOT NULL DEFAULT '' COMMENT '权限编码',
  `permission_name` varchar(100) NOT NULL DEFAULT '' COMMENT '权限名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '权限描述',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';
-- ----------------------------
-- Table structure for `t_rbac_role`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
-- ----------------------------
-- Table structure for `t_rbac_role_menu`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_menu_id` (`role_id`,`menu_id`),
  KEY `idx_role_menu_menuid` (`menu_id`),
  CONSTRAINT `idx_role_menu_menuid` FOREIGN KEY (`menu_id`) REFERENCES `t_rbac_menu` (`id`),
  CONSTRAINT `idx_role_menu_roleid` FOREIGN KEY (`role_id`) REFERENCES `t_rbac_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';
-- ----------------------------
-- Table structure for `t_rbac_role_permission`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限id',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_permission_id` (`role_id`,`permission_id`),
  KEY `idx_role_permission_permissionid` (`permission_id`),
  CONSTRAINT `idx_role_permission_permissionid` FOREIGN KEY (`permission_id`) REFERENCES `t_rbac_permission` (`id`),
  CONSTRAINT `idx_role_permission_roleid` FOREIGN KEY (`role_id`) REFERENCES `t_rbac_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';
-- ----------------------------
-- Table structure for `t_rbac_user`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '电子邮件地址',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '连续登陆失败次数',
  `last_login_time` datetime COMMENT '上次登陆日期',
  `with_expire` tinyint(2) NOT NULL DEFAULT '1' COMMENT '密码设置过期 1-是 0-否',
  `expire_time` datetime COMMENT '过期时间',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `department` bigint(20) unsigned COMMENT '所属部门',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`) USING BTREE,
  KEY `idx_department_id` (`department`),
  CONSTRAINT `idx_department_id` FOREIGN KEY (`department`) REFERENCES `t_rbac_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息表';
-- ----------------------------
-- Table structure for `t_rbac_user_role`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_rbac_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户编码',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效状态 1-有效 0-无效',
  `created_by` varchar(50) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建日期',
  `updated_by` varchar(50) NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_role_id` (`user_id`,`role_id`),
  KEY `idx_user_role_roleid` (`role_id`),
  CONSTRAINT `idx_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `t_rbac_role` (`id`),
  CONSTRAINT `idx_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `t_rbac_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
