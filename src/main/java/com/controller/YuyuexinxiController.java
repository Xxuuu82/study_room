package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.QiandaoxinxiEntity;
import com.entity.QiantuixinxiEntity;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import com.service.QiandaoxinxiService;
import com.service.QiantuixinxiService;
import com.service.YuyuexinxiService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 预约信息后端接口（融合双方所有功能）
 */
@RestController
@RequestMapping("/yuyuexinxi")
public class YuyuexinxiController {
    private static final Logger logger = LoggerFactory.getLogger(YuyuexinxiController.class);

    @Autowired
    private YuyuexinxiService yuyuexinxiService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private QiandaoxinxiService qiandaoxinxiService;
    @Autowired
    private QiantuixinxiService qiantuixinxiService;

    // 功能1：自动标记过期违纪（来自yuyuexinxicontroller(2)）
    private void autoMarkOverdueWeigui() {
        try {
            Date now = new Date();
            // 1) 过了预约结束时间且未签退 → 违纪
            String sql1 = "UPDATE yuyuexinxi SET weigui_flag = 1 " +
                    "WHERE yuyue_end < ? AND (qiantuizhuangtai IS NULL OR qiantuizhuangtai <> '已签退') " +
                    "AND (weigui_flag IS NULL OR weigui_flag = 0)";
            jdbcTemplate.update(sql1, now);
            // 2) 过了预约结束时间且未签到 → 违纪
            String sql2 = "UPDATE yuyuexinxi SET weigui_flag = 1 " +
                    "WHERE yuyue_end < ? AND (qiandaozhuangtai IS NULL OR qiandaozhuangtai <> '已签到') " +
                    "AND (weigui_flag IS NULL OR weigui_flag = 0)";
            jdbcTemplate.update(sql2, now);
        } catch (Exception e) {
            logger.warn("自动标记过期违纪失败：{}", e.getMessage());
        }
    }

