/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.25 : Database - ysg_marketing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ysg_marketing` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ysg_marketing`;

/*Table structure for table `activity_goods` */

DROP TABLE IF EXISTS `activity_goods`;

CREATE TABLE `activity_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动商品id',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `goods_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` varchar(255) DEFAULT NULL COMMENT '商品详情的图片',
  `original_price` decimal(12,2) DEFAULT NULL COMMENT '原价',
  `goods_price` decimal(12,2) DEFAULT NULL COMMENT '单价',
  `purchase_limit` int(11) DEFAULT NULL COMMENT '限购数量',
  `inventory` int(11) DEFAULT NULL COMMENT '库存',
  `activity_rule` text COMMENT '活动规则',
  `activity_type` varchar(2) DEFAULT NULL COMMENT '活动类型',
  `activity_music` varchar(255) DEFAULT NULL COMMENT '音乐',
  `background_image` varchar(255) DEFAULT NULL COMMENT '背景图',
  `join_number` int(11) DEFAULT NULL COMMENT '参与人数',
  `scan_number` int(11) DEFAULT NULL COMMENT '浏览人数',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '截至时间',
  `reward_amount` decimal(4,2) DEFAULT NULL COMMENT '奖励金额',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否开启0：关闭；1：开启',
  `store_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `remark_1` varchar(255) DEFAULT NULL COMMENT '备注1',
  `remark_2` varchar(255) DEFAULT NULL COMMENT '备注2',
  `remark_3` varchar(255) DEFAULT NULL COMMENT '备注3',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='活动信息表';

/*Data for the table `activity_goods` */

