package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.QuxiaoyuyueEntity;
import com.entity.view.QuxiaoyuyueView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 取消预约
 */
public interface QuxiaoyuyueService extends IService<QuxiaoyuyueEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<QuxiaoyuyueView> selectListView(Wrapper<QuxiaoyuyueEntity> wrapper);

    QuxiaoyuyueView selectView(@Param("ew") Wrapper<QuxiaoyuyueEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<QuxiaoyuyueEntity> wrapper);


}