    // 功能2：后端分页列表（带违纪自动标记）
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        try {
            autoMarkOverdueWeigui(); // 先标记过期违纪
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
            }
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime,weigui_flag");
            PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));
            return R.ok().put("data", page);
        } catch (Exception e) {
            logger.error("查询预约分页失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能3：前端列表（带违纪自动标记）
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        try {
            autoMarkOverdueWeigui(); // 先标记过期违纪
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
            }
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime,weigui_flag");
            PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));
            return R.ok().put("data", page);
        } catch (Exception e) {
            logger.error("查询预约列表失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能4：基础列表查询
    @RequestMapping("/lists")
    public R lists(YuyuexinxiEntity yuyuexinxi) {
        try {
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime,weigui_flag");
            ew.allEq(MPUtil.allEQMapPre(yuyuexinxi, "yuyuexinxi"));
            return R.ok().put("data", yuyuexinxiService.selectListView(ew));
        } catch (Exception e) {
            logger.error("查询预约基础列表失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能5：单条查询
    @RequestMapping("/query")
    public R query(YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime,weigui_flag");
            ew.allEq(MPUtil.allEQMapPre(yuyuexinxi, "yuyuexinxi"));
            if (tableName.equals("xuesheng")) {
                ew.eq("xuehao", (String) request.getSession().getAttribute("username"));
            }
            YuyuexinxiView yuyuexinxiView = yuyuexinxiService.selectView(ew);
            return R.ok("查询预约信息成功").put("data", yuyuexinxiView);
        } catch (Exception e) {
            logger.error("查询预约信息失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能6：后端详情查询
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            YuyuexinxiEntity yuyuexinxi = yuyuexinxiService.selectById(id);
            if (yuyuexinxi == null) {
                logger.warn("预约记录不存在，ID：{}", id);
                return R.error("预约记录不存在");
            }
            // 学生只能查看自己的记录
            if (tableName.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null || !currentXuehao.equals(yuyuexinxi.getXuehao())) {
                    logger.warn("学生{}尝试访问他人预约记录，记录ID：{}", currentXuehao, id);
                    return R.error("无权限查看该预约记录");
                }
            }
            yuyuexinxi.setQiandaoshijian(null);
            yuyuexinxi.setQiantuishijian(null);
            yuyuexinxi.setZixishichang(null);
            return R.ok().put("data", yuyuexinxi);
        } catch (Exception e) {
            logger.error("查询预约详情失败，ID：{}", id, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能7：前端详情查询（带签到/签退信息关联）
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable Long id, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            String currentXuehao = (String) request.getSession().getAttribute("username");
            YuyuexinxiEntity yuyue = yuyuexinxiService.selectById(id);
            if (yuyue == null) {
                logger.warn("预约记录不存在，ID：{}", id);
                return R.error("预约记录不存在");
            }
            // 学生只能查看自己的记录
            if (tableName.equals("xuesheng") && currentXuehao != null && !currentXuehao.equals(yuyue.getXuehao())) {
                logger.warn("学生{}尝试访问他人预约记录，记录ID：{}", currentXuehao, id);
                return R.error("无权限查看该预约记录");
            }

            // 关联最新签到时间
            try {
                QiandaoxinxiEntity qiandao = qiandaoxinxiService.selectOne(
                        new EntityWrapper<QiandaoxinxiEntity>()
                                .eq("yuyuedanhao", yuyue.getYuyuedanhao())
                                .orderBy("id", false)
                );
                if (qiandao != null) {
                    yuyue.setQiandaoshijian(qiandao.getQiandaoshijian());
                }
            } catch (Exception ex) {
                logger.warn("获取签到信息失败，预约单号：{}，原因：{}", yuyue.getYuyuedanhao(), ex.getMessage());
            }

            // 关联最新签退时间与自习时长
            try {
                QiantuixinxiEntity qiantui = qiantuixinxiService.selectOne(
                        new EntityWrapper<QiantuixinxiEntity>()
                                .eq("yuyuedanhao", yuyue.getYuyuedanhao())
                                .orderBy("id", false)
                );
                if (qiantui != null) {
                    yuyue.setQiantuishijian(qiantui.getQiantuishijian());
                    yuyue.setZixishichang(qiantui.getZixishichang());
                }
            } catch (Exception ex) {
                logger.warn("获取签退信息失败，预约单号：{}，原因：{}", yuyue.getYuyuedanhao(), ex.getMessage());
            }

            return R.ok().put("data", yuyue);
        } catch (Exception e) {
            logger.error("查询预约详情失败，ID：{}", id, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能8：后端保存
    @RequestMapping("/save")
    public R save(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        try {
            yuyuexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
            yuyuexinxiService.insert(yuyuexinxi);
            logger.info("保存预约记录成功，ID：{}", yuyuexinxi.getId());
            return R.ok();
        } catch (Exception e) {
            logger.error("保存预约记录失败", e);
            return R.error("保存失败：" + e.getMessage());
        }
    }

    // 功能9：前端保存
    @RequestMapping("/add")
    public R add(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        Integer zuoweiId = yuyuexinxi.getZuowei();
        Integer zixishiId = yuyuexinxi.getZixishiid();
        yuyuexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        try {
            String yuyueStartStr = request.getParameter("yuyue_start");
            String yuyueEndStr = request.getParameter("yuyue_end");
            if (yuyuexinxi.getYuyueStart() == null && yuyueStartStr != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                yuyuexinxi.setYuyueStart(sdf.parse(yuyueStartStr));
            }
            if (yuyuexinxi.getYuyueEnd() == null && yuyueEndStr != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                yuyuexinxi.setYuyueEnd(sdf.parse(yuyueEndStr));
            }
            yuyuexinxiService.insert(yuyuexinxi);
            logger.info("新增预约记录成功，ID：{}", yuyuexinxi.getId());
            return R.ok();
        } catch (Exception e) {
            logger.error("新增预约失败，座位ID：{}，自习室ID：{}", zuoweiId, zixishiId, e);
            return R.error("数据库操作失败：" + e.getMessage());
        }
    }

    // 功能10：修改接口（支持签退状态+违纪标记更新，带权限校验和时间校验）
    @RequestMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public R update(@RequestBody Map<String, Object> updateData, HttpServletRequest request) {
        try {
            if (updateData == null || updateData.get("id") == null) {
                return R.error("参数缺失：id 必须提供");
            }
            Long id = Long.parseLong(updateData.get("id").toString());

            // 读取需要更新的字段
            String qiantuizhuangtai = updateData.get("qiantuizhuangtai") == null ? null : updateData.get("qiantuizhuangtai").toString();
            Integer weiguiFlag = null;
            if (updateData.get("weigui_flag") != null) {
                try {
                    weiguiFlag = Integer.parseInt(updateData.get("weigui_flag").toString());
                } catch (Exception ignore) {
                }
            } else if (updateData.get("weiguiFlag") != null) {
                try {
                    weiguiFlag = Integer.parseInt(updateData.get("weiguiFlag").toString());
                } catch (Exception ignore) {
                }
            }

            logger.info("收到 update 请求：id={}, qiantuizhuangtai={}, weigui_flag={}", id, qiantuizhuangtai, weiguiFlag);

            // 权限校验：学生只能修改自己的记录
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null) {
                    logger.warn("学生未登录，尝试修改预约记录ID：{}", id);
                    return R.error("请先登录");
                }
                YuyuexinxiEntity oldEntity = yuyuexinxiService.selectById(id);
                if (oldEntity == null || !currentXuehao.equals(oldEntity.getXuehao())) {
                    logger.warn("学生{}无权限修改预约记录ID：{}", currentXuehao, id);
                    return R.error("无权限修改该预约记录");
                }
            }

            // 校验记录是否存在
            YuyuexinxiEntity old = yuyuexinxiService.selectById(id);
            if (old == null) {
                logger.warn("更新失败：预约记录不存在 id={}", id);
                return R.error("预约记录不存在");
            }

            // 签退时间校验：不能在预约结束后签退
            if (qiantuizhuangtai != null && qiantuizhuangtai.equals("已签退")) {
                Date now = new Date();
                Date end = old.getYuyueEnd();
                if (end == null) {
                    return R.error("预约结束时间缺失，无法签退");
                }
                if (now.getTime() > end.getTime()) {
                    logger.warn("用户尝试在预约结束后标记已签退（id={}），拒绝并提示使用违纪标记", id);
                    return R.error("已超过预约结束时间，不能签退；请记录违纪（weigui_flag=1）");
                }
            }

            // 组装更新实体
            YuyuexinxiEntity updateEntity = new YuyuexinxiEntity();
            updateEntity.setId(id);
            if (qiantuizhuangtai != null) {
                updateEntity.setQiantuizhuangtai(qiantuizhuangtai);
            }
            if (weiguiFlag != null) {
                updateEntity.setWeiguiFlag(weiguiFlag);
            }

            yuyuexinxiService.updateById(updateEntity);
            logger.info("更新预约记录成功，ID：{}", id);
            return R.ok();
        } catch (Exception e) {
            logger.error("更新预约记录失败", e);
            return R.error("更新失败：" + e.getMessage());
        }
    }

    // 功能11：签到接口（带时间范围校验）
    @PostMapping("/signIn")
    @Transactional(rollbackFor = Exception.class)
    public R signIn(@RequestBody Map<String, Object> signInData, HttpServletRequest request) {
        try {
            // 校验预约ID
            Object yuyueIdObj = signInData.get("yuyueId");
            if (yuyueIdObj == null) {
                logger.warn("签到请求缺少 yuyueId");
                return R.error("请求缺少预约ID（yuyueId）");
            }
            Long yuyueId = Long.parseLong(yuyueIdObj.toString());

            // 校验预约记录是否存在
            YuyuexinxiEntity yuyue = yuyuexinxiService.selectById(yuyueId);
            if (yuyue == null) {
                logger.warn("签到失败：预约记录不存在，ID={}", yuyueId);
                return R.error("预约记录不存在");
            }

            // 校验签到时间是否在预约区间内
            Date start = yuyue.getYuyueStart();
            Date end = yuyue.getYuyueEnd();
            Date now = new Date();
            if (start == null || end == null) {
                logger.warn("签到失败：预约时间不完整，ID={}", yuyueId);
                return R.error("预约时间不完整，不能签到");
            }
            if (now.getTime() < start.getTime() || now.getTime() > end.getTime()) {
                logger.warn("签到失败：当前时间不在预约区间内，ID={}，start={}，end={}，now={}", yuyueId, start, end, now);
                return R.error("当前不在签到时间范围内，无法签到");
            }

            // 保存签到记录
            QiandaoxinxiEntity qiandao = new QiandaoxinxiEntity();
            qiandao.setMingcheng((String) signInData.get("mingcheng"));
            qiandao.setQiandaoshijian(new Date());
            qiandao.setQiandaobeizhu("正常签到");
            qiandao.setXuehao((String) signInData.get("xuehao"));
            qiandao.setShouji((String) signInData.get("shouji"));
            qiandao.setBanji((String) signInData.get("banji"));
            qiandao.setAddtime(new Date());
            qiandao.setYuyuedanhao((String) signInData.get("yuyuedanhao"));
            qiandaoxinxiService.insert(qiandao);

            // 更新预约签到状态
            YuyuexinxiEntity updateY = new YuyuexinxiEntity();
            updateY.setId(yuyueId);
            updateY.setQiandaozhuangtai("已签到");
            yuyuexinxiService.updateById(updateY);

            return R.ok("签到成功");
        } catch (Exception e) {
            logger.error("签到失败", e);
            return R.error("签到失败：" + e.getMessage());
        }
    }

    // 功能12：删除接口（带权限校验）
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null) {
                    logger.warn("学生未登录，尝试删除预约记录");
                    return R.error("请先登录");
                }
                // 校验每条记录的权限
                for (Long id : ids) {
                    YuyuexinxiEntity entity = yuyuexinxiService.selectById(id);
                    if (entity == null || !currentXuehao.equals(entity.getXuehao())) {
                        logger.warn("学生{}无权限删除预约记录ID：{}", currentXuehao, id);
                        return R.error("无权限删除该预约记录");
                    }
                }
            }

            yuyuexinxiService.deleteBatchIds(Arrays.asList(ids));
            logger.info("删除预约记录成功，IDs：{}", Arrays.toString(ids));
            return R.ok();
        } catch (Exception e) {
            logger.error("删除预约记录失败，IDs：{}", Arrays.toString(ids), e);
            return R.error("删除失败：" + e.getMessage());
        }
    }

    // 功能13：提醒接口
    @RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request,
                         @PathVariable("type") String type, @RequestParam Map<String, Object> map) {
        try {
            map.put("column", columnName);
            map.put("type", type);
            if (type.equals("2")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date remindStartDate = null;
                Date remindEndDate = null;
                if (map.get("remindstart") != null) {
                    Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
                    c.setTime(new Date());
                    c.add(Calendar.DAY_OF_MONTH, remindStart);
                    remindStartDate = c.getTime();
                    map.put("remindstart", sdf.format(remindStartDate));
                }
                if (map.get("remindend") != null) {
                    Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
                    c.setTime(new Date());
                    c.add(Calendar.DAY_OF_MONTH, remindEnd);
                    remindEndDate = c.getTime();
                    map.put("remindend", sdf.format(remindEndDate));
                }
            }

            Wrapper<YuyuexinxiEntity> wrapper = new EntityWrapper<YuyuexinxiEntity>();
            if (map.get("remindstart") != null) {
                ((EntityWrapper<YuyuexinxiEntity>) wrapper).ge(columnName, map.get("remindstart"));
            }
            if (map.get("remindend") != null) {
                ((EntityWrapper<YuyuexinxiEntity>) wrapper).le(columnName, map.get("remindend"));
            }

            // 学生只能查看自己的提醒
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                ((EntityWrapper<YuyuexinxiEntity>) wrapper).eq("xuehao", (String) request.getSession().getAttribute("username"));
            }

            int count = yuyuexinxiService.selectCount(wrapper);
            return R.ok().put("count", count);
        } catch (Exception e) {
            logger.error("查询预约提醒数量失败，列名：{}，类型：{}", columnName, type, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    // 功能14：提交分时预约（融合双方校验规则：日期范围+时间范围+跨天校验+30分钟粒度校验）
    @PostMapping("/submit")
    public R submitYuyue(
            @RequestBody YuyuexinxiEntity yuyuexinxi,
            HttpServletRequest request
    ) {
        try {
            // 身份校验：仅学生可预约
            String tableName = request.getSession().getAttribute("tableName") == null ?
                    "" : request.getSession().getAttribute("tableName").toString();
            if (!tableName.equals("xuesheng")) {
                logger.warn("非学生身份尝试提交预约，身份：{}", tableName);
                return R.error("仅学生可提交座位预约");
            }

            // 基础参数校验
            Integer zixishiid = yuyuexinxi.getZixishiid();
            Integer zuowei = yuyuexinxi.getZuowei();
            String xuehao = yuyuexinxi.getXuehao();
            String xingming = yuyuexinxi.getXingming();
            String shouji = yuyuexinxi.getShouji();
            Date yuyueStart = yuyuexinxi.getYuyueStart();
            Date yuyueEnd = yuyuexinxi.getYuyueEnd();
            String mingcheng = yuyuexinxi.getMingcheng();
            String beizhu = yuyuexinxi.getBeizhu(); // <- 前端填写的备注

            if (zixishiid == null || zuowei == null || xuehao == null || yuyueStart == null || yuyueEnd == null) {
                logger.warn("提交预约失败：必填参数为空，自习室ID：{}，座位ID：{}，学号：{}", zixishiid, zuowei, xuehao);
                return R.error("自习室、座位、学号、预约时间不能为空");
            }
            if (mingcheng == null || mingcheng.trim().isEmpty()) {
                logger.warn("提交预约失败：自习室名称为空，自习室ID：{}", zixishiid);
                return R.error("自习室名称不能为空");
            }

            // 校验1：预约日期范围（仅允许今天和明天）
            Calendar todayCal = Calendar.getInstance();
            todayCal.set(Calendar.HOUR_OF_DAY, 0);
            todayCal.set(Calendar.MINUTE, 0);
            todayCal.set(Calendar.SECOND, 0);
            todayCal.set(Calendar.MILLISECOND, 0);
            Date todayStart = todayCal.getTime();

            Calendar dayAfterTomorrowCal = Calendar.getInstance();
            dayAfterTomorrowCal.add(Calendar.DAY_OF_YEAR, 2);
            dayAfterTomorrowCal.set(Calendar.HOUR_OF_DAY, 0);
            dayAfterTomorrowCal.set(Calendar.MINUTE, 0);
            dayAfterTomorrowCal.set(Calendar.SECOND, 0);
            dayAfterTomorrowCal.set(Calendar.MILLISECOND, 0);
            Date dayAfterTomorrowStart = dayAfterTomorrowCal.getTime();

            if (yuyueStart.before(todayStart) || yuyueStart.compareTo(dayAfterTomorrowStart) >= 0) {
                logger.warn("提交预约失败：预约日期超出范围，开始时间：{}", yuyueStart);
                return R.error("预约日期仅允许今天和明天");
            }
            if (yuyueEnd.compareTo(dayAfterTomorrowStart) >= 0) {
                logger.warn("提交预约失败：预约结束时间超出允许范围（后天），结束时间：{}", yuyueEnd);
                return R.error("预约结束时间不能在后天或之后");
            }

            // 校验2：预约时间范围（8:00-22:00）+ 跨天校验
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(yuyueStart);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(yuyueEnd);

            // 禁止跨天
            if (startCal.get(Calendar.YEAR) != endCal.get(Calendar.YEAR) ||
                    startCal.get(Calendar.DAY_OF_YEAR) != endCal.get(Calendar.DAY_OF_YEAR)) {
                logger.warn("提交预约失败：预约跨天，开始时间：{}，结束时间：{}", yuyueStart, yuyueEnd);
                return R.error("预约不能跨越日期");
            }

            // 开始时间不能早于8:00
            int startHour = startCal.get(Calendar.HOUR_OF_DAY);
            if (startHour < 8) {
                logger.warn("提交预约失败：开始时间早于8点，开始时间：{}", yuyueStart);
                return R.error("预约开始时间不能早于08:00");
            }

            // 结束时间不能晚于22:00
            int endHour = endCal.get(Calendar.HOUR_OF_DAY);
            int endMinute = endCal.get(Calendar.MINUTE);
            int endSecond = endCal.get(Calendar.SECOND);
            if (endHour > 22 || (endHour == 22 && (endMinute > 0 || endSecond > 0))) {
                logger.warn("提交预约失败：结束时间晚于22点，结束时间：{}", yuyueEnd);
                return R.error("预约结束时间不能晚于22:00");
            }

            // 开始时间不能晚于22:00
            if (startHour >= 22) {
                logger.warn("提交预约失败：开始时间晚于22点，开始时间：{}", yuyueStart);
                return R.error("预约开始时间必须在08:00到22:00之间");
            }

            // 校验3：结束时间必须晚于开始时间
            if (yuyueEnd.before(yuyueStart) || yuyueEnd.equals(yuyueStart)) {
                logger.warn("提交预约失败：时间不合法，开始时间：{}，结束时间：{}", yuyueStart, yuyueEnd);
                return R.error("预约结束时间必须晚于开始时间");
            }

            // 校验4：30分钟粒度校验（开始/结束时间对齐+时长倍数+当天预约不早于当前时间）
            final int granularityMinutes = 30;
            Date now = new Date();

            // 判断是否为当天预约
            Calendar startCalCheck = Calendar.getInstance();
            startCalCheck.setTime(yuyueStart);
            Calendar todayZero = Calendar.getInstance();
            todayZero.set(Calendar.HOUR_OF_DAY, 0);
            todayZero.set(Calendar.MINUTE, 0);
            todayZero.set(Calendar.SECOND, 0);
            todayZero.set(Calendar.MILLISECOND, 0);
            boolean isSameDayAsNow = startCalCheck.get(Calendar.YEAR) == todayZero.get(Calendar.YEAR)
                    && startCalCheck.get(Calendar.DAY_OF_YEAR) == todayZero.get(Calendar.DAY_OF_YEAR);

            // 当天预约：开始时间不能早于当前时间（按粒度取整）
            long roundedNow = roundUpToGranularityMs(now.getTime(), granularityMinutes);
            if (isSameDayAsNow) {
                if (yuyueStart.getTime() < roundedNow) {
                    logger.warn("提交预约失败：开始时间早于允许的最早时间（按粒度取整），开始:{} roundingNow:{}", yuyueStart, new Date(roundedNow));
                    return R.error("预约开始时间必须在当前时间之后且按 30 分钟对齐（例如 17:30）");
                }
            }

            // 时间必须按30分钟对齐（分钟为30的倍数，秒/毫秒为0）
            if (!isAlignedToGranularity(yuyueStart, granularityMinutes) || !isAlignedToGranularity(yuyueEnd, granularityMinutes)) {
                logger.warn("提交预约失败：时间未按粒度对齐，开始:{} 结束:{}", yuyueStart, yuyueEnd);
                return R.error("预约开始/结束时间必须是 30 分钟的倍数（例如 08:00、08:30）");
            }

            // 时长必须为30分钟的倍数
            long durationMs = yuyueEnd.getTime() - yuyueStart.getTime();
            long granMs = granularityMinutes * 60L * 1000L;
            if (durationMs <= 0 || (durationMs % granMs) != 0) {
                logger.warn("提交预约失败：时长不是粒度倍数，时长ms: {}", durationMs);
                return R.error("预约时长需为 30 分钟的倍数");
            }

            // 提交预约（业务层校验座位可用性并插入）
            boolean success = yuyuexinxiService.submitYuyue(zixishiid, zuowei, xuehao, xingming, shouji, yuyueStart, yuyueEnd, mingcheng);
            if (success) {
                // ---- 新增处理：如果前端有传 beizhu，则尝试基于唯一的 yuyuedanhao 更新刚插入的记录的备注字段 ----
                try {
                    if (yuyuexinxi.getYuyuedanhao() != null && !yuyuexinxi.getYuyuedanhao().trim().isEmpty() && beizhu != null) {
                        EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<>();
                        ew.eq("yuyuedanhao", yuyuexinxi.getYuyuedanhao());
                        // 可多字段校验：加上 xuehao 和 zixishiid 可进一步确保命中正确记录（容错）
                        if (xuehao != null) ew.eq("xuehao", xuehao);
                        if (zixishiid != null) ew.eq("zixishiid", zixishiid);

                        YuyuexinxiEntity inserted = yuyuexinxiService.selectOne(ew);
                        if (inserted != null) {
                            inserted.setBeizhu(beizhu);
                            yuyuexinxiService.updateById(inserted);
                            logger.info("已为刚插入的预约记录更新备注，yuyuedanhao={}, beizhu={}", yuyuexinxi.getYuyuedanhao(), beizhu);
                        } else {
                            logger.warn("提交预约成功但未找到插入记录以更新备注，yuyuedanhao={}", yuyuexinxi.getYuyuedanhao());
                        }
                    }
                } catch (Exception ex) {
                    logger.warn("提交预约后尝试更新备注失败：{}", ex.getMessage());
                }

                logger.info("预约成功，自习室ID：{}，座位ID：{}，学号：{}", zixishiid, zuowei, xuehao);
                return R.ok("预约成功");
            } else {
                logger.warn("预约失败：座位已被占用，自习室ID：{}，座位ID：{}，时段：{} - {}", zixishiid, zuowei, yuyueStart, yuyueEnd);
                return R.error("该座位在指定时段已被预约，请更换时段或座位");
            }
        } catch (Exception e) {
            logger.error("提交分时预约失败", e);
            return R.error("预约失败：" + e.getMessage());
        }
    }

    // 辅助方法：向上取整到指定粒度（毫秒）
    private long roundUpToGranularityMs(long timeMs, int granularityMin) {
        long granMs = granularityMin * 60L * 1000L;
        return ((timeMs + granMs - 1) / granMs) * granMs;
    }

    // 辅助方法：判断时间是否按指定粒度对齐
    private boolean isAlignedToGranularity(Date d, int granularityMin) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int millis = c.get(Calendar.MILLISECOND);
        return (minutes % granularityMin == 0) && seconds == 0 && millis == 0;
    }

    // 功能15：查询座位可预约列表（来自yuyuexinxicontroller2）
    @IgnoreAuth
    @RequestMapping(value = "/seat/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R getSeatReservationList(
            @RequestParam Integer zixishiid,
            @RequestParam Integer zuowei,
            @RequestParam String date // 先用String接收，避免格式报错
    ) {
        try {
            // 解析日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date startOfDay = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date endOfDay = cal.getTime();

            // 查询该座位当天的预约记录
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<>();
            ew.eq("zixishiid", zixishiid)
                    .eq("zuowei", zuowei)
                    .ge("yuyue_start", startOfDay)
                    .le("yuyue_end", endOfDay);
            List<YuyuexinxiEntity> list = yuyuexinxiService.selectList(ew);

            // 计算可预约区间（30分钟粒度，10分钟缓冲）
            List<Map<String, Object>> intervals = Collections.emptyList();
            try {
                LocalDate ld = LocalDate.parse(date);
                intervals = yuyuexinxiService.getSeatAvailability(zixishiid, zuowei, ld, 30, 0, null);
            } catch (Exception ex) {
                logger.warn("计算可约区间失败，改为只返回预约列表，原因：{}", ex.getMessage());
                intervals = Collections.emptyList();
            }

            // 组装返回结果
            Map<String, Object> resp = new HashMap<>();
            resp.put("date", date);
            resp.put("bookings", list);
            resp.put("intervals", intervals);
            resp.put("openStart", "");
            resp.put("openEnd", "");
            return R.ok().put("data", resp);
        } catch (Exception e) {
            logger.error("查询座位预约异常", e);
            return R.error("获取可约时间失败：" + e.getMessage());
        }
    }
}