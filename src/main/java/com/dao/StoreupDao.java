package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.StoreupEntity;
import com.entity.view.StoreupView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 收藏表
 */
public interface StoreupDao extends BaseMapper<StoreupEntity> {


    List<StoreupView> selectListView(@Param("ew") Wrapper<StoreupEntity> wrapper);

    List<StoreupView> selectListView(Pagination page, @Param("ew") Wrapper<StoreupEntity> wrapper);

    StoreupView selectView(@Param("ew") Wrapper<StoreupEntity> wrapper);


}
