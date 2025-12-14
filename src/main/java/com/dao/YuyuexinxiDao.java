package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 预约信息 Dao 接口（修复参数名匹配问题）
 */
public interface YuyuexinxiDao extends BaseMapper<YuyuexinxiEntity> {

    List<YuyuexinxiView> selectListView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    List<YuyuexinxiView> selectListView(Pagination page, @Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    YuyuexinxiView selectView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    /**
     * 检测座位预约冲突（修复：参数名和ServiceImpl传参一致）
     * @param zixishiid 自习室序号
     * @param zuowei 座位号
     * @param yuyueStart 预约开始时间
     * @param yuyueEnd 预约结束时间
     * @return 冲突数量
     */
    int countSeatConflict(
            @Param("zixishiid") Integer zixishiid,
            @Param("zuowei") Integer zuowei,
            @Param("yuyueStart") Date yuyueStart, // 关键：参数名改为yuyueStart，和ServiceImpl传参一致
            @Param("yuyueEnd") Date yuyueEnd      // 关键：参数名改为yuyueEnd，和ServiceImpl传参一致
    );

    /**
     * 查询指定座位的已预约时段
     * @param zixishiid 自习室序号
     * @param zuowei 座位号
     * @return 预约列表
     */
    List<YuyuexinxiEntity> selectSeatYuyueList(
            @Param("zixishiid") Integer zixishiid,
            @Param("zuowei") Integer zuowei
    );

}