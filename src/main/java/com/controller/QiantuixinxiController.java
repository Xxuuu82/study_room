package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.QiandaoxinxiEntity;
import com.entity.QiantuixinxiEntity;
import com.entity.YuyuexinxiEntity;
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
import java.util.*;

/**
 * 签退信息
 * 后端接口（签退保存加入预约/时间校验与违纪记录逻辑）
 */
@RestController
@RequestMapping("/qiantuixinxi")
public class QiantuixinxiController {
    // 添加 logger 声明（不要导入别的 logger 字段）
    private static final Logger logger = LoggerFactory.getLogger(QiantuixinxiController.class);

    @Autowired
    private QiantuixinxiService qiantuixinxiService;

    @Autowired
    private QiandaoxinxiService qiandaoxinxiService;

    // 新增注入：用于校验/更新对应预约记录
    @Autowired
    private YuyuexinxiService yuyuexinxiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, QiantuixinxiEntity qiantuixinxi,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date qiantuishijianstart,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date qiantuishijianend,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            qiantuixinxi.setXuehao((String) request.getSession().getAttribute("username"));
        }
        EntityWrapper<QiantuixinxiEntity> ew = new EntityWrapper<QiantuixinxiEntity>();
        if (qiantuishijianstart != null) ew.ge("qiantuishijian", qiantuishijianstart);
        if (qiantuishijianend != null) ew.le("qiantuishijian", qiantuishijianend);

        PageUtils page = qiantuixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qiantuixinxi), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, QiantuixinxiEntity qiantuixinxi,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date qiantuishijianstart,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date qiantuishijianend,
                  HttpServletRequest request) {
        EntityWrapper<QiantuixinxiEntity> ew = new EntityWrapper<QiantuixinxiEntity>();
        if (qiantuishijianstart != null) ew.ge("qiantuishijian", qiantuishijianstart);
        if (qiantuishijianend != null) ew.le("qiantuishijian", qiantuishijianend);

        PageUtils page = qiantuixinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qiantuixinxi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(QiantuixinxiEntity qiantuixinxi) {
        EntityWrapper<QiantuixinxiEntity> ew = new EntityWrapper<QiantuixinxiEntity>();
        ew.allEq(MPUtil.allEQMapPre(qiantuixinxi, "qiantuixinxi"));
        return R.ok().put("data", qiantuixinxiService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(QiantuixinxiEntity qiantuixinxi) {
        EntityWrapper<QiantuixinxiEntity> ew = new EntityWrapper<QiantuixinxiEntity>();
        ew.allEq(MPUtil.allEQMapPre(qiantuixinxi, "qiantuixinxi"));
        com.entity.view.QiantuixinxiView qiantuixinxiView = qiantuixinxiService.selectView(ew);
        return R.ok("查询签到信息成功").put("data", qiantuixinxiView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        QiantuixinxiEntity qiantuixinxi = qiantuixinxiService.selectById(id);
        return R.ok().put("data", qiantuixinxi);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        QiantuixinxiEntity qiantuixinxi = qiantuixinxiService.selectById(id);
        return R.ok().put("data", qiantuixinxi);
    }

    // ========== 新增：根据预约单号查询签退记录 ==========
    @IgnoreAuth
    @GetMapping("/getByYuyueDanHao/{yuyuedanhao}")
    public R getByYuyueDanHao(@PathVariable("yuyuedanhao") String yuyuedanhao) {
        QiantuixinxiEntity qiantuixinxi = qiantuixinxiService.getByYuyueDanHao(yuyuedanhao);
        return R.ok().put("data", qiantuixinxi);
    }

    /**
     * 后端保存（修复并加入预约/时间校验）
     */
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class) // 事务保障
    public R save(@RequestBody QiantuixinxiEntity qiantuixinxi, HttpServletRequest request) {
        try {
            Integer zuoweiId = qiantuixinxi.getZuowei();
            Integer zixishiId = qiantuixinxi.getZixishiid();
            String xuehao = qiantuixinxi.getXuehao();
            String yuyuedanhao = qiantuixinxi.getYuyuedanhao();

            // ========== 强制处理签退时间（若前端未传则使用当前时间） ==========
            Date qiantuishijian = qiantuixinxi.getQiantuishijian();
            if (qiantuishijian == null) {
                qiantuishijian = new Date();
                qiantuixinxi.setQiantuishijian(qiantuishijian);
            }

            // 非空校验
            if (zuoweiId == null || zuoweiId <= 0) {
                return R.error("座位ID不能为空且必须为正数");
            }
            if (zixishiId == null || zixishiId <= 0) {
                return R.error("自习室ID不能为空且必须为正数");
            }
            if (xuehao == null || xuehao.trim().isEmpty()) {
                return R.error("学号不能为空");
            }
            if (yuyuedanhao == null || yuyuedanhao.trim().isEmpty()) {
                return R.error("预约单号不能为空");
            }

            // 查找对应预约记录（按预约单号）
            YuyuexinxiEntity yuyue = yuyuexinxiService.selectOne(new EntityWrapper<YuyuexinxiEntity>().eq("yuyuedanhao", yuyuedanhao));
            if (yuyue == null) {
                return R.error("未找到对应的预约记录，无法签退");
            }

            // 若预约尚未签到，则不能签退
            if (!"已签到".equals(yuyue.getQiandaozhuangtai())) {
                return R.error("该预约尚未签到，不能进行签退");
            }

            // 检查是否已经超过预约结束时间
            Date yuyueEnd = yuyue.getYuyueEnd();
            if (yuyueEnd == null) {
                return R.error("预约结束时间缺失，无法签退");
            }

            Date now = new Date();
            if (now.getTime() > yuyueEnd.getTime()) {
                // 超时：不要保存签退记录，改为记录违纪（weigui_flag=1）
                YuyuexinxiEntity updateWeigui = new YuyuexinxiEntity();
                updateWeigui.setId(yuyue.getId());
                updateWeigui.setWeiguiFlag(1);
                yuyuexinxiService.updateById(updateWeigui);
                return R.error("已超过预约结束时间，不能签退；已记录违纪（weigui_flag=1）");
            }

            // ====== 到这里表示允许签退（当前时间 <= 预约结束时间） ======
            // 找到对应的最新签到记录以计算时长
            QiandaoxinxiEntity qiandaoxinxi = qiandaoxinxiService.selectOne(
                    new EntityWrapper<QiandaoxinxiEntity>()
                            .eq("xuehao", xuehao)
                            .orderBy("id", false)
            );
            if (qiandaoxinxi == null) {
                return R.error("未找到对应的签到记录，无法计算自习时长");
            }
            Date qiandaoshijian = qiandaoxinxi.getQiandaoshijian();
            if (qiandaoshijian == null) {
                return R.error("签到时间缺失，无法签退");
            }

            // 计算自习时长（分钟，保留一位小数）
            long durationInMillis = qiantuishijian.getTime() - qiandaoshijian.getTime();
            if (durationInMillis < 0) {
                return R.error("签退时间不能早于签到时间");
            }
            double minutes = durationInMillis / 60000.0;
            double durationMinutes = Math.round(minutes * 10.0) / 10.0;
            qiantuixinxi.setZixishichang(durationMinutes);

            // 主键使用唯一生成（避免冲突）
            qiantuixinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());

            // 更新座位状态（尝试更新，不阻断签退主流程）
            String tableName = "seats_" + zixishiId;
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法表名");
            }
            try {
                jdbcTemplate.update(
                        "UPDATE " + tableName + " SET status = 1 WHERE id = ?",
                        zuoweiId
                );
            } catch (Exception e) {
                // 只记录，不阻断签退
                logger.warn("更新座位状态失败：自习室{} 座位{}，原因：{}", zixishiId, zuoweiId, e.getMessage());
            }

            // 保存签退记录
            qiantuixinxiService.insert(qiantuixinxi);

            // 更新学生累计自习时长（容错写法）
            try {
                jdbcTemplate.update(
                        "UPDATE xuesheng SET zixishichang = COALESCE(zixishichang, 0) + ? WHERE xuehao = ?",
                        durationMinutes,
                        xuehao
                );
            } catch (Exception e) {
                logger.warn("更新学生累计自习时长失败，学号：{}，原因：{}", xuehao, e.getMessage());
            }

            return R.ok("签退成功");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return R.error("数据库操作失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统错误：" + e.getMessage());
        }
    }

    /**
     * 前端保存（保留）
     */
    @RequestMapping("/add")
    public R add(@RequestBody QiantuixinxiEntity qiantuixinxi, HttpServletRequest request) {
        qiantuixinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        qiantuixinxiService.insert(qiantuixinxi);
        return R.ok();
    }

    @RequestMapping("/fenxi")
    public R fenxi() {
        try {
            String sql = "SELECT * FROM xuesheng ORDER BY xuehao";
            List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
            return R.ok().put("data", data);
        } catch (Exception e) {
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody QiantuixinxiEntity qiantuixinxi, HttpServletRequest request) {
        qiantuixinxiService.updateById(qiantuixinxi);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        qiantuixinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 提醒接口
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

        Wrapper<QiantuixinxiEntity> wrapper = new EntityWrapper<QiantuixinxiEntity>();
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

        int count = qiantuixinxiService.selectCount(wrapper);
        return R.ok().put("count", count);
    }
}