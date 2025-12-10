package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.QiandaoxinxiEntity;
import com.entity.view.QiandaoxinxiView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 签到信息
 */
public interface QiandaoxinxiDao extends BaseMapper<QiandaoxinxiEntity> {


    List<QiandaoxinxiView> selectListView(@Param("ew") Wrapper<QiandaoxinxiEntity> wrapper);

    List<QiandaoxinxiView> selectListView(Pagination page, @Param("ew") Wrapper<QiandaoxinxiEntity> wrapper);

    QiandaoxinxiView selectView(@Param("ew") Wrapper<QiandaoxinxiEntity> wrapper);


}
