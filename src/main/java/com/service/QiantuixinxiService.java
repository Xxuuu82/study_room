package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.QiantuixinxiEntity;
import com.entity.view.QiantuixinxiView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 签退信息
 */
public interface QiantuixinxiService extends IService<QiantuixinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<QiantuixinxiView> selectListView(Wrapper<QiantuixinxiEntity> wrapper);

    QiantuixinxiView selectView(@Param("ew") Wrapper<QiantuixinxiEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<QiantuixinxiEntity> wrapper);


}

