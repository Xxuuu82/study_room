package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ZixishiEntity;
import com.entity.view.ZixishiView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 自习室
 */
public interface ZixishiService extends IService<ZixishiEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<ZixishiView> selectListView(Wrapper<ZixishiEntity> wrapper);

    ZixishiView selectView(@Param("ew") Wrapper<ZixishiEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<ZixishiEntity> wrapper);


}

