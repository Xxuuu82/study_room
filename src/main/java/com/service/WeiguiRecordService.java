package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.WeiguiRecord;

import java.util.List;

/**
 * 违规记录Service接口（适配MyBatis-Plus 2.3版本）
 */
public interface WeiguiRecordService extends IService<WeiguiRecord> {
    // 适配低版本：参数为Wrapper类型
    List<WeiguiRecord> selectList(Wrapper<WeiguiRecord> wrapper);

    // 新增：原生SQL查询方法
    List<WeiguiRecord> selectListBySql(String sql);
    Integer selectCountBySql(String sql);
}