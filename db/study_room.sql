-- study_room_full.sql
-- Complete schema + data. Modified tables (qiandaoxinxi, yuyuexinxi, qiantuixinxi, quxiaoyuyue)
-- are recreated with updated structure and sample data that follow your constraints.
-- CAUTION: Running this will DROP existing tables with the same names.

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE;
SET SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

-- Use or create the database as appropriate, uncomment if needed:
-- CREATE DATABASE IF NOT EXISTS study_room DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- USE study_room;

-- ===================================================================
-- Keep most original tables and data from your dump - unchanged
-- ===================================================================

/*!40101 SET @saved_cs_client     = @@character_set_client */;

-- Table: config
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(100) NOT NULL COMMENT '配置参数名称',
                          `value` varchar(100) DEFAULT NULL COMMENT '配置参数值',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='配置文件';

LOCK TABLES `config` WRITE;
INSERT INTO `config` VALUES
                         (1,'picture1','upload/picture1.jpg'),
                         (2,'picture2','upload/picture2.jpg'),
                         (3,'picture3','upload/picture3.jpg'),
                         (7,'APIKey','ihML0NfEM0jokUAuBD9tbYtW'),
                         (8,'SecretKey','WK2bRUFf2uNe3oEoQXULpi8Y37aCvFdD');
UNLOCK TABLES;

-- Table: news
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `title` varchar(200) NOT NULL COMMENT '标题',
                        `introduction` longtext COMMENT '简介',
                        `picture` longtext NOT NULL COMMENT '图片',
                        `content` longtext NOT NULL COMMENT '内容',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb3 COMMENT='公告资讯';

