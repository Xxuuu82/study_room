package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 预约信息
 */
public interface YuyuexinxiDao extends BaseMapper<YuyuexinxiEntity> {


    List<YuyuexinxiView> selectListView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    List<YuyuexinxiView> selectListView(Pagination page, @Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    YuyuexinxiView selectView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);


}
