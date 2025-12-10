package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.XueshengEntity;
import com.entity.view.XueshengView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 学生
 */
public interface XueshengDao extends BaseMapper<XueshengEntity> {


    List<XueshengView> selectListView(@Param("ew") Wrapper<XueshengEntity> wrapper);

    List<XueshengView> selectListView(Pagination page, @Param("ew") Wrapper<XueshengEntity> wrapper);

    XueshengView selectView(@Param("ew") Wrapper<XueshengEntity> wrapper);


}