LOCK TABLES `news` WRITE;
INSERT INTO `news` VALUES
                       (81,'2025-10-31 08:35:15','智能自习室使用守则','为营造良好学习环境，请遵守使用守则。','upload/news_picture1.jpg','<p>智能自习室使用守则</p>
<p>开放时间：7:00-23:00</p>
<p>预约规则：提前24小时预约，每日最多3次</p>
<p>签到要求：预约后15分钟内完成签到</p>
<p>行为规范：保持安静，爱护设施</p>
<p>违规处理：未签到3次暂停权限7天</p>'),
                       (82,'2025-10-31 09:15:20','自习室违规通报','近期发现违规情况，特此通报。','upload/news_picture2.jpg','<p>自习室违规通报</p>
<p>10月26日：236001234长时间离席</p>
<p>10月28日：236001235未签到</p>
<p>10月29日：236001236携带食物</p>
<p>处理措施：扣除信用积分</p>
<p>严重者暂停使用权限</p>'),
                       (83,'2025-10-30 14:20:10','设备使用规范','请按规范使用设备。','upload/news_picture3.jpg','<p>设备使用规范</p>
<p>电源：使用正规充电设备</p>
<p>桌椅：禁止刻划桌面</p>
<p>空调：夏季26℃，冬季22℃</p>
<p>报告：发现损坏及时报告</p>'),
                       (84,'2025-10-29 16:45:30','系统调整通知','预约系统11月1日调整。','upload/news_picture4.jpg','<p>系统调整通知</p>
<p>新增功能：智能推荐、同伴学习</p>
<p>规则调整：预约时段1.5小时</p>
<p>临时离开功能：最长30分钟</p>
<p>维护时间：11月1日0:00-6:00</p>'),
                       (85,'2025-10-28 10:30:15','自习室公告','关于自习室使用的重要通知。','upload/news_picture5.jpg','<p>自习室公告</p>
<p>请按预约时间准时到达</p>
<p>离开时带走个人物品</p>
<p>保持桌面整洁</p>
<p>遵守自习室管理规定</p>
<p>共同维护学习环境</p>'),
                       (86,'2025-10-27 15:20:45','安全注意事项','自习室使用安全须知。','upload/news_picture6.jpg','<p>安全注意事项</p>
<p>用电安全：规范使用插座</p>
<p>物品保管：贵重物品随身带</p>
<p>应急处理：熟悉安全出口</p>
<p>紧急情况：按报警器求助</p>'),
                       (87,'2025-10-26 11:10:30','考试周安排','考试周期间特别安排。','upload/news_picture7.jpg','<p>考试周安排</p>
<p>时间：12月20日-1月10日</p>
<p>延长开放至24小时</p>
<p>增设通宵自习区</p>
<p>座位增加至1200个</p>
<p>注意劳逸结合</p>'),
                       (88,'2025-10-25 13:40:20','意见征集','征集自习室改进意见。','upload/news_picture8.jpg','<p>意见征集</p>
<p>征集内容：系统体验、设施改进</p>
<p>参与方式：系统反馈、意见箱</p>
<p>征集时间：至11月15日</p>
<p>欢迎提出宝贵意见</p>');
UNLOCK TABLES;

-- ---------------------------------------------------------------------
-- Modified tables: qiandaoxinxi, qiantuixinxi, quxiaoyuyue, yuyuexinxi
-- We recreate them here with updated schemas and with generated sample data
-- ---------------------------------------------------------------------

-- Table: qiandaoxinxi (updated)
DROP TABLE IF EXISTS `qiandaoxinxi`;
CREATE TABLE `qiandaoxinxi` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                                `qiandaoshijian` datetime DEFAULT NULL COMMENT '签到时间',
                                `qiandaobeizhu` varchar(200) DEFAULT NULL COMMENT '签到备注',
                                `xuehao` varchar(200) DEFAULT NULL COMMENT '学号',
                                `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
                                `banji` varchar(200) DEFAULT NULL COMMENT '班级',
                                `shhf` longtext COMMENT '回复内容',
                                `yuyuedanhao` varchar(50) DEFAULT NULL COMMENT '预约单号',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1740281000000 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='签到信息';

LOCK TABLES `qiandaoxinxi` WRITE;
-- Sample sign-in records: link to some yuyue records below via yuyuedanhao
INSERT INTO `qiandaoxinxi` (`id`,`addtime`,`mingcheng`,`qiandaoshijian`,`qiandaobeizhu`,`xuehao`,`shouji`,`banji`,`shhf`,`yuyuedanhao`) VALUES
                                                                                                                                            (1740280606000,'2025-10-12 07:16:46','大自习室','2025-10-12 08:00:00','','236002052','18345623578','23计科2班','','YUYUE0001'),
                                                                                                                                            (1740280707000,'2025-10-12 07:18:27','大自习室','2025-10-12 08:30:00','','236004998','13077467681','23计科1班','','YUYUE0002'),
                                                                                                                                            (1740634112000,'2025-10-16 09:28:32','大自习室','2025-10-16 09:30:00','','236002052','18345623578','23计科2班','','YUYUE0003'),
                                                                                                                                            (1740634115000,'2025-10-16 09:28:35','大自习室','2025-10-16 10:00:00','','236003773','18267834563','23计科2班','','YUYUE0004'),
                                                                                                                                            (1740697218000,'2025-10-17 08:00:18','大自习室','2025-10-17 08:00:00','','236001205','16735464892','23计科2班','','YUYUE0005'),
                                                                                                                                            (1741397151000,'2025-10-24 10:25:51','图书馆','2025-10-24 10:30:00','','236004998','13077467681','23计科1班','','YUYUE0006');
UNLOCK TABLES;

-- Table: qiantuixinxi (updated)
DROP TABLE IF EXISTS `qiantuixinxi`;
CREATE TABLE `qiantuixinxi` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                                `qiantuishijian` datetime DEFAULT NULL COMMENT '签退时间',
                                `zixishiid` int DEFAULT NULL COMMENT '自习室序号',
                                `zuowei` int DEFAULT NULL COMMENT '座位',
                                `qiantuibeizhu` varchar(200) DEFAULT NULL COMMENT '签退备注',
                                `xuehao` varchar(200) DEFAULT NULL COMMENT '学号',
                                `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
                                `banji` varchar(200) DEFAULT NULL COMMENT '班级',
                                `shhf` longtext COMMENT '回复内容',
                                `zixishichang` double DEFAULT NULL COMMENT '自习时长(分钟)',
                                `yuyuedanhao` varchar(50) DEFAULT NULL COMMENT '预约单号',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1740281000000 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='签退信息';

LOCK TABLES `qiantuixinxi` WRITE;
-- sample sign-outs corresponding to some sign-ins
INSERT INTO `qiantuixinxi` (`id`,`addtime`,`mingcheng`,`qiantuishijian`,`zixishiid`,`zuowei`,`qiantuibeizhu`,`xuehao`,`shouji`,`banji`,`shhf`,`zixishichang`,`yuyuedanhao`) VALUES
                                                                                                                                                                                (1740280606000,'2025-10-12 07:29:27','大自习室','2025-10-12 10:00:00',21,22,'正常签退','236002052','18345623578','23计科2班','',120.0,'YUYUE0001'),
                                                                                                                                                                                (1740280707000,'2025-10-12 07:28:12','大自习室','2025-10-12 09:00:00',21,1,'正常签退','236004998','13077467681','23计科1班','',30.0,'YUYUE0002'),
                                                                                                                                                                                (1740696212000,'2025-10-17 05:25:46','大自习室','2025-10-17 13:30:00',21,21,'签退','236001205','16735464892','23计科2班','',330.0,'YUYUE0005');
UNLOCK TABLES;

-- Table: quxiaoyuyue (updated)
DROP TABLE IF EXISTS `quxiaoyuyue`;
CREATE TABLE `quxiaoyuyue` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `yuyuedanhao` varchar(200) DEFAULT NULL COMMENT '预约单号',
                               `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                               `yuyue_start` datetime DEFAULT NULL COMMENT '预约开始时间',
                               `yuyue_end` datetime DEFAULT NULL COMMENT '预约结束时间',
                               `yuyueshijian` varchar(200) DEFAULT NULL COMMENT '预约时间(兼容旧字段)',
                               `quxiaoshijian` datetime DEFAULT NULL COMMENT '取消时间',
                               `quxiaoyuanyin` longtext DEFAULT NULL COMMENT '取消原因',
                               `zixishiid` int DEFAULT NULL COMMENT '自习室序号',
                               `zuowei` varchar(255) DEFAULT NULL COMMENT '座位',
                               `xuehao` varchar(200) DEFAULT NULL COMMENT '学号',
                               `xingming` varchar(200) DEFAULT NULL COMMENT '姓名',
                               `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
                               `crossuserid` bigint DEFAULT NULL COMMENT '跨表用户id',
                               `crossrefid` bigint DEFAULT NULL COMMENT '跨表主键id',
                               `shhf` longtext COMMENT '审核回复',
                               `userid` bigint DEFAULT NULL COMMENT '用户id',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1740281000000 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='取消预约';

LOCK TABLES `quxiaoyuyue` WRITE;
-- sample cancelled reservation
INSERT INTO `quxiaoyuyue` (`id`,`addtime`,`yuyuedanhao`,`mingcheng`,`yuyue_start`,`yuyue_end`,`quxiaoshijian`,`quxiaoyuanyin`,`zixishiid`,`zuowei`,`xuehao`,`xingming`,`shouji`,`crossuserid`,`crossrefid`,`shhf`,`userid`) VALUES
    (1740280595000,'2025-10-12 08:26:35','YUYUE_CANCEL_0001','大自习室','2025-10-12 14:00:00','2025-10-12 15:30:00','2025-10-12 14:55:00','临时有事','21','1','236002052','zhengyilin','18345623578',16,1740280869000,'','17');
UNLOCK TABLES;

-- Table: yuyuexinxi (updated)
DROP TABLE IF EXISTS `yuyuexinxi`;
CREATE TABLE `yuyuexinxi` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `zixishiid` int DEFAULT NULL COMMENT '自习室编号',
                              `yuyuedanhao` varchar(200) DEFAULT NULL COMMENT '预约单号',
                              `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                              `tupian` longtext COMMENT '图片',
                              `zuowei` int DEFAULT NULL COMMENT '座位',
                              `qiandaozhuangtai` varchar(200) DEFAULT NULL COMMENT '签到状态',
                              `qiantuizhuangtai` varchar(200) DEFAULT NULL COMMENT '签退状态',
                              `beizhu` varchar(200) DEFAULT NULL COMMENT '备注',
                              `xuehao` varchar(200) DEFAULT NULL COMMENT '学号',
                              `xingming` varchar(200) DEFAULT NULL COMMENT '姓名',
                              `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
                              `shhf` longtext COMMENT '回复',
                              `yuyue_start` datetime DEFAULT NULL COMMENT '预约开始时间',
                              `yuyue_end` datetime DEFAULT NULL COMMENT '预约结束时间',
                              `weigui_flag` tinyint(1) DEFAULT 0 COMMENT '违纪标记：0=正常,1=违纪',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `yuyuedanhao_unique` (`yuyuedanhao`)
) ENGINE=InnoDB AUTO_INCREMENT=1745493805000 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='预约信息';

LOCK TABLES `yuyuexinxi` WRITE;
-- Sample yuyue records that follow the rule (08:00-22:00, minutes 00 or 30, seconds 00)
INSERT INTO `yuyuexinxi` (`id`,`addtime`,`zixishiid`,`yuyuedanhao`,`mingcheng`,`tupian`,`zuowei`,`qiandaozhuangtai`,`qiantuizhuangtai`,`beizhu`,`xuehao`,`xingming`,`shouji`,`shhf`,`yuyue_start`,`yuyue_end`,`weigui_flag`) VALUES
                                                                                                                                                                                                                                 (1745493800001,'2025-12-01 09:00:00',21,'YUYUE0001','图书馆','upload/zixishi_tupian2.jpg',10,'已签到','已签退','无备注','236002052','zhengyilin','18345623578','','2025-12-12 08:00:00','2025-12-12 10:00:00',0),
                                                                                                                                                                                                                                 (1745493800002,'2025-12-02 09:00:00',22,'YUYUE0002','大自习室','upload/zixishi_tupian1.jpg',1,'未签到','未签退','无备注','236004998','ayakuozi','13077467681','','2025-12-13 09:30:00','2025-12-13 11:00:00',0),
                                                                                                                                                                                                                                 (1745493800003,'2025-12-03 09:00:00',21,'YUYUE0003','大自习室','upload/zixishi_tupian1.jpg',3,'已签到','未签退','无备注','236003773','zhangyuling','18267834563','','2025-12-14 20:00:00','2025-12-14 22:00:00',0),
                                                                                                                                                                                                                                 (1745493800004,'2025-12-04 09:00:00',25,'YUYUE0004','北区2号楼','upload/zixishi_tupian5.jpg',30,'未签到','未签退','超时未签到','236005000','student1','13500000001','','2025-12-10 13:00:00','2025-12-10 14:30:00',1),
                                                                                                                                                                                                                                 (1745493800005,'2025-12-05 09:00:00',22,'YUYUE0005','图书馆','upload/zixishi_tupian2.jpg',6,'已签到','未签退','正在使用','236004998','ayakuozi','13077467681','','2025-12-15 08:30:00','2025-12-15 10:00:00',0),
                                                                                                                                                                                                                                 (1745493800006,'2025-12-06 09:00:00',21,'YUYUE0006','大自习室','upload/zixishi_tupian1.jpg',24,'未签到','未签退','无备注','236001205','xushiyu','16735464892','','2025-12-16 13:30:00','2025-12-16 15:00:00',0),
                                                                                                                                                                                                                                 (1745493800007,'2025-12-07 09:00:00',24,'YUYUE0007','多媒体教室','upload/zixishi_tupian4.jpg',15,'已签到','已签退','正常','236001205','xushiyu','16735464892','','2025-12-11 08:00:00','2025-12-11 09:30:00',0),
                                                                                                                                                                                                                                 (1745493800008,'2025-12-08 09:00:00',25,'YUYUE0008','北区2号楼','upload/zixishi_tupian5.jpg',45,'未签到','未签退','无备注','236002928','huruiyu','17354826452','','2025-12-18 14:00:00','2025-12-18 15:30:00',0);
UNLOCK TABLES;

-- ---------------------------------------------------------------------
-- Remaining original tables and data (seats_21 ... seats_28 etc) retained
-- as in your original dump
-- ---------------------------------------------------------------------

-- seats_21
DROP TABLE IF EXISTS `seats_21`;
CREATE TABLE `seats_21` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_21` WRITE;
INSERT INTO `seats_21` VALUES
                           (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,1),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(92,1),(93,1),(94,1),(95,1),(96,1),(97,1),(98,1),(99,1),(100,1);
UNLOCK TABLES;

-- seats_22
DROP TABLE IF EXISTS `seats_22`;
CREATE TABLE `seats_22` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_22` WRITE;
INSERT INTO `seats_22` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,0),(8,0),(9,0),(10,1),(11,1),(12,1),(13,1),(14,1),(15,0),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,0),(46,1),(47,1),(48,1),(49,1),(50,0),(51,1),(52,1),(53,1),(54,1),(55,1),(56,0),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,0),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(92,1),(93,1),(94,1),(95,1),(96,1),(97,1),(98,1),(99,1),(100,1);
UNLOCK TABLES;

-- seats_23
DROP TABLE IF EXISTS `seats_23`;
CREATE TABLE `seats_23` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_23` WRITE;
INSERT INTO `seats_23` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,0),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1);
UNLOCK TABLES;

-- seats_24
DROP TABLE IF EXISTS `seats_24`;
CREATE TABLE `seats_24` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_24` WRITE;
INSERT INTO `seats_24` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1);
UNLOCK TABLES;

-- seats_25
DROP TABLE IF EXISTS `seats_25`;
CREATE TABLE `seats_25` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_25` WRITE;
INSERT INTO `seats_25` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,1),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1);
UNLOCK TABLES;

-- seats_26
DROP TABLE IF EXISTS `seats_26`;
CREATE TABLE `seats_26` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_26` WRITE;
INSERT INTO `seats_26` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,1),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1);
UNLOCK TABLES;

-- seats_27
DROP TABLE IF EXISTS `seats_27`;
CREATE TABLE `seats_27` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_27` WRITE;
INSERT INTO `seats_27` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,1),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(92,1),(93,1),(94,1),(95,1),(96,1),(97,1),(98,1),(99,1),(100,1),(101,1),(102,1),(103,1),(104,1),(105,1),(106,0),(107,1),(108,1),(109,1),(110,1),(111,1),(112,1),(113,1),(114,1),(115,1),(116,1),(117,1),(118,1),(119,1),(120,1),(121,1),(122,1),(123,1),(124,1),(125,1),(126,1),(127,1),(128,1),(129,1),(130,1),(131,1),(132,1),(133,1),(134,1),(135,1),(136,1),(137,1),(138,1),(139,1),(140,1),(141,1),(142,1),(143,1),(144,1),(145,1),(146,1),(147,1),(148,1),(149,1),(150,1);
UNLOCK TABLES;

-- seats_28
DROP TABLE IF EXISTS `seats_28`;
CREATE TABLE `seats_28` (
                            `id` int NOT NULL,
                            `status` int DEFAULT '1',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `seats_28` WRITE;
INSERT INTO `seats_28` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1),(56,1),(57,1),(58,1),(59,1),(60,1),(61,1),(62,1),(63,1),(64,1),(65,1),(66,1),(67,1),(68,1),(69,1),(70,1),(71,1),(72,1),(73,1),(74,1),(75,1),(76,1),(77,1),(78,1),(79,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(92,1),(93,1),(94,1),(95,1),(96,1),(97,1),(98,1),(99,1),(100,1);
UNLOCK TABLES;

-- Table: storeup
DROP TABLE IF EXISTS `storeup`;
CREATE TABLE `storeup` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `userid` bigint NOT NULL,
                           `refid` bigint DEFAULT NULL,
                           `tablename` varchar(200) DEFAULT NULL,
                           `name` varchar(200) NOT NULL,
                           `picture` longtext NOT NULL,
                           `type` varchar(200) DEFAULT '1',
                           `inteltype` varchar(200) DEFAULT NULL,
                           `remark` varchar(200) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1745494860776 DEFAULT CHARSET=utf8mb3;
LOCK TABLES `storeup` WRITE;
INSERT INTO `storeup` VALUES (1743159939726,'2025-03-28 11:05:39',1743085507549,21,'zixishi','名称1','upload/zixishi_tupian1.jpg','1',NULL,NULL),(1743159952733,'2025-03-28 11:05:51',1743085507549,21,'zixishi','名称1','upload/zixishi_tupian1.jpg','1',NULL,NULL),(1743650978022,'2025-04-03 03:29:37',17,21,'zixishi','自习室1','upload/zixishi_tupian1.jpg','1',NULL,NULL),(1743651084416,'2025-04-03 03:31:23',17,22,'zixishi','图书馆','upload/zixishi_tupian2.jpg','1',NULL,NULL),(1743651086107,'2025-04-03 03:31:25',16,21,'zixishi','大自习室','upload/zixishi_tupian1.jpg','1',NULL,NULL),(1745494860775,'2025-04-24 11:40:59',18,24,'zixishi','多媒体教室','upload/zixishi_tupian4.jpg','1',NULL,NULL);
UNLOCK TABLES;

-- Table: token
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `userid` bigint NOT NULL,
                         `username` varchar(100) NOT NULL,
                         `tablename` varchar(100) DEFAULT NULL,
                         `role` varchar(100) DEFAULT NULL,
                         `token` varchar(200) NOT NULL,
                         `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
LOCK TABLES `token` WRITE;
INSERT INTO `token` VALUES (1,1,'admin','users','管理员','xmmlfstwor9csk7xnphocqq47xy9amm4','2025-09-27 13:11:21','2025-12-21 13:17:10'),(2,19,'236002052','xuesheng','学生','xcu52vsqkx91w9z7qtgdalqd6009hmpw','2025-09-27 14:25:34','2025-12-17 03:54:45'),(3,17,'236003773','xuesheng','学生','fttz3gnvpu2vf1kxohv5cv5twkwf4kva','2025-09-03 07:29:42','2025-12-17 09:13:42'),(4,16,'236002928','xuesheng','学生','lpcfszejz5myrt1obf7mvkojidoou2ib','2025-09-03 07:29:26','2025-12-17 07:48:27'),(5,18,'236004998','xuesheng','学生','wkbz7woj7781xtiiczti9eqmgc6g3ypa','2025-09-03 07:24:05','2025-12-24 17:11:29'),(6,15,'236001205','xuesheng','学生','esiz2hcwzaa1uu5scqa1lgpvzj4z159p','2025-09-17 08:37:22','2025-12-17 09:43:22');
UNLOCK TABLES;

-- Table: users
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `username` varchar(100) NOT NULL,
                         `password` varchar(100) NOT NULL,
                         `role` varchar(100) DEFAULT '管理员',
                         `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'admin','123456','管理员','2025-10-08 10:15:30');
UNLOCK TABLES;

-- Table: xuesheng
DROP TABLE IF EXISTS `xuesheng`;
CREATE TABLE `xuesheng` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `xuehao` varchar(200) NOT NULL,
                            `mima` varchar(200) NOT NULL,
                            `xingming` varchar(200) NOT NULL,
                            `xingbie` varchar(200) DEFAULT NULL,
                            `touxiang` longtext,
                            `shouji` varchar(200) DEFAULT NULL,
                            `banji` varchar(200) DEFAULT NULL,
                            `zixishichang` double DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `xuehao` (`xuehao`)
) ENGINE=InnoDB AUTO_INCREMENT=1743085507550 DEFAULT CHARSET=utf8mb3;
LOCK TABLES `xuesheng` WRITE;
INSERT INTO `xuesheng` VALUES (15,'2025-10-08 08:09:37','236001205','123456','xushiyu','女','upload/xuesheng_touxiang2.jpg','16735464892','23计科2班',103.5),(16,'2025-10-08 07:25:12','236002928','123456','huruiyu','女','upload/xuesheng_touxiang5.jpg','17354826452','23计科1班',1756.5),(17,'2025-10-08 09:15:02','236003773','123456','zhangyuling','女','upload/xuesheng_touxiang3.jpg','18267834563','23计科2班',120),(18,'2025-10-08 02:45:12','236004998','123456','ayakuozi','女','upload/xuesheng_touxiang4.jpg','13077467681','23计科1班',190),(19,'2025-10-12 08:16:26','236002052','123456','zhengyilin','女','upload/xuesheng_touxiang1.jpg','18345623578','23计科2班',0);
UNLOCK TABLES;

-- Table: zixishi
DROP TABLE IF EXISTS `zixishi`;
CREATE TABLE `zixishi` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `mingcheng` varchar(200) NOT NULL,
                           `tupian` longtext,
                           `guimo` varchar(200) DEFAULT NULL,
                           `zuowei` int DEFAULT NULL,
                           `weizhi` varchar(200) NOT NULL,
                           `sheshi` varchar(200) DEFAULT NULL,
                           `keyueshijian` varchar(200) DEFAULT NULL,
                           `shifouyouke` varchar(200) DEFAULT NULL,
                           `xiangqing` longtext,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
LOCK TABLES `zixishi` WRITE;
INSERT INTO `zixishi` VALUES (21,'2025-10-08 08:25:42','大自习室','upload/zixishi_tupian1.jpg,upload/zixishi_tupian2.jpg,upload/zixishi_tupian3.jpg','大型',100,'1-203','设施1','可约时间1','否','<p>大型自习室</p>'),(22,'2025-10-08 09:35:17','图书馆','upload/zixishi_tupian2.jpg,upload/zixishi_tupian3.jpg,upload/zixishi_tupian4.jpg','大型',100,'图书馆','设施2','每天8：00至21：00，周一闭馆','是','<p>图书馆</p>'),(23,'2025-10-08 08:25:12','北区三号楼','upload/zixishi_tupian3.jpg,upload/zixishi_tupian4.jpg,upload/zixishi_tupian5.jpg','大型',40,'北区三号楼','设施3','每天8：00至21：00','是','<p>常常有课</p>'),(24,'2025-10-08 09:25:32','多媒体教室','upload/zixishi_tupian4.jpg,upload/zixishi_tupian5.jpg,upload/zixishi_tupian6.jpg','小型',20,'多媒体教室','设施4','每天8：00至21：00','是','<p>预约人数较多，请提前预约</p>'),(25,'2025-10-08 09:25:22','北区2号楼','upload/zixishi_tupian5.jpg,upload/zixishi_tupian6.jpg,upload/zixishi_tupian7.jpg','中型',80,'北区2号楼','设施5','每天8：00至21：00','是','<p>常常有课，请注意课表</p>'),(26,'2025-10-08 09:25:12','北区机房','upload/zixishi_tupian6.jpg,upload/zixishi_tupian7.jpg,upload/zixishi_tupian8.jpg','大型',90,'北区机房','设施6','每天8：00至18：00','是','<p>请注意可预约时间</p>'),(27,'2025-10-08 08:35:12','s2s','upload/zixishi_tupian7.jpg,upload/zixishi_tupian8.jpg,upload/zixishi_tupian9.jpg','大型',150,'s2s','设施7','每天6：00至23：00','否','<p>桌子较小</p>'),(28,'2025-10-08 07:45:12','宿舍楼自习室','upload/zixishi_tupian8.jpg,upload/zixishi_tupian9.jpg,upload/zixishi_tupian10.jpg','大型',100,'宿舍楼自习室','设施8','每天8：00至0：00','否','<p>请保持安静</p>');
UNLOCK TABLES;

-- Finalize: restore checks and SQL mode
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- End of file