package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.YuyuexinxiDao;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import com.service.YuyuexinxiService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("yuyuexinxiService")
public class YuyuexinxiServiceImpl extends ServiceImpl<YuyuexinxiDao, YuyuexinxiEntity> implements YuyuexinxiService {

    // 原有方法：完全保留，无任何修改
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<YuyuexinxiEntity> page = this.selectPage(
                new Query<YuyuexinxiEntity>(params).getPage(),
                new EntityWrapper<YuyuexinxiEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<YuyuexinxiEntity> wrapper) {
        Page<YuyuexinxiView> page = new Query<YuyuexinxiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<YuyuexinxiView> selectListView(Wrapper<YuyuexinxiEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public YuyuexinxiView selectView(Wrapper<YuyuexinxiEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }

    // 新增：提交预约核心逻辑（仅新增mingcheng参数和赋值）
    @Override
    @Transactional(rollbackFor = Exception.class) // 事务保证入库原子性，避免数据异常
    public boolean submitYuyue(Integer zixishiid, Integer zuowei, String xuehao, String xingming, String shouji, Date yuyueStart, Date yuyueEnd, String mingcheng) {
        // 1. 检测该座位在指定时段是否有冲突（调用Dao层的冲突检测方法）
        int conflictCount = baseMapper.countSeatConflict(zixishiid, zuowei, yuyueStart, yuyueEnd);
        if (conflictCount > 0) {
            return false; // 有冲突，返回预约失败
        }

        // 2. 无冲突，组装预约数据（字段完全匹配你的数据库表）
        YuyuexinxiEntity yuyue = new YuyuexinxiEntity();
        yuyue.setYuyuedanhao(UUID.randomUUID().toString().replace("-", "")); // 生成唯一预约单号
        yuyue.setZixishiid(zixishiid); // 自习室序号
        yuyue.setZuowei(zuowei);       // 座位号
        yuyue.setXuehao(xuehao);       // 学号
        yuyue.setXingming(xingming);   // 姓名
        yuyue.setShouji(shouji);       // 手机号
        yuyue.setYuyueStart(yuyueStart); // 预约开始时间（对应数据库yuyue_start）
        yuyue.setYuyueEnd(yuyueEnd);   // 预约结束时间（对应数据库yuyue_end）
        yuyue.setMingcheng(mingcheng); // 新增：赋值自习室名称
        yuyue.setQiandaozhuangtai("未签到"); // 初始签到状态
        yuyue.setQiantuizhuangtai("未签退"); // 初始签退状态
        yuyue.setAddtime(new Date());  // 预约创建时间

        // 3. 插入数据库（MyBatis-Plus自带的insert方法，自动适配字段映射）
        this.insert(yuyue);
        return true; // 预约成功
    }

    // 新增：查询指定座位的所有已预约时段（供前端展示）
    @Override
    public List<YuyuexinxiEntity> getSeatYuyueList(Integer zixishiid, Integer zuowei) {
        // 调用Dao层的查询方法，返回该座位未结束的预约信息
        return baseMapper.selectSeatYuyueList(zixishiid, zuowei);
    }

}