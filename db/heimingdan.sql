CREATE DATABASE IF NOT EXISTS study_room DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE study_room;
DROP TABLE IF EXISTS `heimingdan`;
CREATE TABLE `heimingdan` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `xuehao` varchar(200) NOT NULL COMMENT '学生学号',
                              `xingming` varchar(200) DEFAULT NULL COMMENT '学生姓名',
                              `total_weigui_count` tinyint NOT NULL DEFAULT 0 COMMENT '累计违规次数（≥3次自动拉黑）',
                              `weigui_chifan_count` tinyint NOT NULL DEFAULT 0 COMMENT '教室乱吃乱喝违规次数',
                              `weigui_weiqiandao_count` tinyint NOT NULL DEFAULT 0 COMMENT '未签到违规次数',
                              `weigui_xuanhua_count` tinyint NOT NULL DEFAULT 0 COMMENT '大声喧哗违规次数',
                              `weigui_suibiaobian_count` tinyint NOT NULL DEFAULT 0 COMMENT '随地大小便违规次数',
                              `other_weigui_desc` varchar(500) DEFAULT NULL COMMENT '其他违规类型说明（非以上四类时填写）',
                              `heimingdan_yuanyin` longtext COMMENT '拉黑原因（汇总说明）',
                              `heimingdan_start_time` datetime NOT NULL COMMENT '拉黑开始时间（即加入黑名单时间）',
                              `heimingdan_end_time` datetime NOT NULL COMMENT '拉黑结束时间（即解除时间）',
                              `shifoujiechu` tinyint DEFAULT 0 COMMENT '是否解除（0=未解除，1=已解除）',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_xuehao_status` (`xuehao`, `shifoujiechu`),
                              KEY `idx_xuehao` (`xuehao`),
                              KEY `idx_heimingdan_time` (`heimingdan_start_time`, `heimingdan_end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单表（最终版）';