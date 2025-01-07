# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 5.6.39)
# 数据库: big_market_01
# 生成时间: 2024-06-09 02:47:24 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE database if NOT EXISTS `big_market_01` default character set utf8mb4;
use `big_market_01`;

# 转储表 raffle_activity_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_account`;

CREATE TABLE `raffle_activity_account` (
                                           `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                           `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                           `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                           `total_count` int(8) NOT NULL COMMENT '总次数',
                                           `total_count_surplus` int(8) NOT NULL COMMENT '总次数-剩余',
                                           `day_count` int(8) NOT NULL COMMENT '日次数',
                                           `day_count_surplus` int(8) NOT NULL COMMENT '日次数-剩余',
                                           `month_count` int(8) NOT NULL COMMENT '月次数',
                                           `month_count_surplus` int(8) NOT NULL COMMENT '月次数-剩余',
                                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `uq_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动账户表';

LOCK TABLES `raffle_activity_account` WRITE;
/*!40000 ALTER TABLE `raffle_activity_account` DISABLE KEYS */;

INSERT INTO `raffle_activity_account` (`id`, `user_id`, `activity_id`, `total_count`, `total_count_surplus`, `day_count`, `day_count_surplus`, `month_count`, `month_count_surplus`, `create_time`, `update_time`)
VALUES
    (3,'xiaofuge',100301,660,561,660,599,660,599,'2024-03-23 16:38:57','2024-06-09 10:45:11'),
    (4,'12345',100301,10,10,10,10,10,10,'2024-05-01 15:28:50','2024-05-01 15:28:50'),
    (5,'liergou',100301,20,6,20,6,20,6,'2024-05-04 15:30:21','2024-05-04 15:34:10'),
    (6,'liergou2',100301,100,86,100,86,100,86,'2024-05-04 15:35:52','2024-05-04 15:37:37'),
    (7,'user003',100301,100,98,100,98,100,98,'2024-05-25 10:52:19','2024-05-25 10:54:30');

/*!40000 ALTER TABLE `raffle_activity_account` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 raffle_activity_account_day
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_account_day`;

CREATE TABLE `raffle_activity_account_day` (
                                               `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                               `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                               `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                               `day` varchar(10) NOT NULL COMMENT '日期（yyyy-mm-dd）',
                                               `day_count` int(8) NOT NULL COMMENT '日次数',
                                               `day_count_surplus` int(8) NOT NULL COMMENT '日次数-剩余',
                                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                               PRIMARY KEY (`id`),
                                               UNIQUE KEY `uq_user_id_activity_id_day` (`user_id`,`activity_id`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动账户表-日次数';

LOCK TABLES `raffle_activity_account_day` WRITE;
/*!40000 ALTER TABLE `raffle_activity_account_day` DISABLE KEYS */;

INSERT INTO `raffle_activity_account_day` (`id`, `user_id`, `activity_id`, `day`, `day_count`, `day_count_surplus`, `create_time`, `update_time`)
VALUES
    (2,'xiaofuge',100301,'2024-04-05',45,44,'2024-04-05 17:10:31','2024-04-05 17:10:31'),
    (3,'xiaofuge',100301,'2024-04-08',45,44,'2024-04-08 22:52:47','2024-04-08 22:52:47'),
    (4,'xiaofuge',100301,'2024-04-13',45,23,'2024-04-13 11:44:10','2024-04-20 10:51:09'),
    (7,'xiaofuge',100301,'2024-04-20',45,13,'2024-04-20 16:50:38','2024-04-20 16:50:38'),
    (11,'xiaofuge',100301,'2024-05-01',60,40,'2024-05-01 14:51:45','2024-05-01 17:45:10'),
    (12,'xiaofuge',100301,'2024-05-03',90,86,'2024-05-03 09:00:28','2024-05-03 13:28:42'),
    (13,'xiaofuge',100301,'2024-05-04',160,131,'2024-05-04 09:32:02','2024-05-04 15:29:56'),
    (14,'liergou',100301,'2024-05-04',20,6,'2024-05-04 15:30:36','2024-05-04 15:34:10'),
    (15,'liergou2',100301,'2024-05-04',100,86,'2024-05-04 15:35:56','2024-05-04 15:37:37'),
    (16,'user003',100301,'2024-05-25',100,98,'2024-05-25 10:53:19','2024-05-25 10:54:30'),
    (17,'xiaofuge',100301,'2024-05-30',160,154,'2024-05-30 07:24:01','2024-05-30 08:02:18');

/*!40000 ALTER TABLE `raffle_activity_account_day` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 raffle_activity_account_month
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_account_month`;

CREATE TABLE `raffle_activity_account_month` (
                                                 `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                 `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                                 `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                                 `month` varchar(7) NOT NULL COMMENT '月（yyyy-mm）',
                                                 `month_count` int(8) NOT NULL COMMENT '月次数',
                                                 `month_count_surplus` int(8) NOT NULL COMMENT '月次数-剩余',
                                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE KEY `uq_user_id_activity_id_month` (`user_id`,`activity_id`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动账户表-月次数';

LOCK TABLES `raffle_activity_account_month` WRITE;
/*!40000 ALTER TABLE `raffle_activity_account_month` DISABLE KEYS */;

INSERT INTO `raffle_activity_account_month` (`id`, `user_id`, `activity_id`, `month`, `month_count`, `month_count_surplus`, `create_time`, `update_time`)
VALUES
    (7,'xiaofuge',100301,'2024-05',140,79,'2024-05-01 14:51:45','2024-05-30 08:02:18'),
    (8,'liergou',100301,'2024-05',20,6,'2024-05-04 15:30:36','2024-05-04 15:34:10'),
    (9,'liergou2',100301,'2024-05',100,86,'2024-05-04 15:35:56','2024-05-04 15:37:37'),
    (10,'user003',100301,'2024-05',100,98,'2024-05-25 10:53:19','2024-05-25 10:54:30');

/*!40000 ALTER TABLE `raffle_activity_account_month` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 raffle_activity_order_000
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_order_000`;

CREATE TABLE `raffle_activity_order_000` (
                                             `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                             `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                             `sku` bigint(12) NOT NULL COMMENT '商品sku',
                                             `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                             `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                             `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                             `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                             `order_time` datetime NOT NULL COMMENT '下单时间',
                                             `total_count` int(8) NOT NULL COMMENT '总次数',
                                             `day_count` int(8) NOT NULL COMMENT '日次数',
                                             `month_count` int(8) NOT NULL COMMENT '月次数',
                                             `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额【积分】',
                                             `state` varchar(16) NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
                                             `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `uq_order_id` (`order_id`),
                                             UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                             KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动单';



# 转储表 raffle_activity_order_001
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_order_001`;

CREATE TABLE `raffle_activity_order_001` (
                                             `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                             `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                             `sku` bigint(12) NOT NULL COMMENT '商品sku',
                                             `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                             `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                             `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                             `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                             `order_time` datetime NOT NULL COMMENT '下单时间',
                                             `total_count` int(8) NOT NULL COMMENT '总次数',
                                             `day_count` int(8) NOT NULL COMMENT '日次数',
                                             `month_count` int(8) NOT NULL COMMENT '月次数',
                                             `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额【积分】',
                                             `state` varchar(16) NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
                                             `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `uq_order_id` (`order_id`),
                                             UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                             KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动单';

LOCK TABLES `raffle_activity_order_001` WRITE;
/*!40000 ALTER TABLE `raffle_activity_order_001` DISABLE KEYS */;

INSERT INTO `raffle_activity_order_001` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `day_count`, `month_count`, `pay_amount`, `state`, `out_business_no`, `create_time`, `update_time`)
VALUES
    (1096,'xiaofuge',9011,100301,'测试活动',100006,'364757830401','2024-05-01 09:14:43',10,10,10,NULL,'completed','xiaofuge_sku_20240501','2024-05-01 17:14:43','2024-05-01 17:14:43'),
    (1097,'xiaofuge',9011,100301,'测试活动',100006,'157026402583','2024-05-01 09:39:40',10,10,10,NULL,'completed','xiaofuge_sku_20240420','2024-05-01 17:39:40','2024-05-01 17:39:40'),
    (1098,'xiaofuge',9011,100301,'测试活动',100006,'481116019750','2024-05-01 09:41:53',10,10,10,NULL,'completed','xiaofuge_sku_20240401','2024-05-01 17:41:53','2024-05-01 17:41:53'),
    (1099,'xiaofuge',9011,100301,'测试活动',100006,'639151059221','2024-05-01 09:45:10',10,10,10,NULL,'completed','xiaofuge_sku_20240402','2024-05-01 17:45:10','2024-05-01 17:45:10'),
    (4234,'xiaofuge',9011,100301,'测试活动',100006,'129360973197','2024-05-03 05:28:43',10,10,10,NULL,'completed','xiaofuge_sku_20240503','2024-05-03 13:28:42','2024-05-03 13:28:42'),
    (4247,'liergou',9011,100301,'测试活动',100006,'151494600661','2024-05-04 07:32:26',10,10,10,NULL,'completed','liergou_sku_20240504','2024-05-04 15:32:25','2024-05-04 15:32:25'),
    (4248,'xiaofuge',9011,100301,'测试活动',100006,'398083697802','2024-06-08 10:38:59',100,100,100,1.68,'wait_pay','70009240608001','2024-06-08 18:38:59','2024-06-08 18:38:59'),
    (4249,'xiaofuge',9011,100301,'测试活动',100006,'356030049461','2024-06-08 10:54:33',100,100,100,1.68,'wait_pay','70009240608002','2024-06-08 18:54:32','2024-06-08 18:54:32'),
    (4250,'xiaofuge',9011,100301,'测试活动',100006,'605318523315','2024-06-08 10:55:50',100,100,100,1.68,'completed','70009240608003','2024-06-08 18:55:49','2024-06-08 20:29:30'),
    (4251,'xiaofuge',9011,100301,'测试活动',100006,'127654026777','2024-06-08 10:56:28',100,100,100,0.00,'completed','70009240608004','2024-06-08 18:56:27','2024-06-08 18:56:27'),
    (4252,'xiaofuge',9011,100301,'测试活动',100006,'932526349433','2024-06-08 12:33:09',100,100,100,1.68,'completed','70009240608007','2024-06-08 20:33:09','2024-06-08 20:34:13'),
    (4253,'xiaofuge',9011,100301,'测试活动',100006,'073017788455','2024-06-09 01:07:48',100,100,100,1.68,'completed','70009240609001','2024-06-09 09:07:47','2024-06-09 09:11:24'),
    (4254,'xiaofuge',9011,100301,'测试活动',100006,'590031092982','2024-06-09 02:33:49',100,100,100,NULL,'wait_pay','70009240610001','2024-06-09 10:33:48','2024-06-09 10:33:48'),
    (4255,'xiaofuge',9011,100301,'测试活动',100006,'732573213062','2024-06-09 02:43:18',100,100,100,1.68,'completed','70009240610002','2024-06-09 10:43:18','2024-06-09 10:45:11');

