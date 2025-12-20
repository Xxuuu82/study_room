package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.HeiMingDan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
// 泛型要和实体类名完全一致（HeiMingDan）
public interface HeimingdanDao extends BaseMapper<HeiMingDan> {
}