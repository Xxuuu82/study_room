package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.WeiguiRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 违规记录Dao接口（适配项目dao目录结构，支持原生SQL查询）
 */
@Repository
public interface WeiguiRecordDao extends BaseMapper<WeiguiRecord> {
    // 原生SQL查询列表（适配纯驼峰字段）
    @Select("${sql}")
    List<WeiguiRecord> selectListBySql(@Param("sql") String sql);

    // 原生SQL统计总数（适配纯驼峰字段）
    @Select("${sql}")
    Integer selectCountBySql(@Param("sql") String sql);
}