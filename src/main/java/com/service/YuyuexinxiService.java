package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 预约信息
 */
public interface YuyuexinxiService extends IService<YuyuexinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<YuyuexinxiView> selectListView(Wrapper<YuyuexinxiEntity> wrapper);

    YuyuexinxiView selectView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<YuyuexinxiEntity> wrapper);


}