insert  into `activity_goods`(`goods_id`,`goods_name`,`goods_image`,`goods_detail`,`original_price`,`goods_price`,`purchase_limit`,`inventory`,`activity_rule`,`activity_type`,`activity_music`,`background_image`,`join_number`,`scan_number`,`begin_time`,`end_time`,`reward_amount`,`status`,`store_id`,`remark_1`,`remark_2`,`remark_3`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(2,'19.9元抢购','http://ppqnam2qx.bkt.clouddn.com/FgsGqGxKQPlX_skThoGIN4pfBo57','http://ppqnam2qx.bkt.clouddn.com/FgsGqGxKQPlX_skThoGIN4pfBo57',NULL,19.90,1,NULL,'123123',NULL,'http://ppqnam2qx.bkt.clouddn.com/Fg5gHNK9tf7m6HNGb9uau5fhycjD',NULL,NULL,NULL,'2019-04-12 00:00:00','2019-05-19 00:00:00',5.00,1,2,NULL,NULL,NULL,'2019-04-11 13:17:02','管理员','2019-04-16 13:58:27','管理员');

/*Table structure for table `client_user` */

DROP TABLE IF EXISTS `client_user`;

CREATE TABLE `client_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(45) DEFAULT NULL COMMENT '用户昵称',
  `username` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT '盐',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  `gender` tinyint(1) DEFAULT NULL COMMENT '是否有效 0 女 1 男',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登陆时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

/*Data for the table `client_user` */

insert  into `client_user`(`user_id`,`openid`,`user_name`,`phone`,`avatar`,`nick_name`,`username`,`password`,`salt`,`status`,`gender`,`age`,`create_time`,`last_login_time`) values 
(2,NULL,'赵志沛','12313123','http://ppqnam2qx.bkt.clouddn.com/FgsGqGxKQPlX_skThoGIN4pfBo57',NULL,NULL,'1',NULL,1,NULL,NULL,'2019-04-11 18:27:21','2019-04-11 18:27:21'),
(3,NULL,'asf','12313123','http://ppqnam2qx.bkt.clouddn.com/FgsGqGxKQPlX_skThoGIN4pfBo57',NULL,NULL,NULL,NULL,1,NULL,NULL,'2019-04-11 18:28:39','2019-04-11 18:28:39');

/*Table structure for table `order_info` */

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(45) DEFAULT NULL COMMENT '业务编号',
  `store_id` int(11) DEFAULT NULL COMMENT '店铺id',
  `p_user_id` int(11) DEFAULT NULL COMMENT '邀请人用户id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `pay_account` varchar(40) DEFAULT NULL COMMENT '第三方支付唯一标识',
  `buy_count` int(11) DEFAULT NULL COMMENT '购买数量',
  `payment_amount` decimal(12,2) DEFAULT NULL COMMENT '支付金额',
  `pay_type` tinyint(1) DEFAULT '1' COMMENT '支付方式 1 微信 2 支付宝',
  `order_amount` decimal(12,2) DEFAULT NULL COMMENT '订单金额',
  `status` tinyint(1) DEFAULT '1' COMMENT '订单状态 1 已下单 2 已支付 3 已完成 4 已取消 5 已退款',
  `order_code` varchar(255) DEFAULT NULL COMMENT '核销码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark_1` varchar(255) DEFAULT NULL COMMENT '备注1',
  `remark_2` varchar(255) DEFAULT NULL COMMENT '备注2',
  `remark_3` varchar(255) DEFAULT NULL COMMENT '备注3',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='订单信息';

/*Data for the table `order_info` */

insert  into `order_info`(`order_id`,`order_no`,`store_id`,`p_user_id`,`user_id`,`goods_id`,`pay_account`,`buy_count`,`payment_amount`,`pay_type`,`order_amount`,`status`,`order_code`,`create_time`,`pay_time`,`update_time`,`remark_1`,`remark_2`,`remark_3`) values 
(1,'YSG6e50d634',2,NULL,2,2,NULL,1,19.90,1,19.90,3,'YSG6e50d634','2019-04-11 18:27:22','2019-04-11 18:27:21','2019-04-17 17:12:57',NULL,NULL,NULL),
(2,'YSG6e50d635',2,NULL,3,2,NULL,1,19.90,1,19.90,1,'YSG6e50d633','2019-04-11 18:28:39','2019-04-11 18:28:39','2019-04-11 18:28:39',NULL,NULL,NULL),
(10,'YSG6e50d635',2,NULL,2,2,NULL,1,19.90,1,19.90,1,'YSG6e50d635','2019-04-17 15:06:10','2019-04-17 15:06:10','2019-04-17 15:06:10',NULL,NULL,NULL);

/*Table structure for table `scan_record` */

DROP TABLE IF EXISTS `scan_record`;

CREATE TABLE `scan_record` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) DEFAULT NULL COMMENT 'openid',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_time` datetime DEFAULT NULL COMMENT '浏览时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `scan_record` */

/*Table structure for table `store_info` */

DROP TABLE IF EXISTS `store_info`;

CREATE TABLE `store_info` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `store_name` varchar(50) DEFAULT NULL COMMENT '店铺名称',
  `store_image` varchar(255) DEFAULT NULL COMMENT '店铺loge',
  `store_address` varchar(255) DEFAULT NULL COMMENT '店铺地址',
  `store_phone` varchar(20) DEFAULT NULL COMMENT '店铺联系方式',
  `link_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否开启0：关闭；1：开启',
  `store_code` varchar(255) DEFAULT NULL COMMENT '店铺微信二维码',
  `remark_1` varchar(255) DEFAULT NULL COMMENT '备注1',
  `remark_2` varchar(255) DEFAULT NULL COMMENT '备注2',
  `remark_3` varchar(255) DEFAULT NULL COMMENT '备注3',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='店铺表';

/*Data for the table `store_info` */