/*!40000 ALTER TABLE `raffle_activity_order_001` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 raffle_activity_order_002
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_order_002`;

CREATE TABLE `raffle_activity_order_002` (
                                             `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                             `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                             `sku` bigint(12) NOT NULL COMMENT '商品sku',
                                             `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                             `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                             `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                             `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                             `order_time` datetime NOT NULL COMMENT '下单时间',
                                             `total_count` int(8) NOT NULL COMMENT '总次数',
                                             `day_count` int(8) NOT NULL COMMENT '日次数',
                                             `month_count` int(8) NOT NULL COMMENT '月次数',
                                             `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额【积分】',
                                             `state` varchar(16) NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
                                             `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `uq_order_id` (`order_id`),
                                             UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                             KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动单';

LOCK TABLES `raffle_activity_order_002` WRITE;
/*!40000 ALTER TABLE `raffle_activity_order_002` DISABLE KEYS */;

INSERT INTO `raffle_activity_order_002` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `day_count`, `month_count`, `pay_amount`, `state`, `out_business_no`, `create_time`, `update_time`)
VALUES
    (1,'liergou2',9011,100301,'测试活动',100006,'987026967898','2024-05-04 07:35:53',100,100,100,NULL,'completed','liergou2_sku_20240504','2024-05-04 15:35:52','2024-05-04 15:35:52'),
    (2,'user003',9011,100301,'测试活动',100006,'700446814309','2024-05-25 02:52:20',100,100,100,NULL,'completed','user003_sku_20240525','2024-05-25 10:52:19','2024-05-25 10:52:19');

