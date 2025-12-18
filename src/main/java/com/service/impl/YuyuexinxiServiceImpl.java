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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("yuyuexinxiService")
public class YuyuexinxiServiceImpl extends ServiceImpl<YuyuexinxiDao, YuyuexinxiEntity> implements YuyuexinxiService {

    // 原有基础方法：完全保留
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

    // 功能1：生成带业务辨识度的唯一预约单号（来自yuyuexinxiServiceImpl(1)）
    private String generateYuyueDanhao() {
        // 1. 固定业务前缀（预约=YY）
        String prefix = "YY";
        // 2. 日期部分：yyyyMMdd（如20251217）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());
        // 3. 当日流水号：查询今日最大流水号，+1后补零（3位）
        int maxSeq = baseMapper.getMaxYuyueSeqByDate(datePart);
        int newSeq = maxSeq + 1;
        String seqPart = String.format("%03d", newSeq); // 不足3位补零（如1→001）
        // 4. 随机校验位（3位数字+字母，防重+防猜测）
        String randomPart = RandomStringUtils.randomAlphanumeric(3);
        // 5. 拼接最终单号（示例：YY2025121700189X）
        return prefix + datePart + seqPart + randomPart;
    }

    // 功能2：提交预约核心逻辑（融合双方功能：自定义单号+事务保证+自习室名称赋值+违纪标记初始化）
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitYuyue(Integer zixishiid, Integer zuowei, String xuehao, String xingming, String shouji, Date yuyueStart, Date yuyueEnd, String mingcheng, String beizhu) {
        // 1. 检测座位预约冲突
        int conflictCount = baseMapper.countSeatConflict(zixishiid, zuowei, yuyueStart, yuyueEnd);
        if (conflictCount > 0) {
            return false;
        }

        // 2. 组装预约数据
        YuyuexinxiEntity yuyue = new YuyuexinxiEntity();
        yuyue.setYuyuedanhao(generateYuyueDanhao()); // 使用自定义单号生成逻辑（替代UUID）
        yuyue.setZixishiid(zixishiid);
        yuyue.setZuowei(zuowei);
        yuyue.setXuehao(xuehao);
        yuyue.setXingming(xingming);
        yuyue.setShouji(shouji);
        yuyue.setYuyueStart(yuyueStart);
        yuyue.setYuyueEnd(yuyueEnd);
        yuyue.setMingcheng(mingcheng); // 自习室名称字段
        // 关键修改：保存备注 beizhu 到实体，确保入库
        yuyue.setBeizhu(beizhu);
        yuyue.setQiandaozhuangtai("未签到");
        yuyue.setQiantuizhuangtai("未签退");
        yuyue.setAddtime(new Date());
        yuyue.setWeiguiFlag(0); // 初始化违纪标记为0（未违纪）

        // 3. 插入数据库
        this.insert(yuyue);
        return true;
    }

    // 功能3：查询指定座位的已预约时段（供前端展示）
    @Override
    public List<YuyuexinxiEntity> getSeatYuyueList(Integer zixishiid, Integer zuowei) {
        return baseMapper.selectSeatYuyueList(zixishiid, zuowei);
    }

    // 功能4：计算座位可预约时间段（来自yuyuexinxiServiceimpl2，支持粒度控制和缓冲时间）
    public static class Interval {
        public Date start;
        public Date end;

        public Interval(Date s, Date e) {
            start = s;
            end = e;
        }
    }

    @Override
    public List<Map<String, Object>> getSeatAvailability(Integer zixishiid, Integer zuowei, LocalDate date, int granularityMinutes, int bufferMinutes, String openTimeStr) {
        // （实现保持不变，省略重复说明）
        String openStart = "08:00";
        String openEnd = "22:00";
        if (openTimeStr != null) {
            String s = openTimeStr.replace("：", ":").replace("至", "-");
            s = s.replaceAll("每[天周月年\\s]*", "");
            if (s.contains("-")) {
                String[] parts = s.split("-");
                if (parts.length >= 2) {
                    openStart = parts[0].trim();
                    openEnd = parts[1].trim();
                }
            }
        }

        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime dayStartLdt = LocalDateTime.of(date, LocalTime.parse(openStart, dtfTime));
        LocalDateTime dayEndLdt = LocalDateTime.of(date, LocalTime.parse(openEnd, dtfTime));
        Date dayStart = Date.from(dayStartLdt.atZone(ZoneId.systemDefault()).toInstant());
        Date dayEnd = Date.from(dayEndLdt.atZone(ZoneId.systemDefault()).toInstant());

        List<YuyuexinxiEntity> bookings = baseMapper.selectBookingsInRange(zixishiid, zuowei, dayStart, dayEnd);

        List<Interval> booked = bookings.stream().map(b -> {
            Date s = b.getYuyueStart();
            Date e = b.getYuyueEnd();
            if (s.before(dayStart)) s = dayStart;
            if (e.after(dayEnd)) e = dayEnd;
            return new Interval(s, e);
        }).collect(Collectors.toList());

        booked.sort(Comparator.comparingLong(i -> i.start.getTime()));
        List<Interval> merged = new ArrayList<>();
        for (Interval it : booked) {
            if (merged.isEmpty()) {
                merged.add(it);
                continue;
            }
            Interval last = merged.get(merged.size() - 1);
            if (it.start.getTime() <= last.end.getTime()) {
                if (it.end.getTime() > last.end.getTime()) {
                    last.end = it.end;
                }
            } else {
                merged.add(it);
            }
        }

        long bufferMs = bufferMinutes * 60L * 1000L;
        List<Interval> buffered = new ArrayList<>();
        for (Interval it : merged) {
            long s = Math.max(it.start.getTime() - bufferMs, dayStart.getTime());
            long e = Math.min(it.end.getTime() + bufferMs, dayEnd.getTime());
            buffered.add(new Interval(new Date(s), new Date(e)));
        }

        buffered.sort(Comparator.comparingLong(i -> i.start.getTime()));
        List<Interval> mergedBuffered = new ArrayList<>();
        for (Interval it : buffered) {
            if (mergedBuffered.isEmpty()) {
                mergedBuffered.add(it);
                continue;
            }
            Interval last = mergedBuffered.get(mergedBuffered.size() - 1);
            if (it.start.getTime() <= last.end.getTime()) {
                if (it.end.getTime() > last.end.getTime()) {
                    last.end = it.end;
                }
            } else {
                mergedBuffered.add(it);
            }
        }

        List<Interval> available = new ArrayList<>();
        long cursor = dayStart.getTime();
        for (Interval b : mergedBuffered) {
            if (b.start.getTime() > cursor) {
                available.add(new Interval(new Date(cursor), new Date(b.start.getTime())));
            }
            cursor = Math.max(cursor, b.end.getTime());
        }
        if (cursor < dayEnd.getTime()) {
            available.add(new Interval(new Date(cursor), new Date(dayEnd.getTime())));
        }

        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Interval it : available) {
            Map<String, Object> m = new HashMap<>();
            m.put("start", it.start.getTime());
            m.put("end", it.end.getTime());
            m.put("startStr", sdf.format(it.start));
            m.put("endStr", sdf.format(it.end));
            m.put("status", "available");
            result.add(m);
        }
        return result;
    }
}