insert  into `store_info`(`store_id`,`store_name`,`store_image`,`store_address`,`store_phone`,`link_name`,`status`,`store_code`,`remark_1`,`remark_2`,`remark_3`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(2,'test001',NULL,'啊啊手动阀','13524002701','啊',0,'http://ppqnam2qx.bkt.clouddn.com/FvG41YMFLOr5OffW_h8IepTFk173',NULL,NULL,NULL,'2019-04-11 13:17:02','管理员','2019-04-16 13:58:27','管理员');

/*Table structure for table `sys_company` */

DROP TABLE IF EXISTS `sys_company`;

CREATE TABLE `sys_company` (
  `company_id` int(8) NOT NULL AUTO_INCREMENT,
  `code` varchar(15) DEFAULT NULL COMMENT '公司编码',
  `name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `short_name` varchar(10) DEFAULT NULL COMMENT '公司简称',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '上级代码',
  `logo` varchar(50) DEFAULT NULL COMMENT '公司LOGO',
  `comment` varchar(300) DEFAULT NULL COMMENT '公司介绍',
  `address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `city_code` varchar(10) DEFAULT NULL COMMENT '城市代码',
  `status` int(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_company` */

/*Table structure for table `sys_department` */

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `department_id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `company_id` int(8) DEFAULT NULL COMMENT '公司代码',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司代码',
  `code` varchar(50) DEFAULT NULL COMMENT '部门编号',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '上级代码',
  `comment` varchar(200) DEFAULT NULL COMMENT '部门描述',
  `status` int(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `index_department_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_department` */

/*Table structure for table `sys_department_user` */

DROP TABLE IF EXISTS `sys_department_user`;

CREATE TABLE `sys_department_user` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `department_id` int(8) DEFAULT NULL COMMENT '部门编码',
  `user_id` int(8) DEFAULT NULL COMMENT '用户号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_department_user` */

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`num`,`pid`,`name`,`tips`) values 
(1,0,0,'状态',NULL),
(2,0,1,'启用',NULL),
(3,1,1,'禁用',NULL),
(4,0,0,'性别',NULL),
(5,1,4,'男',NULL),
(6,2,4,'女',NULL);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `is_menu` int(11) DEFAULT NULL COMMENT '是否是菜单',
  `level` int(11) DEFAULT NULL COMMENT '菜单层级',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `status` int(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`name`,`url`,`is_menu`,`level`,`sort`,`status`,`icon`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(1,0,'系统根目录','root',0,0,1,1,NULL,'2018-05-03 18:31:54','admin','2018-05-08 11:02:55',NULL),
(2,1,'系统设置','system',1,1,1,1,'fa-gear','2018-05-04 09:47:06','admin','2018-05-08 11:02:55',NULL),
(3,2,'用户管理','system/user/list',1,2,4,1,'','2018-05-04 09:53:21','admin','2018-06-04 15:01:05','管理员'),
(4,3,'用户添加','system/user/add',0,3,2,1,'','2018-05-04 10:57:54','admin','2018-06-07 16:03:22','管理员'),
(5,3,'用户修改','system/user/edit',0,3,3,1,'','2018-05-07 11:15:23','admin','2018-06-07 16:03:25','管理员'),
(6,3,'用户查看','system/user/View',0,3,1,1,'','2018-05-07 16:21:12','admin','2018-06-07 16:03:20','管理员'),
(7,3,'用户删除','system/user/delete',0,3,4,1,'','2018-05-07 16:21:52','admin','2018-05-08 11:02:55',NULL),
(8,2,'角色管理','system/role/list',1,2,5,1,'','2018-05-07 16:27:47','admin','2018-06-04 15:01:13','管理员'),
(9,8,'角色添加','system/role/add',0,3,1,1,'','2018-05-07 16:30:31','admin','2018-05-08 11:02:55',NULL),
(10,8,'角色编辑','system/role/edit',0,3,3,1,'','2018-05-07 16:31:08','admin','2018-06-07 16:03:43','管理员'),
(11,8,'角色删除','system/role/delete',0,3,2,1,'','2018-05-07 16:31:40','admin','2018-05-07 16:37:24',NULL),
(12,8,'角色配权','system/role/grant',0,3,4,1,'','2018-05-07 16:32:18','admin','2018-06-12 12:37:09','管理员'),
(13,2,'菜单管理','system/menu/list',1,2,1,1,'','2018-05-07 16:34:58','admin','2018-06-04 14:51:31','管理员'),
(14,13,'菜单添加','system/menu/add',0,3,1,1,'','2018-05-07 16:35:36','admin','2018-06-07 16:09:10','管理员'),
(15,13,'菜单编辑','system/menu/edit',0,3,2,1,'','2018-05-07 16:36:08','admin','2018-05-08 11:02:55',NULL),
(16,13,'菜单删除','system/menu/delete',0,3,3,1,'','2018-05-07 16:36:50','admin','2018-06-07 16:00:47','管理员'),
(17,2,'公司管理','system/company/list',1,2,2,1,'','2018-05-17 18:18:43','admin','2018-06-04 14:51:39','管理员'),
(18,17,'公司添加','system/company/add',0,3,1,1,'','2018-05-22 16:25:54','admin','2018-05-08 11:02:55',NULL),
(19,17,'公司编辑','system/company/edit',0,3,2,1,'','2018-05-23 09:51:04','admin','2018-05-08 11:02:55',NULL),
(20,17,'公司删除','system/company/delete',0,3,3,1,'','2018-05-23 16:38:49','admin','2018-05-08 11:02:55',NULL),
(21,2,'部门管理','system/department/list',1,2,3,1,'','2018-06-04 14:57:43','管理员',NULL,NULL),
(22,21,'部门新增','system/department/add',0,3,1,1,'','2018-06-04 14:58:24','管理员','2018-06-09 16:38:39','管理员'),
(23,21,'部门编辑','system/department/edit',0,3,2,1,'','2018-06-04 14:59:01','管理员','2018-06-09 16:38:51','管理员'),
(26,1,'活动管理','activity',1,1,2,1,'fa-tags','2019-04-11 11:18:31','管理员',NULL,NULL),
(27,26,'活动商品列表','activity/goods/list',1,2,1,1,'','2019-04-11 11:18:58','管理员',NULL,NULL),
(28,27,'新增活动','activity/goods/add',0,3,1,1,'','2019-04-11 11:19:24','管理员',NULL,NULL),
(29,27,'编辑活动','activity/goods/edit',0,3,2,1,'','2019-04-11 11:19:48','管理员',NULL,NULL),
(30,27,'关闭活动','activity/goods/delete',0,3,3,1,'','2019-04-11 11:20:09','管理员',NULL,NULL);

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_notice` */

/*Table structure for table `sys_operation_log` */

DROP TABLE IF EXISTS `sys_operation_log`;

CREATE TABLE `sys_operation_log` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '操作人ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '操作人姓名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(255) DEFAULT NULL COMMENT '方法',
  `args` text COMMENT '参数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Data for the table `sys_operation_log` */

insert  into `sys_operation_log`(`id`,`user_id`,`user_name`,`operation`,`method`,`args`,`create_time`) values 
(34,1,'admin','编辑菜单','save','[参数1:{\"createBy\":\"管理员\",\"createTime\":1554952711070,\"icon\":\"fa-tags\",\"id\":26,\"isMenu\":1,\"level\":1,\"name\":\"活动管理\",\"parentId\":1,\"sort\":2,\"status\":1,\"url\":\"activity\"}]','2019-04-11 11:18:31'),
(35,1,'admin','编辑菜单','save','[参数1:{\"createBy\":\"管理员\",\"createTime\":1554952738404,\"icon\":\"\",\"id\":27,\"isMenu\":1,\"level\":2,\"name\":\"活动商品列表\",\"parentId\":26,\"sort\":1,\"status\":1,\"url\":\"activity/goods/list\"}]','2019-04-11 11:18:58'),
(36,1,'admin','编辑菜单','save','[参数1:{\"createBy\":\"管理员\",\"createTime\":1554952764071,\"icon\":\"\",\"id\":28,\"isMenu\":0,\"level\":3,\"name\":\"新增活动\",\"parentId\":27,\"sort\":1,\"status\":1,\"url\":\"activity/goods/add\"}]','2019-04-11 11:19:24'),
(37,1,'admin','编辑菜单','save','[参数1:{\"createBy\":\"管理员\",\"createTime\":1554952787572,\"icon\":\"\",\"id\":29,\"isMenu\":0,\"level\":3,\"name\":\"编辑活动\",\"parentId\":27,\"sort\":2,\"status\":1,\"url\":\"activity/goods/edit\"}]','2019-04-11 11:19:48'),
(38,1,'admin','编辑菜单','save','[参数1:{\"createBy\":\"管理员\",\"createTime\":1554952809419,\"icon\":\"\",\"id\":30,\"isMenu\":0,\"level\":3,\"name\":\"关闭活动\",\"parentId\":27,\"sort\":3,\"status\":1,\"url\":\"activity/goods/delete\"}]','2019-04-11 11:20:09'),
(39,1,'admin','角色编辑','update','[参数1:{\"menuIds\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\",\"10\",\"11\",\"12\",\"13\",\"14\",\"15\",\"16\",\"17\",\"18\",\"19\",\"21\",\"22\",\"26\",\"27\",\"28\",\"29\",\"30\"],\"name\":\"超级管理员\",\"remark\":\"admin\",\"roleId\":\"1\"}]','2019-04-11 11:20:21'),
(40,1,'admin','关闭活动','delete','[参数1:\"2\"][参数2:1]','2019-04-11 14:13:09'),
(41,1,'admin','关闭活动','delete','[参数1:\"2\"][参数2:0]','2019-04-11 14:13:12'),
(42,1,'admin','关闭活动','delete','[参数1:\"2\"][参数2:1]','2019-04-11 14:13:16');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `value` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `unique_role_name` (`name`),
  UNIQUE KEY `unique_role_value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`name`,`value`,`remark`,`create_time`,`update_time`,`status`) values 
(1,'超级管理员','admin','admin','2017-06-20 15:08:45','2019-04-11 11:20:20',1);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`,`create_time`) values 
(1,1,'2019-04-11 11:20:21'),
(1,2,'2019-04-11 11:20:21'),
(1,3,'2019-04-11 11:20:21'),
(1,4,'2019-04-11 11:20:21'),
(1,5,'2019-04-11 11:20:21'),
(1,6,'2019-04-11 11:20:21'),
(1,7,'2019-04-11 11:20:21'),
(1,8,'2019-04-11 11:20:21'),
(1,9,'2019-04-11 11:20:21'),
(1,10,'2019-04-11 11:20:21'),
(1,11,'2019-04-11 11:20:21'),
(1,12,'2019-04-11 11:20:21'),
(1,13,'2019-04-11 11:20:21'),
(1,14,'2019-04-11 11:20:21'),
(1,15,'2019-04-11 11:20:21'),
(1,16,'2019-04-11 11:20:21'),
(1,17,'2019-04-11 11:20:21'),
(1,18,'2019-04-11 11:20:21'),
(1,19,'2019-04-11 11:20:21'),
(1,21,'2019-04-11 11:20:21'),
(1,22,'2019-04-11 11:20:21'),
(1,26,'2019-04-11 11:20:21'),
(1,27,'2019-04-11 11:20:21'),
(1,28,'2019-04-11 11:20:21'),
(1,29,'2019-04-11 11:20:21'),
(1,30,'2019-04-11 11:20:21');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `username` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT '盐',
  `name` varchar(45) DEFAULT NULL COMMENT '用户姓名',
  `merchant_id` varchar(45) DEFAULT NULL COMMENT '商家用户id',
  `phone` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `status` int(1) DEFAULT '1' COMMENT '是否有效 0 无效 1 有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `update_by` datetime DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`avatar`,`username`,`password`,`salt`,`name`,`merchant_id`,`phone`,`status`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(1,NULL,'admin','a5322b1321d2c849e22fa3f62bf87d6b','u2w3z','管理员','1','1352400201',1,'2018-05-13 20:09:54','1','2017-06-26 17:31:41',NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`create_time`,`create_by`) values 
(1,1,1,'2017-09-11 13:02:45',NULL);

/*Table structure for table `user_account_record` */

DROP TABLE IF EXISTS `user_account_record`;

CREATE TABLE `user_account_record` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(18) DEFAULT NULL COMMENT '账户',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '1:返现;2:提现;3:消费;4 冻结',
  `amount` decimal(10,2) NOT NULL COMMENT '操作金额',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '当前余额',
  `trade_no` varchar(50) NOT NULL COMMENT '流水号 收入和消费存入订单order_no  提现存入提现流水号',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark_1` varchar(255) DEFAULT NULL COMMENT '备注1 goods_id',
  `remark_2` varchar(255) DEFAULT NULL COMMENT '备注2 user_id',
  `remark_3` varchar(255) DEFAULT NULL COMMENT '备注3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='返现记录表';

/*Data for the table `user_account_record` */

insert  into `user_account_record`(`id`,`account`,`user_id`,`type`,`amount`,`balance`,`trade_no`,`desc`,`create_time`,`remark_1`,`remark_2`,`remark_3`) values 
(1,NULL,2,1,50.00,NULL,'12312',NULL,'2019-04-17 11:09:34','2',NULL,NULL),
(2,NULL,3,1,40.00,NULL,'123',NULL,'2019-04-17 11:17:30','2',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