/*!40000 ALTER TABLE `raffle_activity_order_002` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 raffle_activity_order_003
# ------------------------------------------------------------

DROP TABLE IF EXISTS `raffle_activity_order_003`;

CREATE TABLE `raffle_activity_order_003` (
                                             `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                             `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                             `sku` bigint(12) NOT NULL COMMENT '商品sku',
                                             `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                             `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                             `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                             `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                             `order_time` datetime NOT NULL COMMENT '下单时间',
                                             `total_count` int(8) NOT NULL COMMENT '总次数',
                                             `day_count` int(8) NOT NULL COMMENT '日次数',
                                             `month_count` int(8) NOT NULL COMMENT '月次数',
                                             `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额【积分】',
                                             `state` varchar(16) NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
                                             `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `uq_order_id` (`order_id`),
                                             UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                             KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动单';



# 转储表 task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                        `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                        `topic` varchar(32) NOT NULL COMMENT '消息主题',
                        `message_id` varchar(11) DEFAULT NULL COMMENT '消息编号',
                        `message` varchar(512) NOT NULL COMMENT '消息主体',
                        `state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '任务状态；create-创建、completed-完成、fail-失败',
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uq_message_id` (`message_id`),
                        KEY `idx_state` (`state`),
                        KEY `idx_create_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表，发送MQ';

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;

INSERT INTO `task` (`id`, `user_id`, `topic`, `message_id`, `message`, `state`, `create_time`, `update_time`)
VALUES
    (1,'xiaofuge','send_award','23913710462','{\"data\":{\"awardId\":101,\"awardTitle\":\"OpenAI 增加使用次数\",\"userId\":\"xiaofuge\"},\"id\":\"23913710462\",\"timestamp\":1712374909975}','completed','2024-04-06 11:41:50','2024-04-06 12:14:50'),
    (2,'xiaofuge','send_award','33004806135','{\"data\":{\"awardId\":101,\"awardTitle\":\"OpenAI 增加使用次数\",\"userId\":\"xiaofuge\"},\"id\":\"33004806135\",\"timestamp\":1712375273609}','completed','2024-04-06 11:47:54','2024-04-06 12:14:50'),
    (3,'xiaofuge','send_award','61315401992','{\"data\":{\"awardId\":101,\"awardTitle\":\"OpenAI 增加使用次数\",\"userId\":\"xiaofuge\"},\"id\":\"61315401992\",\"timestamp\":1712377009778}','completed','2024-04-06 12:16:50','2024-04-06 12:16:50'),
    (4,'xiaofuge','send_award','74920280321','{\"data\":{\"awardId\":101,\"awardTitle\":\"OpenAI 增加使用次数\",\"userId\":\"xiaofuge\"},\"id\":\"74920280321\",\"timestamp\":1712377010878}','completed','2024-04-06 12:16:50','2024-04-06 12:16:50'),
    (5,'xiaofuge','send_award','71692388884','{\"data\":{\"awardId\":101,\"awardTitle\":\"OpenAI 增加使用次数\",\"userId\":\"xiaofuge\"},\"id\":\"71692388884\",\"timestamp\":1712377011390}','completed','2024-04-06 12:16:51','2024-04-06 12:16:51'),
    (249,'xiaofuge','credit_adjust_success','50806088557','{\"data\":{\"amount\":-1.68,\"orderId\":\"528225981137\",\"outBusinessNo\":\"70009240610002\",\"userId\":\"xiaofuge\"},\"id\":\"50806088557\",\"timestamp\":1717901110572}','completed','2024-06-09 10:45:11','2024-06-09 10:45:11');

/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_award_record_000
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_award_record_000`;

CREATE TABLE `user_award_record_000` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
                                         `award_id` int(11) NOT NULL COMMENT '奖品ID',
                                         `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
                                         `award_time` datetime NOT NULL COMMENT '中奖时间',
                                         `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id` (`user_id`),
                                         KEY `idx_activity_id` (`activity_id`),
                                         KEY `idx_award_id` (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户中奖记录表';



# 转储表 user_award_record_001
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_award_record_001`;

CREATE TABLE `user_award_record_001` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
                                         `award_id` int(11) NOT NULL COMMENT '奖品ID',
                                         `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
                                         `award_time` datetime NOT NULL COMMENT '中奖时间',
                                         `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id` (`user_id`),
                                         KEY `idx_activity_id` (`activity_id`),
                                         KEY `idx_award_id` (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户中奖记录表';

LOCK TABLES `user_award_record_001` WRITE;
/*!40000 ALTER TABLE `user_award_record_001` DISABLE KEYS */;

