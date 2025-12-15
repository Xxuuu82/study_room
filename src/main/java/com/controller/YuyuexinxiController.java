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
 * 后端接口
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

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
            }
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime");
            PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));
            return R.ok().put("data", page);
        } catch (Exception e) {
            logger.error("查询预约分页失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, YuyuexinxiEntity yuyuexinxi,
                  HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (tableName.equals("xuesheng")) {
                yuyuexinxi.setXuehao((String) request.getSession().getAttribute("username"));
            }
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime");
            PageUtils page = yuyuexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yuyuexinxi), params), params));
            return R.ok().put("data", page);
        } catch (Exception e) {
            logger.error("查询预约列表失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(YuyuexinxiEntity yuyuexinxi) {
        try {
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime");
            ew.allEq(MPUtil.allEQMapPre(yuyuexinxi, "yuyuexinxi"));
            return R.ok().put("data", yuyuexinxiService.selectListView(ew));
        } catch (Exception e) {
            logger.error("查询预约基础列表失败", e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            EntityWrapper<YuyuexinxiEntity> ew = new EntityWrapper<YuyuexinxiEntity>();
            ew.setSqlSelect("id,yuyuedanhao,mingcheng,zixishiid,zuowei,qiandaozhuangtai,qiantuizhuangtai,yuyue_start,yuyue_end,beizhu,xuehao,xingming,shouji,addtime");
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

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            YuyuexinxiEntity yuyuexinxi = yuyuexinxiService.selectById(id);

            if (yuyuexinxi == null) {
                logger.warn("预约记录不存在，ID：{}", id);
                return R.error("预约记录不存在");
            }

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

    /**
     * 前端详情
     */
    // --- 仅展示修改后的 detail 方法体，替换原文件中对应方法 ---
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

            if (tableName.equals("xuesheng") && currentXuehao != null && !currentXuehao.equals(yuyue.getXuehao())) {
                logger.warn("学生{}尝试访问他人预约记录，记录ID：{}", currentXuehao, id);
                return R.error("无权限查看该预约记录");
            }

            // ===== 新增：从签到表取最新签到时间 =====
            try {
                QiandaoxinxiEntity qiandao = qiandaoxinxiService.selectOne(
                        new EntityWrapper<QiandaoxinxiEntity>()
                                .eq("yuyuedanhao", yuyue.getYuyuedanhao())
                                .orderBy("id", false) // 按 id 降序取最新
                );
                if (qiandao != null) {
                    yuyue.setQiandaoshijian(qiandao.getQiandaoshijian());
                }
            } catch (Exception ex) {
                // 日志记录但不影响详情展示
                logger.warn("获取签到信息失败，预约单号：{}，原因：{}", yuyue.getYuyuedanhao(), ex.getMessage());
            }

            // ===== 新增：从签退表取最新签退时间与自习时长 =====
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
    /**
     * 后端保存（恢复原有逻辑）
     */
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

    /**
     * 前端保存
     */
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

    /**
     * 修改（仅保留签退联动的更新逻辑）
     */
    @RequestMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public R update(@RequestBody Map<String, Object> updateData, HttpServletRequest request) {
        try {
            Long id = Long.parseLong(updateData.get("id").toString());
            String qiantuizhuangtai = updateData.get("qiantuizhuangtai") == null ? "" : updateData.get("qiantuizhuangtai").toString();

            logger.info("更新预约签退状态：ID={}, 签退状态={}", id, qiantuizhuangtai);

            YuyuexinxiEntity yuyuexinxi = new YuyuexinxiEntity<>();
            yuyuexinxi.setId(id);
            if (!qiantuizhuangtai.isEmpty()) {
                yuyuexinxi.setQiantuizhuangtai(qiantuizhuangtai);
            }

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

            if (id == null) {
                logger.warn("更新预约失败：ID不能为空");
                return R.error("预约ID不能为空");
            }

            yuyuexinxiService.updateById(yuyuexinxi);
            logger.info("更新预约记录成功，ID：{}", id);
            return R.ok();
        } catch (Exception e) {
            logger.error("更新预约记录失败", e);
            return R.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 签到事务接口
     */
    @PostMapping("/signIn")
    @Transactional(rollbackFor = Exception.class)
    public R signIn(@RequestBody Map<String, Object> signInData, HttpServletRequest request) {
        try {
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

            YuyuexinxiEntity yuyue = new YuyuexinxiEntity<>();
            yuyue.setId(Long.parseLong(signInData.get("yuyueId").toString()));
            yuyue.setQiandaozhuangtai("已签到");
            yuyuexinxiService.updateById(yuyue);

            return R.ok("签到成功");
        } catch (Exception e) {
            logger.error("签到失败", e);
            return R.error("签到失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     */
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

    /**
     * 提醒接口
     */
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

    /**
     * 提交分时预约
     */
    @PostMapping("/submit")
    public R submitYuyue(
            @RequestBody YuyuexinxiEntity yuyuexinxi,
            HttpServletRequest request
    ) {
        try {
            String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (!tableName.equals("xuesheng")) {
                logger.warn("非学生身份尝试提交预约，身份：{}", tableName);
                return R.error("仅学生可提交座位预约");
            }

            Integer zixishiid = yuyuexinxi.getZixishiid();
            Integer zuowei = yuyuexinxi.getZuowei();
            String xuehao = yuyuexinxi.getXuehao();
            String xingming = yuyuexinxi.getXingming();
            String shouji = yuyuexinxi.getShouji();
            Date yuyueStart = yuyuexinxi.getYuyueStart();
            Date yuyueEnd = yuyuexinxi.getYuyueEnd();
            String mingcheng = yuyuexinxi.getMingcheng();

            if (zixishiid == null || zuowei == null || xuehao == null || yuyueStart == null || yuyueEnd == null) {
                logger.warn("提交预约失败：必填参数为空，自习室ID：{}，座位ID：{}，学号：{}", zixishiid, zuowei, xuehao);
                return R.error("自习室、座位、学号、预约时间不能为空");
            }
            if (mingcheng == null || mingcheng.trim().isEmpty()) {
                logger.warn("提交预约失败：自习室名称为空，自习室ID：{}", zixishiid);
                return R.error("自习室名称不能为空");
            }

            if (yuyueEnd.before(yuyueStart) || yuyueEnd.equals(yuyueStart)) {
                logger.warn("提交预约失败：时间不合法，开始时间：{}，结束时间：{}", yuyueStart, yuyueEnd);
                return R.error("预约结束时间必须晚于开始时间");
            }

            boolean success = yuyuexinxiService.submitYuyue(zixishiid, zuowei, xuehao, xingming, shouji, yuyueStart, yuyueEnd, mingcheng);
            if (success) {
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

    /**
     * 查询指定座位的已预约时段
     */
    @GetMapping("/seat/list")
    public R getSeatYuyueList(
            @RequestParam Integer zixishiid,
            @RequestParam Integer zuowei
    ) {
        try {
            java.util.List<YuyuexinxiEntity> list = yuyuexinxiService.getSeatYuyueList(zixishiid, zuowei);
            return R.ok().put("data", list);
        } catch (Exception e) {
            logger.error("查询座位预约时段失败，自习室ID：{}，座位ID：{}", zixishiid, zuowei, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

}