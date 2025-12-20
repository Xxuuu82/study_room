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

describe complaint;

INSERT INTO complaint (
    complaint_no,
    user_id,
    room_id,
    seat_id,
    category,
    detail,
    status,
    is_processed
)
VALUES (
           'CP20251220001',  -- 唯一投诉编号（可自定义）
           236001205,          -- 你的目标user_id
           1,                  -- 自习室ID（根据实际情况调整）
           10,                 -- 座位ID（根据实际情况调整）
           2,                  -- 类型：2对应“大声喧哗”
           '在自习室大声讨论问题，影响他人学习',  -- 投诉详情
           0,                  -- 状态：0对应“待处理”
           0                   -- 是否处理：0对应未处理
       );