INSERT INTO `user_award_record_001` (`id`, `user_id`, `activity_id`, `strategy_id`, `order_id`, `award_id`, `award_title`, `award_time`, `award_state`, `create_time`, `update_time`)
VALUES
    (1,'xiaofuge',100301,100006,'313091076458',101,'OpenAI 增加使用次数','2024-04-06 03:41:50','create','2024-04-06 11:41:50','2024-04-06 11:41:50'),
    (3,'xiaofuge',100301,100006,'313091076459',101,'OpenAI 增加使用次数','2024-04-06 03:47:54','create','2024-04-06 11:47:54','2024-04-06 11:47:54'),
    (6,'xiaofuge',100301,100006,'658772889112',101,'OpenAI 增加使用次数','2024-04-06 04:16:50','create','2024-04-06 12:16:50','2024-04-06 12:16:50'),
    (7,'xiaofuge',100301,100006,'623291703218',101,'OpenAI 增加使用次数','2024-04-06 04:16:51','create','2024-04-06 12:16:50','2024-04-06 12:16:50'),
    (8,'xiaofuge',100301,100006,'619841045154',101,'OpenAI 增加使用次数','2024-04-06 04:16:51','create','2024-04-06 12:16:51','2024-04-06 12:16:51'),
    (9,'xiaofuge',100301,100006,'696947604604',101,'OpenAI 增加使用次数','2024-04-06 04:16:52','create','2024-04-06 12:16:51','2024-04-06 12:16:51'),
    (10,'xiaofuge',100301,100006,'239997053403',101,'OpenAI 增加使用次数','2024-04-06 04:16:52','create','2024-04-06 12:16:52','2024-04-06 12:16:52'),
    (11,'xiaofuge',100301,100006,'837228766840',101,'OpenAI 增加使用次数','2024-04-06 04:16:53','create','2024-04-06 12:16:52','2024-04-06 12:16:52'),
    (12,'xiaofuge',100301,100006,'012609968231',101,'OpenAI 增加使用次数','2024-04-06 04:16:53','create','2024-04-06 12:16:53','2024-04-06 12:16:53'),
    (13,'xiaofuge',100301,100006,'540056227059',101,'OpenAI 增加使用次数','2024-04-06 04:16:54','create','2024-04-06 12:16:54','2024-04-06 12:16:54');

/*!40000 ALTER TABLE `user_award_record_001` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_award_record_002
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_award_record_002`;

CREATE TABLE `user_award_record_002` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
                                         `award_id` int(11) NOT NULL COMMENT '奖品ID',
                                         `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
                                         `award_time` datetime NOT NULL COMMENT '中奖时间',
                                         `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id` (`user_id`),
                                         KEY `idx_activity_id` (`activity_id`),
                                         KEY `idx_award_id` (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户中奖记录表';

LOCK TABLES `user_award_record_002` WRITE;
/*!40000 ALTER TABLE `user_award_record_002` DISABLE KEYS */;

