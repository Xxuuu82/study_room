package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.QiandaoxinxiEntity;
import com.entity.view.QiandaoxinxiView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 签到信息
 */
public interface QiandaoxinxiService extends IService<QiandaoxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<QiandaoxinxiView> selectListView(Wrapper<QiandaoxinxiEntity> wrapper);

    QiandaoxinxiView selectView(@Param("ew") Wrapper<QiandaoxinxiEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<QiandaoxinxiEntity> wrapper);


}

