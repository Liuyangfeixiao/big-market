# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 5.6.39)
# 数据库: big_market_02
# 生成时间: 2024-04-30 10:18:59 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE database if NOT EXISTS `big_market_02` default character set utf8mb4;
use `big_market_02`;

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
                                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `uq_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动账户表';

LOCK TABLES `raffle_activity_account` WRITE;
/*!40000 ALTER TABLE `raffle_activity_account` DISABLE KEYS */;

INSERT INTO `raffle_activity_account` (`id`, `user_id`, `activity_id`, `total_count`, `total_count_surplus`, `day_count`, `day_count_surplus`, `month_count`, `month_count_surplus`, `create_time`, `update_time`)
VALUES
    (2,'xiaofuge',100301,4,3,4,3,4,3,'2024-03-23 12:40:56','2024-03-23 13:16:40');

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

INSERT INTO `raffle_activity_order_001` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `day_count`, `month_count`, `state`, `out_business_no`, `create_time`, `update_time`)
VALUES
    (3,'xiaofuge',9011,100301,'测试活动',100006,'383240888158','2024-03-23 04:38:23',1,1,1,'completed','700091009111','2024-03-23 12:38:23','2024-03-23 12:38:23');

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
                                             `state` varchar(16) NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
                                             `out_business_no` varchar(64) NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `uq_order_id` (`order_id`),
                                             UNIQUE KEY `uq_out_business_no` (`out_business_no`),
                                             KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抽奖活动单';



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

LOCK TABLES `user_behavior_rebate_order_000` WRITE;
/*!40000 ALTER TABLE `user_behavior_rebate_order_000` DISABLE KEYS */;

INSERT INTO `user_behavior_rebate_order_000` (`id`, `user_id`, `order_id`, `behavior_type`, `rebate_desc`, `rebate_type`, `rebate_config`, `out_business_no`, `biz_id`, `create_time`, `update_time`)
VALUES
    (5,'xiaofuge1','883971522401','sign','签到返利-sku额度','sku','9011','20240503','xiaofuge1_sku_20240503','2024-05-03 16:01:42','2024-05-03 16:01:42'),
    (6,'xiaofuge1','995944930386','sign','签到返利-积分','integral','10','20240503','xiaofuge1_integral_20240503','2024-05-03 16:01:43','2024-05-03 16:01:43'),
    (7,'user001','153309768984','sign','签到返利-sku额度','sku','9011','20240524','user001_sku_20240524','2024-05-24 22:25:09','2024-05-24 22:25:09'),
    (8,'user001','922397055482','sign','签到返利-积分','integral','10','20240524','user001_integral_20240524','2024-05-24 22:25:09','2024-05-24 22:25:09'),
    (9,'xiaofuge25','049961446285','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge25_sku_20240622','2024-06-22 10:07:40','2024-06-22 10:07:40'),
    (10,'xiaofuge25','311903567924','sign','签到返利-积分','integral','10','20240622','xiaofuge25_integral_20240622','2024-06-22 10:07:40','2024-06-22 10:07:40');

/*!40000 ALTER TABLE `user_behavior_rebate_order_000` ENABLE KEYS */;
UNLOCK TABLES;


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
    (1,'user002','791847185524','sign','签到返利-sku额度','sku','9011','20240524','user002_sku_20240524','2024-05-24 22:29:11','2024-05-24 22:29:11'),
    (2,'user002','119018837248','sign','签到返利-积分','integral','10','20240524','user002_integral_20240524','2024-05-24 22:29:11','2024-05-24 22:29:11'),
    (3,'user002','031618502370','sign','签到返利-sku额度','sku','9011','20240527','user002_sku_20240527','2024-05-27 22:37:06','2024-05-27 22:37:06'),
    (4,'user002','591157608900','sign','签到返利-积分','integral','10','20240527','user002_integral_20240527','2024-05-27 22:37:07','2024-05-27 22:37:07'),
    (5,'xiaofuge24','532714815440','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge24_sku_20240622','2024-06-22 09:59:19','2024-06-22 09:59:19'),
    (6,'xiaofuge24','886487876823','sign','签到返利-积分','integral','10','20240622','xiaofuge24_integral_20240622','2024-06-22 09:59:19','2024-06-22 09:59:19'),
    (7,'xiaofuge100','114867898811','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge100_sku_20240622','2024-06-22 11:30:37','2024-06-22 11:30:37'),
    (8,'xiaofuge100','846316816808','sign','签到返利-积分','integral','10','20240622','xiaofuge100_integral_20240622','2024-06-22 11:30:37','2024-06-22 11:30:37');

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
    (1,'xiaofuge23','674540833668','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge23_sku_20240622','2024-06-22 09:57:00','2024-06-22 09:57:00'),
    (2,'xiaofuge23','682003973896','sign','签到返利-积分','integral','10','20240622','xiaofuge23_integral_20240622','2024-06-22 09:57:00','2024-06-22 09:57:00'),
    (3,'xiaofuge101','016416761475','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge101_sku_20240622','2024-06-22 11:44:50','2024-06-22 11:44:50'),
    (4,'xiaofuge101','836048823314','sign','签到返利-积分','integral','10','20240622','xiaofuge101_integral_20240622','2024-06-22 11:44:50','2024-06-22 11:44:50');

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

LOCK TABLES `user_behavior_rebate_order_003` WRITE;
/*!40000 ALTER TABLE `user_behavior_rebate_order_003` DISABLE KEYS */;

INSERT INTO `user_behavior_rebate_order_003` (`id`, `user_id`, `order_id`, `behavior_type`, `rebate_desc`, `rebate_type`, `rebate_config`, `out_business_no`, `biz_id`, `create_time`, `update_time`)
VALUES
    (7,'xiaofuge2','298513989210','sign','签到返利-sku额度','sku','9011','20240504','xiaofuge2_sku_20240504','2024-05-04 13:07:53','2024-05-04 13:07:53'),
    (8,'xiaofuge2','352651244433','sign','签到返利-积分','integral','10','20240504','xiaofuge2_integral_20240504','2024-05-04 13:07:53','2024-05-04 13:07:53'),
    (9,'xiaofuge22','769157142538','sign','签到返利-sku额度','sku','9014','20240622','xiaofuge22_sku_20240622','2024-06-22 09:52:58','2024-06-22 09:52:58'),
    (10,'xiaofuge22','718932497750','sign','签到返利-积分','integral','10','20240622','xiaofuge22_integral_20240622','2024-06-22 09:52:58','2024-06-22 09:52:58');

/*!40000 ALTER TABLE `user_behavior_rebate_order_003` ENABLE KEYS */;
UNLOCK TABLES;


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