INSERT INTO `user_award_record_002` (`id`, `user_id`, `activity_id`, `strategy_id`, `order_id`, `award_id`, `award_title`, `award_time`, `award_state`, `create_time`, `update_time`)
VALUES
    (1,'liergou2',100301,100006,'319771078666',108,'暴走玩偶','2024-05-04 07:35:57','create','2024-05-04 15:35:56','2024-05-04 15:35:56'),
    (2,'liergou2',100301,100006,'953580004772',101,'随机积分','2024-05-04 07:36:03','create','2024-05-04 15:36:03','2024-05-04 15:36:03'),
    (3,'liergou2',100301,100006,'002033127656',103,'支付优惠券','2024-05-04 07:36:10','create','2024-05-04 15:36:10','2024-05-04 15:36:10'),
    (4,'liergou2',100301,100006,'786106818681',101,'随机积分','2024-05-04 07:36:22','create','2024-05-04 15:36:22','2024-05-04 15:36:22'),
    (5,'liergou2',100301,100006,'903521978453',101,'随机积分','2024-05-04 07:36:33','create','2024-05-04 15:36:32','2024-05-04 15:36:32'),
    (6,'liergou2',100301,100006,'599563157264',104,'小米台灯','2024-05-04 07:36:40','create','2024-05-04 15:36:39','2024-05-04 15:36:39'),
    (7,'liergou2',100301,100006,'236230739530',101,'随机积分','2024-05-04 07:36:47','create','2024-05-04 15:36:46','2024-05-04 15:36:46'),
    (8,'liergou2',100301,100006,'284065292342',101,'随机积分','2024-05-04 07:36:53','create','2024-05-04 15:36:53','2024-05-04 15:36:53'),
    (9,'liergou2',100301,100006,'667428166119',108,'暴走玩偶','2024-05-04 07:37:00','create','2024-05-04 15:36:59','2024-05-04 15:36:59'),
    (10,'liergou2',100301,100006,'320484285041',103,'支付优惠券','2024-05-04 07:37:07','create','2024-05-04 15:37:06','2024-05-04 15:37:06'),
    (11,'liergou2',100301,100006,'048048925549',102,'OpenAI会员卡','2024-05-04 07:37:13','create','2024-05-04 15:37:13','2024-05-04 15:37:13'),
    (12,'liergou2',100301,100006,'536732336372',103,'支付优惠券','2024-05-04 07:37:21','create','2024-05-04 15:37:20','2024-05-04 15:37:20'),
    (13,'liergou2',100301,100006,'378120929272',102,'OpenAI会员卡','2024-05-04 07:37:28','create','2024-05-04 15:37:28','2024-05-04 15:37:28'),
    (14,'liergou2',100301,100006,'368599869327',102,'OpenAI会员卡','2024-05-04 07:37:38','create','2024-05-04 15:37:37','2024-05-04 15:37:37'),
    (15,'user003',100301,100006,'248641902208',101,'随机积分','2024-05-25 02:53:20','completed','2024-05-25 10:53:20','2024-05-25 10:53:20'),
    (16,'user003',100301,100006,'020196190863',101,'随机积分','2024-05-25 02:54:31','completed','2024-05-25 10:54:31','2024-05-25 10:54:31');

/*!40000 ALTER TABLE `user_award_record_002` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_award_record_003
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_award_record_003`;

CREATE TABLE `user_award_record_003` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
                                         `award_id` int(11) NOT NULL COMMENT '奖品ID',
                                         `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
                                         `award_time` datetime NOT NULL COMMENT '中奖时间',
                                         `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id` (`user_id`),
                                         KEY `idx_activity_id` (`activity_id`),
                                         KEY `idx_award_id` (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户中奖记录表';



# 转储表 user_behavior_rebate_order_000
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_behavior_rebate_order_000`;

CREATE TABLE `user_behavior_rebate_order_000` (
                                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                                  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                                  `behavior_type` varchar(16) NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
                                                  `rebate_desc` varchar(128) NOT NULL COMMENT '返利描述',
                                                  `rebate_type` varchar(16) NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
                                                  `rebate_config` varchar(32) NOT NULL COMMENT '返利配置【sku值，积分值】',
                                                  `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
                                                  `biz_id` varchar(128) NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
                                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `uq_order_id` (`order_id`),
                                                  UNIQUE KEY `uq_biz_id` (`biz_id`),
                                                  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为返利流水订单表';



# 转储表 user_behavior_rebate_order_001
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_behavior_rebate_order_001`;

CREATE TABLE `user_behavior_rebate_order_001` (
                                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                                  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                                  `behavior_type` varchar(16) NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
                                                  `rebate_desc` varchar(128) NOT NULL COMMENT '返利描述',
                                                  `rebate_type` varchar(16) NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
                                                  `rebate_config` varchar(32) NOT NULL COMMENT '返利配置【sku值，积分值】',
                                                  `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
                                                  `biz_id` varchar(128) NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
                                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `uq_order_id` (`order_id`),
                                                  UNIQUE KEY `uq_biz_id` (`biz_id`),
                                                  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为返利流水订单表';

LOCK TABLES `user_behavior_rebate_order_001` WRITE;
/*!40000 ALTER TABLE `user_behavior_rebate_order_001` DISABLE KEYS */;

