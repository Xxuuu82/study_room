package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.ComplaintEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComplaintDao extends BaseMapper<ComplaintEntity> {

    List<ComplaintEntity> selectPage(@Param("params") Map<String, Object> params);

    Long count(@Param("params") Map<String, Object> params);

    ComplaintEntity selectById(@Param("id") Long id);

    int updateApprove(@Param("id") Long id,
                      @Param("handlerAdminId") Long handlerAdminId,
                      @Param("handleSummary") String handleSummary);

    int updateReject(@Param("id") Long id,
                     @Param("handlerAdminId") Long handlerAdminId,
                     @Param("rejectReason") String rejectReason);

    int deleteById(@Param("id") Long id);
}
