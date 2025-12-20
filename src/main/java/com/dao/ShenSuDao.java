package com.dao;

import com.entity.ShenSu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShenSuDao {
    // 分页查询申诉记录
    List<ShenSu> selectPage(@Param("params") Map<String, Object> params);
    // 查询总数
    Long selectCount(@Param("params") Map<String, Object> params);
    // 更新申诉处理状态
    int updateChuliStatus(@Param("id") Long id,
                          @Param("chuliZhuangtai") Integer chuliZhuangtai,
                          @Param("chuliRen") String chuliRen,
                          @Param("chuliBeizhu") String chuliBeizhu);
    // 根据ID查询申诉记录
    ShenSu selectById(@Param("id") Long id);
}