INSERT INTO `user_behavior_rebate_order_001` (`id`, `user_id`, `order_id`, `behavior_type`, `rebate_desc`, `rebate_type`, `rebate_config`, `out_business_no`, `biz_id`, `create_time`, `update_time`)
VALUES
    (5,'xiaofuge','630841674684','sign','签到返利-sku额度','sku','9011','20240503','xiaofuge_sku_20240503','2024-05-03 13:28:42','2024-05-03 13:28:42'),
    (6,'xiaofuge','552413408368','sign','签到返利-积分','integral','10','20240503','xiaofuge_integral_20240503','2024-05-03 13:28:42','2024-05-03 13:28:42'),
    (33,'liergou','658660043956','sign','签到返利-sku额度','sku','9011','20240504','liergou_sku_20240504','2024-05-04 15:32:25','2024-05-04 15:32:25'),
    (34,'liergou','659440313972','sign','签到返利-积分','integral','10','20240504','liergou_integral_20240504','2024-05-04 15:32:25','2024-05-04 15:32:25'),
    (35,'xiaofuge','577047817885','sign','签到返利-积分','integral','10','20240601001','xiaofuge_integral_20240601001','2024-06-01 10:49:00','2024-06-01 10:49:00'),
    (37,'xiaofuge','060366280132','sign','签到返利-积分','integral','10','20240601002','xiaofuge_integral_20240601002','2024-06-01 10:51:58','2024-06-01 10:51:58'),
    (38,'xiaofuge','735771087688','sign','签到返利-积分','integral','10','20240601003','xiaofuge_integral_20240601003','2024-06-01 10:52:52','2024-06-01 10:52:52'),
    (39,'xiaofuge','890916856671','sign','签到返利-积分','integral','10','20240601004','xiaofuge_integral_20240601004','2024-06-01 10:54:30','2024-06-01 10:54:30'),
    (40,'xiaofuge','881155696490','sign','签到返利-积分','integral','10','20240601005','xiaofuge_integral_20240601005','2024-06-01 10:57:09','2024-06-01 10:57:09'),
    (41,'xiaofuge','213871507610','sign','签到返利-积分','integral','10','20240601006','xiaofuge_integral_20240601006','2024-06-01 11:00:27','2024-06-01 11:00:27'),
    (43,'xiaofuge','031074415283','sign','签到返利-积分','integral','10','20240601101','xiaofuge_integral_20240601101','2024-06-01 14:02:47','2024-06-01 14:02:47');

/*!40000 ALTER TABLE `user_behavior_rebate_order_001` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_behavior_rebate_order_002
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_behavior_rebate_order_002`;

CREATE TABLE `user_behavior_rebate_order_002` (
                                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                                  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                                  `behavior_type` varchar(16) NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
                                                  `rebate_desc` varchar(128) NOT NULL COMMENT '返利描述',
                                                  `rebate_type` varchar(16) NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
                                                  `rebate_config` varchar(32) NOT NULL COMMENT '返利配置【sku值，积分值】',
                                                  `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
                                                  `biz_id` varchar(128) NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
                                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `uq_order_id` (`order_id`),
                                                  UNIQUE KEY `uq_biz_id` (`biz_id`),
                                                  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为返利流水订单表';

LOCK TABLES `user_behavior_rebate_order_002` WRITE;
/*!40000 ALTER TABLE `user_behavior_rebate_order_002` DISABLE KEYS */;

INSERT INTO `user_behavior_rebate_order_002` (`id`, `user_id`, `order_id`, `behavior_type`, `rebate_desc`, `rebate_type`, `rebate_config`, `out_business_no`, `biz_id`, `create_time`, `update_time`)
VALUES
    (5,'liergou2','274252392446','sign','签到返利-sku额度','sku','9011','20240504','liergou2_sku_20240504','2024-05-04 15:35:52','2024-05-04 15:35:52'),
    (6,'liergou2','687741770429','sign','签到返利-积分','integral','10','20240504','liergou2_integral_20240504','2024-05-04 15:35:52','2024-05-04 15:35:52'),
    (7,'user003','317965139211','sign','签到返利-sku额度','sku','9011','20240525','user003_sku_20240525','2024-05-25 10:52:18','2024-05-25 10:52:18'),
    (8,'user003','429627541291','sign','签到返利-积分','integral','10','20240525','user003_integral_20240525','2024-05-25 10:52:19','2024-05-25 10:52:19');

/*!40000 ALTER TABLE `user_behavior_rebate_order_002` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_behavior_rebate_order_003
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_behavior_rebate_order_003`;

CREATE TABLE `user_behavior_rebate_order_003` (
                                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                                  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                                  `behavior_type` varchar(16) NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
                                                  `rebate_desc` varchar(128) NOT NULL COMMENT '返利描述',
                                                  `rebate_type` varchar(16) NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
                                                  `rebate_config` varchar(32) NOT NULL COMMENT '返利配置【sku值，积分值】',
                                                  `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
                                                  `biz_id` varchar(128) NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
                                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE KEY `uq_order_id` (`order_id`),
                                                  UNIQUE KEY `uq_biz_id` (`biz_id`),
                                                  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为返利流水订单表';



# 转储表 user_credit_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_credit_account`;

CREATE TABLE `user_credit_account` (
                                       `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                       `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                       `total_amount` decimal(10,2) NOT NULL COMMENT '总积分，显示总账户值，记得一个人获得的总积分',
                                       `available_amount` decimal(10,2) NOT NULL COMMENT '可用积分，每次扣减的值',
                                       `account_status` varchar(8) NOT NULL COMMENT '账户状态【open - 可用，close - 冻结】',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分账户';

LOCK TABLES `user_credit_account` WRITE;
/*!40000 ALTER TABLE `user_credit_account` DISABLE KEYS */;

