package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.QiandaoxinxiEntity;
import com.entity.QiantuixinxiEntity;
import com.entity.YuyuexinxiEntity;
import com.entity.view.YuyuexinxiView;
import com.service.YuyuexinxiService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.shiro.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 预约信息
 * 后端接口（修复权限问题版）
 */
@RestController
@RequestMapping("/yuyuexinxi")
public class YuyuexinxiController {
    private static final Logger logger = LoggerFactory.getLogger(YuyuexinxiController.class);

    @Autowired
    private YuyuexinxiService yuyuexinxiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private QiantuixinxiController qiandaoxinxiService;

    /**
     * 后端列表（保留原有学号过滤逻辑）
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            // 学生只能查自己的预约列表
            yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
        }
        EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();

        PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表（修复：移除匿名访问，添加学号过滤）
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        // 校验登录状态，获取用户身份
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            // 学生只能查自己的预约列表
            yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
        }

        EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
        PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表（基础查询，保留原有逻辑）
     */
    @RequestMapping("/lists")
    public R list(YuyuexinxiEntity yuyuexinxi) {
        EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
        ew.allEq(MPUtil.allEQMapPre(yuyuexinxi, "yuyuexinxi"));
        return R.ok().put("data", yuyuexinxiService.selectListView(ew));
    }

    /**
     * 查询（基础查询，保留原有逻辑）
     */
    @RequestMapping("/query")
    public R query(YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
        ew.allEq(MPUtil.allEQMapPre(yuyuexinxi, "yuyuexinxi"));

        // 学生身份添加学号过滤
        if (tableName.equals("xuesheng")) {
            ew.eq("xuehao", (String) request.getSession().getAttribute("username"));
        }

        YuyuexinxiView yuyuexinxiView = yuyuexinxiService.selectView(ew);
        return R.ok("查询预约信息成功").put("data", yuyuexinxiView);
    }

    /**
     * 后端详情（核心修复：添加学号权限校验）
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            // 获取当前登录用户身份
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            YuyuexinxiEntity yuyuexinxi = yuyuexinxiService.selectById(id);

            // 记录不存在直接返回
            if (yuyuexinxi == null) {
                logger.warn("预约记录不存在，ID：{}", id);
                return R.error("预约记录不存在");
            }

            // 学生身份：强制校验学号
            if (tableName.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                // 校验学号是否匹配
                if (!currentXuehao.equals(yuyuexinxi.getXuehao())) {
                    logger.warn("学生{}尝试访问他人预约记录，记录ID：{}", currentXuehao, id);
                    return R.error("无权限查看该预约记录");
                }
            }

            // 管理员/其他身份：可查看所有记录（如需限制可自行扩展）
            return R.ok().put("data", yuyuexinxi);
        } catch (Exception e) {
            logger.error("查询预约详情失败，ID：{}", id, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 前端详情（核心修复：移除匿名访问，添加学号校验）
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable Long id) {
        // 1. 查询预约主表数据
        YuyuexinxiEntity yuyue = yuyuexinxiService.getById(id);
        if (yuyue == null) {
            return R.error("预约记录不存在");
        }

        // 2. 根据预约单号，关联查询签到表的签到时间
        String yuyueDanHao = yuy.getYuyuedanhao(); // 获取当前预约的单号
        QiandaoxinxiEntity qiandao = qiandaoxinxiService.getByYuyueDanHao(yuyueDanHao);
        if (qiandao != null) {
            yuyue.setQiandaozhuangtai("已签到"); // 签到状态
            yuyue.setQiandaoshijian(qiandao.getQiandaoshijian()); // 关联签到表的qiandaoshijian
        } else {
            yuyue.setQiandaozhuangtai("未签到");
            yuyue.setQiandaoshijian(null);
        }

        // 3. 根据预约单号，关联查询签退表的签退时间、自习时长
        QiantuixinxiEntity qiantui = qiantuixinxiService.getByYuyueDanHao(yuyueDanHao);
        if (qiantu != null) {
            yuyue.setQiantuizhuangtai("已签退"); // 签退状态
            yuyue.setQiantuishijian(qiantu.getQiantuishijian()); // 关联签退表的qiantuishijian
            yuyue.setZixishichang(qiantu.getZixishichang()); // 关联签退表的zixishichang
        } else {
            yuyue.setQiantuizhuangtai("未签退");
            yuyue.setQiantuishijian(null);
            yuyue.setZixishichang(0.0); // 未签退时自习时长为0
        }

        return R.ok().put("data", yuyue);
    }

    /**
     * 后端保存（保留原有逻辑）
     */
    @RequestMapping("/save")
    public R save(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        yuyuexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(yuyuexinxi);
        yuyuexinxiService.insert(yuyuexinxi);
        return R.ok();
    }

