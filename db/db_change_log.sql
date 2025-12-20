-- 2025-12-12 第一次变更：删除yuyueshijian、tupian，新增预约时间段/签到签退时间
ALTER TABLE yuyuexinxi DROP COLUMN yuyueshijian;
ALTER TABLE yuyuexinxi DROP COLUMN tupian;
ALTER TABLE yuyuexinxi ADD COLUMN yuyue_start datetime  NULL COMMENT '预约开始时间';
ALTER TABLE yuyuexinxi ADD COLUMN yuyue_end datetime  NULL COMMENT '预约结束时间';
ALTER TABLE yuyuexinxi ADD COLUMN qiandao_time datetime DEFAULT NULL COMMENT '实际签到时间';
ALTER TABLE yuyuexinxi ADD COLUMN qiantui_time datetime DEFAULT NULL COMMENT '实际签退时间';

-- 2025-12-13 第二次变更：新增违约标记字段
ALTER TABLE yuyuexinxi ADD COLUMN weigui_flag tinyint(1) DEFAULT 0 COMMENT '是否触发违约 0=否 1=是';

-- 新增唯一索引（执行一次即可）
ALTER TABLE yuyuexinxi ADD UNIQUE INDEX idx_yuyuedanhao (yuyuedanhao);

UPDATE heimingdan
SET xuehao = '236001205', xingming = 'xushiyu'
WHERE id = 4;

DESCRIBE weigui_record;

INSERT INTO weigui_record (
    weiguiDanhao,
    xuehao,
    xingming,
    zixishiMingcheng,
    zuoWei,
    weiguiShijian,
    weiguileixingName,
    weiguiBeizhu,
    isValid
) VALUES (
             CONCAT('WG', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')), -- 改用CONCAT拼接字符串
             '236001205',
             'xsy',
             '图书馆',
             5,
             '2025-12-20 15:30:20',
             '教室乱吃乱喝',
             '乱吃乱喝',
             1
         );

DELETE FROM weigui_record
WHERE weiguiDanhao = 'WG20251220145741';

INSERT INTO weigui_record ( weiguiDanhao, xuehao, xingming, zixishiMingcheng, zuoWei, weiguiShijian, weiguiLeixing, weiguiBeizhu, isValid ) VALUES ( CONCAT('WG', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')), '236001205', 'xsy', '图书馆', 5, '2025-12-20 15:30:20', 'chifan', '乱吃乱喝', 1 );

UPDATE weigui_record
SET weiguiLeixing = '教室乱吃乱喝',
    weiguiLeixingName = '乱吃乱喝'
WHERE xuehao = '236001205' AND weiguiShijian = '2025-12-20 15:30:20';

DESCRIBE shensu;