INSERT INTO `user_credit_account` (`id`, `user_id`, `total_amount`, `available_amount`, `account_status`, `create_time`, `update_time`)
VALUES
    (1,'xiaofuge',85.85,85.85,'open','2024-05-24 22:11:59','2024-06-09 10:45:11'),
    (2,'user003',0.96,0.96,'open','2024-05-25 10:53:20','2024-05-25 10:54:31');

/*!40000 ALTER TABLE `user_credit_account` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_credit_order_000
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_credit_order_000`;

CREATE TABLE `user_credit_order_000` (
                                         `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `trade_name` varchar(32) NOT NULL COMMENT '交易名称',
                                         `trade_type` varchar(8) NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
                                         `trade_amount` decimal(10,2) NOT NULL COMMENT '交易金额',
                                         `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                         KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分订单记录';



# 转储表 user_credit_order_001
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_credit_order_001`;

CREATE TABLE `user_credit_order_001` (
                                         `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `trade_name` varchar(32) NOT NULL COMMENT '交易名称',
                                         `trade_type` varchar(8) NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
                                         `trade_amount` decimal(10,2) NOT NULL COMMENT '交易金额',
                                         `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                         KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分订单记录';

LOCK TABLES `user_credit_order_001` WRITE;
/*!40000 ALTER TABLE `user_credit_order_001` DISABLE KEYS */;

INSERT INTO `user_credit_order_001` (`id`, `user_id`, `order_id`, `trade_name`, `trade_type`, `trade_amount`, `out_business_no`, `create_time`, `update_time`)
VALUES
    (1,'xiaofuge','950333991038','行为返利','forward',10.19,'10000990990','2024-06-01 10:31:16','2024-06-01 10:31:16'),
    (4,'xiaofuge','957646101468','行为返利','forward',-10.19,'10000990991','2024-06-01 10:33:26','2024-06-01 10:33:26'),
    (5,'xiaofuge','105601831431','行为返利','forward',10.00,'xiaofuge_integral_20240601006','2024-06-01 11:00:45','2024-06-01 11:00:45'),
    (6,'xiaofuge','120781019441','行为返利','forward',10.19,'100009900001','2024-06-01 13:57:22','2024-06-01 13:57:22'),
    (7,'xiaofuge','626373070354','行为返利','reverse',-10.19,'100009900002','2024-06-01 13:58:23','2024-06-01 13:58:23'),
    (8,'xiaofuge','726664203611','行为返利','forward',10.00,'xiaofuge_integral_20240601101','2024-06-01 14:02:48','2024-06-01 14:02:48'),
    (9,'xiaofuge','337035866234','行为返利','forward',10.19,'100009909911','2024-06-01 14:27:20','2024-06-01 14:27:20'),
    (11,'xiaofuge','904262714981','行为返利','forward',10.19,'12406039900002','2024-06-03 07:28:02','2024-06-03 07:28:02'),
    (12,'xiaofuge','313070417337','兑换抽奖','reverse',-1.68,'70009240608003','2024-06-08 20:27:03','2024-06-08 20:27:03'),
    (15,'xiaofuge','956437348272','兑换抽奖','reverse',-1.68,'70009240608007','2024-06-08 20:34:13','2024-06-08 20:34:13'),
    (16,'xiaofuge','825697847616','兑换抽奖','reverse',-1.68,'70009240609001','2024-06-09 09:11:23','2024-06-09 09:11:23'),
    (17,'xiaofuge','528225981137','兑换抽奖','reverse',-1.68,'70009240610002','2024-06-09 10:45:11','2024-06-09 10:45:11');

/*!40000 ALTER TABLE `user_credit_order_001` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_credit_order_002
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_credit_order_002`;

CREATE TABLE `user_credit_order_002` (
                                         `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `trade_name` varchar(32) NOT NULL COMMENT '交易名称',
                                         `trade_type` varchar(8) NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
                                         `trade_amount` decimal(10,2) NOT NULL COMMENT '交易金额',
                                         `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                         KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分订单记录';



# 转储表 user_credit_order_003
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_credit_order_003`;

CREATE TABLE `user_credit_order_003` (
                                         `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `trade_name` varchar(32) NOT NULL COMMENT '交易名称',
                                         `trade_type` varchar(8) NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
                                         `trade_amount` decimal(10,2) NOT NULL COMMENT '交易金额',
                                         `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                         KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分订单记录';



# 转储表 user_raffle_order_000
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_raffle_order_000`;

CREATE TABLE `user_raffle_order_000` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `order_time` datetime NOT NULL COMMENT '下单时间',
                                         `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户抽奖订单表';



# 转储表 user_raffle_order_001
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_raffle_order_001`;

CREATE TABLE `user_raffle_order_001` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `order_time` datetime NOT NULL COMMENT '下单时间',
                                         `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户抽奖订单表';

LOCK TABLES `user_raffle_order_001` WRITE;
/*!40000 ALTER TABLE `user_raffle_order_001` DISABLE KEYS */;

