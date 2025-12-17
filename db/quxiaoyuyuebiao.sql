DESCRIBE yuyuexinxi;

-- 1. 删除原有字段 `yuyueshijian`
ALTER TABLE qiantuixinxi
    DROP COLUMN yuyuedanhao;

-- 2. 新增与 `yuyuexinxi` 表一致的预约开始/结束时间字段
ALTER TABLE quxiaoyuyue
    ADD COLUMN `yuyue_start` DATETIME NULL COMMENT '预约开始时间' AFTER `mingcheng`,
    ADD COLUMN `yuyue_end` DATETIME NULL COMMENT '预约结束时间' AFTER `yuyue_start`;

ALTER TABLE quxiaoyuyue
    DROP COLUMN tupian;

DESCRIBE qiandaoxinxi;
DESCRIBE yuyuexinxi;
DESCRIBE qiantuixinxi;
DESCRIBE quxiaoyuyue;

-- 给签到表新增预约单号字段
ALTER TABLE qiandaoxinxi ADD COLUMN yuyuedanhao VARCHAR(50) COMMENT '关联的预约单号';
-- 给签退表新增预约单号字段
ALTER TABLE qiantuixinxi ADD COLUMN yuyuedanhao VARCHAR(50) COMMENT '关联的预约单号';


UPDATE qiandaoxinxi qd
    INNER JOIN yuyuexinxi yy
    ON qd.xuehao = yy.xuehao
        AND qd.qiandaoshijian BETWEEN yy.yuyue_start AND yy.yuyue_end
SET qd.yuyuedanhao = yy.yuyuedanhao;


UPDATE qiantuixinxi qt
    INNER JOIN yuyuexinxi yy
    ON qt.xuehao = yy.xuehao
        AND qt.qiantuishijian BETWEEN yy.yuyue_start AND yy.yuyue_end
SET qt.yuyuedanhao = yy.yuyuedanhao;


ALTER TABLE yuyuexinxi
    DROP COLUMN qiandao_time,
    DROP COLUMN qiantui_time;

ALTER TABLE yuyuexinxi
    MODIFY COLUMN yuyue_start datetime NULL,
    MODIFY COLUMN yuyue_end datetime NULL;