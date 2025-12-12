-- 2025-12-12 第一次变更：删除yuyueshijian、tupian，新增预约时间段/签到签退时间
ALTER TABLE yuyuexinxi DROP COLUMN yuyueshijian;
ALTER TABLE yuyuexinxi DROP COLUMN tupian;
ALTER TABLE yuyuexinxi ADD COLUMN yuyue_start datetime NOT NULL COMMENT '预约开始时间';
ALTER TABLE yuyuexinxi ADD COLUMN yuyue_end datetime NOT NULL COMMENT '预约结束时间';
ALTER TABLE yuyuexinxi ADD COLUMN qiandao_time datetime DEFAULT NULL COMMENT '实际签到时间';
ALTER TABLE yuyuexinxi ADD COLUMN qiantui_time datetime DEFAULT NULL COMMENT '实际签退时间';

-- 2025-12-13 第二次变更：新增违约标记字段
ALTER TABLE yuyuexinxi ADD COLUMN weigui_flag tinyint(1) DEFAULT 0 COMMENT '是否触发违约 0=否 1=是';