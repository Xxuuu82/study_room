package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.ZixishiEntity;
import com.entity.view.ZixishiView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 自习室
 */
public interface ZixishiDao extends BaseMapper<ZixishiEntity> {


    List<ZixishiView> selectListView(@Param("ew") Wrapper<ZixishiEntity> wrapper);

    List<ZixishiView> selectListView(Pagination page, @Param("ew") Wrapper<ZixishiEntity> wrapper);

    ZixishiView selectView(@Param("ew") Wrapper<ZixishiEntity> wrapper);


}
