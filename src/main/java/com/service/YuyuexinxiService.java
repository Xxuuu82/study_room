package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
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

    // 新增：提交预约（核心业务方法，适配你的数据库和预约逻辑）
    // 注意：新增了 beizhu 参数，确保备注能被保存
    boolean submitYuyue(
            Integer zixishiid,  // 自习室序号
            Integer zuowei,     // 座位号
            String xuehao,      // 学号
            String xingming,    // 姓名
            String shouji,      // 手机
            Date yuyueStart,    // 预约开始时间（对应数据库yuyue_start）
            Date yuyueEnd,      // 预约结束时间（对应数据库yuyue_end）
            String mingcheng,   // 自习室名称
            String beizhu       // 备注（新增）
    );

    // 新增：查询指定座位的所有已预约时段（供前端展示空闲/占用时段）
    List<YuyuexinxiEntity> getSeatYuyueList(Integer zixishiid, Integer zuowei);

    List<Map<String,Object>> getSeatAvailability(Integer zixishiid, Integer zuowei, LocalDate date, int granularityMinutes, int bufferMinutes, String openTimeStr);
}