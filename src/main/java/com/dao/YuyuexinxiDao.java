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
 * 预约信息Dao接口（融合双方所有方法）
 */
public interface YuyuexinxiDao extends BaseMapper<YuyuexinxiEntity> {

    // 基础查询方法
    List<YuyuexinxiView> selectListView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    List<YuyuexinxiView> selectListView(Pagination page, @Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    YuyuexinxiView selectView(@Param("ew") Wrapper<YuyuexinxiEntity> wrapper);

    /**
     * 检测座位预约冲突（参数名匹配ServiceImpl）
     * @param zixishiid 自习室序号
     * @param zuowei 座位号
     * @param yuyueStart 预约开始时间
     * @param yuyueEnd 预约结束时间
     * @return 冲突数量
     */
    int countSeatConflict(
            @Param("zixishiid") Integer zixishiid,
            @Param("zuowei") Integer zuowei,
            @Param("yuyueStart") Date yuyueStart,
            @Param("yuyueEnd") Date yuyueEnd
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

    /**
     * 查询指定日期的预约单最大流水号（用于生成自定义预约单号）
     * @param dateStr 日期格式：yyyyMMdd
     * @return 当日最大流水号（无数据返回0）
     */
    int getMaxYuyueSeqByDate(@Param("dateStr") String dateStr);

    /**
     * 查询指定时段内的座位预约记录（用于计算可预约区间）
     * @param zixishiid 自习室序号
     * @param zuowei 座位号
     * @param dayStart 时段开始时间
     * @param dayEnd 时段结束时间
     * @return 预约列表
     */
    List<YuyuexinxiEntity> selectBookingsInRange(
            @Param("zixishiid") Integer zixishiid,
            @Param("zuowei") Integer zuowei,
            @Param("dayStart") Date dayStart,
            @Param("dayEnd") Date dayEnd
    );
}