    /**
     * 前端保存（移除全局锁定座位逻辑）
     */
    /**
     * 前端保存（修复：处理时间字段转换，适配前端传参）
     */
    @RequestMapping("/add")
    public R add(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        Integer zuoweiId = yuyuexinxi.getZuowei();
        Integer zixishiId = yuyuexinxi.getZixishiid();

        yuyuexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(yuyuexinxi);

        try {
            // ========== 核心修复：处理前端字符串时间转Date ==========
            // 1. 从请求参数中获取前端传的字符串格式时间（yuyue_start/yuyue_end）
            String yuyueStartStr = request.getParameter("yuyue_start");
            String yuyueEndStr = request.getParameter("yuyue_end");
            // 2. 手动转换为Date类型（兼容前端JSON传参）
            if (yuyuexinxi.getYuyueStart() == null && yuyueStartStr != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                yuyuexinxi.setYuyueStart(sdf.parse(yuyueStartStr));
            }
            if (yuyuexinxi.getYuyueEnd() == null && yuyueEndStr != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                yuyuexinxi.setYuyueEnd(sdf.parse(yuyueEndStr));
            }

            // ========== 可选：调用Service的冲突检测逻辑 ==========
            // 若需要检测座位冲突，可调用submitYuyue方法
            // boolean success = yuyuexinxiService.submitYuyue(
            //     zixishiId, zuoweiId,
            //     yuyuexinxi.getXuehao(), yuyuexinxi.getXingming(), yuyuexinxi.getShouji(),
            //     yuyuexinxi.getYuyueStart(), yuyuexinxi.getYuyueEnd()
            // );
            // if (!success) {
            //     return R.error("该座位在指定时段已被预约");
            // }

            // 插入预约记录
            yuyuexinxiService.insert(yuyuexinxi);
            return R.ok();
        } catch (Exception e) {
            logger.error("新增预约失败", e);
            // 数据库错误自动触发事务回滚
            return R.error("数据库操作失败：" + e.getMessage());
        }
    }

    /**
     * 修改（保留原有逻辑）
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        // 学生只能修改自己的预约记录
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            String currentXuehao = (String) request.getSession().getAttribute("username");
            YuyuexinxiEntity oldEntity = yuyuexinxiService.selectById(yuyuexinxi.getId());
            if (oldEntity == null || !currentXuehao.equals(oldEntity.getXuehao())) {
                return R.error("无权限修改该预约记录");
            }
        }

        //ValidatorUtils.validateEntity(yuyuexinxi);
        yuyuexinxiService.updateById(yuyuexinxi);//全部更新
        return R.ok();
    }

    /**
     * 删除（保留原有逻辑，添加权限校验）
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        // 学生只能删除自己的预约记录
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            String currentXuehao = (String) request.getSession().getAttribute("username");
            // 校验要删除的记录是否属于当前学生
            for (Long id : ids) {
                YuyuexinxiEntity entity = yuyuexinxiService.selectById(id);
                if (entity == null || !currentXuehao.equals(entity.getXuehao())) {
                    return R.error("无权限删除该预约记录");
                }
            }
        }

        yuyuexinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 提醒接口（保留原有逻辑）
     */
    @RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request,
                         @PathVariable("type") String type, @RequestParam Map<String, Object> map) {
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
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }

        String tableName = request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            wrapper.eq("xuehao", (String) request.getSession().getAttribute("username"));
        }

        int count = yuyuexinxiService.selectCount(wrapper);
        return R.ok().put("count", count);
    }

    // ===================== 新增核心接口：分时预约相关 =====================
    /**
     * 提交分时预约（替代原有锁定整个座位的逻辑，核心接口）
     */
    /**
     * 提交分时预约（适配前端传整个ruleForm对象，兼容原有逻辑）
     */
    @PostMapping("/submit")
    public R submitYuyue(
            @RequestBody YuyuexinxiEntity yuyuexinxi, // 直接接收前端的ruleForm对象
            HttpServletRequest request
    ) {
        try {
            // 1. 基础权限校验：仅学生可提交预约
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (!tableName.equals("xuesheng")) {
                return R.error("仅学生可提交座位预约");
            }

            // 2. 提取核心参数（新增mingcheng字段）
            Integer zixishiid = yuyuexinxi.getZixishiid();
            Integer zuowei = yuyuexinxi.getZuowei();
            String xuehao = yuyuexinxi.getXuehao();
            String xingming = yuyuexinxi.getXingming();
            String shouji = yuyuexinxi.getShouji();
            Date yuyueStart = yuyuexinxi.getYuyueStart();
            Date yuyueEnd = yuyuexinxi.getYuyueEnd();
            String mingcheng = yuyuexinxi.getMingcheng(); // 新增：获取自习室名称

            // 3. 校验参数非空（新增mingcheng非空校验）
            if (zixishiid == null || zuowei == null || xuehao == null || yuyueStart == null || yuyueEnd == null) {
                return R.error("自习室、座位、学号、预约时间不能为空");
            }
            if (mingcheng == null || mingcheng.trim().isEmpty()) { // 新增：名称非空校验
                return R.error("自习室名称不能为空");
            }

            // 4. 时间合法性校验：结束时间必须晚于开始时间
            if (yuyueEnd.before(yuyueStart) || yuyueEnd.equals(yuyueStart)) {
                return R.error("预约结束时间必须晚于开始时间");
            }

            // 5. 调用业务层提交预约（新增mingcheng参数传递）
            boolean success = yuyuexinxiService.submitYuyue(zixishiid, zuowei, xuehao, xingming, shouji, yuyueStart, yuyueEnd, mingcheng);
            if (success) {
                return R.ok("预约成功");
            } else {
                return R.error("该座位在指定时段已被预约，请更换时段或座位");
            }
        } catch (Exception e) {
            logger.error("提交分时预约失败", e);
            return R.error("预约失败：" + e.getMessage());
        }
    }

    /**
     * 查询指定座位的已预约时段（供前端展示空闲/占用时段）
     */
    @GetMapping("/seat/list")
    public R getSeatYuyueList(
            @RequestParam Integer zixishiid,
            @RequestParam Integer zuowei
    ) {
        try {
            // 调用业务层查询座位的已预约时段
            java.util.List<YuyuexinxiEntity> list = yuyuexinxiService.getSeatYuyueList(zixishiid, zuowei);
            return R.ok().put("data", list);
        } catch (Exception e) {
            logger.error("查询座位预约时段失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

}