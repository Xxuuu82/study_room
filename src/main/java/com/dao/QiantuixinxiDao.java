package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.QiantuixinxiEntity;
import com.entity.view.QiantuixinxiView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 签到信息
 */
public interface QiantuixinxiDao extends BaseMapper<QiantuixinxiEntity> {


    List<QiantuixinxiView> selectListView(@Param("ew") Wrapper<QiantuixinxiEntity> wrapper);

    List<QiantuixinxiView> selectListView(Pagination page, @Param("ew") Wrapper<QiantuixinxiEntity> wrapper);

    QiantuixinxiView selectView(@Param("ew") Wrapper<QiantuixinxiEntity> wrapper);


}