INSERT INTO `user_raffle_order_001` (`id`, `user_id`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `order_state`, `create_time`, `update_time`)
VALUES

    (94,'xiaofuge',100301,'测试活动',100006,'714442509117','2024-05-04 02:38:49','used','2024-05-04 10:38:49','2024-05-04 10:38:49'),
    (95,'xiaofuge',100301,'测试活动',100006,'124268508437','2024-05-04 03:30:47','used','2024-05-04 11:30:47','2024-05-04 11:30:47'),
    (96,'xiaofuge',100301,'测试活动',100006,'024028065395','2024-05-04 03:40:10','used','2024-05-04 11:40:09','2024-05-04 11:40:09'),
    (97,'xiaofuge',100301,'测试活动',100006,'011132554981','2024-05-04 03:40:17','used','2024-05-04 11:40:16','2024-05-04 11:40:17'),
    (98,'xiaofuge',100301,'测试活动',100006,'748409799526','2024-05-04 04:49:12','used','2024-05-04 12:49:11','2024-05-04 12:49:20'),
    (99,'xiaofuge',100301,'测试活动',100006,'514483431161','2024-05-04 04:49:30','used','2024-05-04 12:49:30','2024-05-04 12:49:30'),
    (100,'xiaofuge',100301,'测试活动',100006,'401352928023','2024-05-04 04:49:42','used','2024-05-04 12:49:42','2024-05-04 12:49:42'),
    (101,'xiaofuge',100301,'测试活动',100006,'569764837195','2024-05-04 04:50:36','used','2024-05-04 12:50:35','2024-05-04 12:50:35'),
    (127,'xiaofuge',100301,'测试活动',100006,'101866910505','2024-05-30 00:02:18','create','2024-05-30 08:02:18','2024-05-30 08:02:18');

/*!40000 ALTER TABLE `user_raffle_order_001` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_raffle_order_002
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_raffle_order_002`;

CREATE TABLE `user_raffle_order_002` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `order_time` datetime NOT NULL COMMENT '下单时间',
                                         `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户抽奖订单表';

LOCK TABLES `user_raffle_order_002` WRITE;
/*!40000 ALTER TABLE `user_raffle_order_002` DISABLE KEYS */;

INSERT INTO `user_raffle_order_002` (`id`, `user_id`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `order_state`, `create_time`, `update_time`)
VALUES
    (1,'liergou2',100301,'测试活动',100006,'319771078666','2024-05-04 07:35:56','used','2024-05-04 15:35:56','2024-05-04 15:35:56'),
    (2,'liergou2',100301,'测试活动',100006,'953580004772','2024-05-04 07:36:03','used','2024-05-04 15:36:03','2024-05-04 15:36:03'),
    (3,'liergou2',100301,'测试活动',100006,'002033127656','2024-05-04 07:36:10','used','2024-05-04 15:36:10','2024-05-04 15:36:10'),
    (4,'liergou2',100301,'测试活动',100006,'786106818681','2024-05-04 07:36:22','used','2024-05-04 15:36:21','2024-05-04 15:36:22'),
    (5,'liergou2',100301,'测试活动',100006,'903521978453','2024-05-04 07:36:33','used','2024-05-04 15:36:32','2024-05-04 15:36:32'),
    (6,'liergou2',100301,'测试活动',100006,'599563157264','2024-05-04 07:36:40','used','2024-05-04 15:36:39','2024-05-04 15:36:39'),
    (7,'liergou2',100301,'测试活动',100006,'236230739530','2024-05-04 07:36:47','used','2024-05-04 15:36:46','2024-05-04 15:36:46'),
    (8,'liergou2',100301,'测试活动',100006,'284065292342','2024-05-04 07:36:53','used','2024-05-04 15:36:53','2024-05-04 15:36:53'),
    (9,'liergou2',100301,'测试活动',100006,'667428166119','2024-05-04 07:37:00','used','2024-05-04 15:36:59','2024-05-04 15:36:59'),
    (10,'liergou2',100301,'测试活动',100006,'320484285041','2024-05-04 07:37:06','used','2024-05-04 15:37:06','2024-05-04 15:37:06'),
    (11,'liergou2',100301,'测试活动',100006,'048048925549','2024-05-04 07:37:13','used','2024-05-04 15:37:13','2024-05-04 15:37:13');
/*!40000 ALTER TABLE `user_raffle_order_002` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 user_raffle_order_003
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_raffle_order_003`;

CREATE TABLE `user_raffle_order_003` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                         `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                         `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                         `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                         `strategy_id` bigint(8) NOT NULL COMMENT '抽奖策略ID',
                                         `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                         `order_time` datetime NOT NULL COMMENT '下单时间',
                                         `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uq_order_id` (`order_id`),
                                         KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户抽奖订单表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
