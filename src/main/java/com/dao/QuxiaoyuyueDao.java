package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.QuxiaoyuyueEntity;
import com.entity.view.QuxiaoyuyueView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 取消预约
 */
public interface QuxiaoyuyueDao extends BaseMapper<QuxiaoyuyueEntity> {


    List<QuxiaoyuyueView> selectListView(@Param("ew") Wrapper<QuxiaoyuyueEntity> wrapper);

    List<QuxiaoyuyueView> selectListView(Pagination page, @Param("ew") Wrapper<QuxiaoyuyueEntity> wrapper);

    QuxiaoyuyueView selectView(@Param("ew") Wrapper<QuxiaoyuyueEntity> wrapper);


}
