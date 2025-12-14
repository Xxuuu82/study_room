package com.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entity.HeiMingDan;

import java.util.Map;

/**
 * 黑名单服务接口（适配MyBatis-Plus 2.3）
 */
public interface HeiMingDanService extends IService<HeiMingDan> {
    // 分页查询黑名单列表（Page是MyBatis-Plus 2.3的Page）
    Page<HeiMingDan> getBlackList(Page<HeiMingDan> page, Map<String, Object> params);

    // 解除黑名单
    boolean jiechuBlackList(String xuehao);
}