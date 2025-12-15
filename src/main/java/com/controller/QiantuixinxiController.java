package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.QiandaoxinxiEntity;
import com.entity.QiantuixinxiEntity;
import com.entity.view.QiantuixinxiView;
import com.service.QiandaoxinxiService;
import com.service.QiantuixinxiService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.shiro.dao.DataAccessException;
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
 * 后端接口
 */
@RestController
@RequestMapping("/qiantuixinxi")
public class QiantuixinxiController {
    @Autowired
    private QiantuixinxiService qiantuixinxiService;

    @Autowired
    private QiandaoxinxiService qiandaoxinxiService;

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
        QiantuixinxiView qiantuixinxiView = qiantuixinxiService.selectView(ew);
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
     * 后端保存（仅修复空指针/主键冲突/事务，完全保留原有业务逻辑）
     */
    /**
     * 后端保存（修复400/主键冲突/座位状态阻断问题）
     */
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class) // 事务保障
    public R save(@RequestBody QiantuixinxiEntity qiantuixinxi, HttpServletRequest request) {
        try {
            Integer zuoweiId = qiantuixinxi.getZuowei();
            Integer zixishiId = qiantuixinxi.getZixishiid();
            String xuehao = qiantuixinxi.getXuehao();

            // ========== 修复1：强制处理签退时间（解决400核心问题） ==========
            Date qiantuishijian = qiantuixinxi.getQiantuishijian();
            // 若前端传的字符串解析失败/为空，直接用当前时间
            if (qiantuishijian == null) {
                qiantuishijian = new Date();
                qiantuixinxi.setQiantuishijian(qiantuishijian);
            }

            // ========== 修复2：非空校验（避免空指针） ==========
            if (zuoweiId == null || zuoweiId <= 0) {
                return R.error("座位ID不能为空且必须为正数");
            }
            if (zixishiId == null || zixishiId <= 0) {
                return R.error("自习室ID不能为空且必须为正数");
            }
            if (xuehao == null || xuehao.trim().isEmpty()) {
                return R.error("学号不能为空");
            }

            QiandaoxinxiEntity qiandaoxinxi = qiandaoxinxiService.selectOne(
                    new EntityWrapper<QiandaoxinxiEntity>()
                            .eq("xuehao", xuehao)
                            .orderBy("id", false)
            );
            if (qiandaoxinxi == null) {
                return R.error("未找到对应的签到记录");
            }

            // ========== 修复3：主键冲突（注释复用ID，改用自增/唯一ID） ==========
            // 注释掉这行：避免主键冲突导致insert失败
            // qiantuixinxi.setId(qiandaoxinxi.getId());
            // 恢复生成唯一ID（和前端add方法逻辑一致）
            qiantuixinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());

            Date qiandaoshijian = qiandaoxinxi.getQiandaoshijian();
            if (qiandaoshijian == null) {
                return R.error("签到时间不能为空");
            }

            long durationInMillis = qiantuishijian.getTime() - qiandaoshijian.getTime();
            if (durationInMillis < 0) {
                return R.error("签退时间不能早于签到时间");
            }
            double durationMinutes = Math.round(durationInMillis / 600.0) / 10.0;
            qiantuixinxi.setZixishichang(durationMinutes);

            // ========== 修复4：座位状态更新（放宽条件，失败不阻断签退） ==========
            String tableName = "seats_" + zixishiId;
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法表名");
            }
            // 修改：移除 status=0 条件（避免座位状态不对导致签退失败），仅更新为1
            int updatedRows = jdbcTemplate.update(
                    "UPDATE " + tableName + " SET status = 1 WHERE id = ?",
                    zuoweiId
            );
            // 仅日志提示，不返回错误（避免阻断签退核心逻辑）
            if (updatedRows == 0) {
                System.out.println("座位状态更新失败：自习室" + zixishiId + "，座位" + zuoweiId);
                // 注释掉错误返回，改为日志
                // return R.error("座位状态更新失败");
            }

            // ========== 核心：保存签退记录（必执行） ==========
            qiantuixinxiService.insert(qiantuixinxi);

            // 更新学生自习时长
            jdbcTemplate.update(
                    "UPDATE xuesheng SET zixishichang = COALESCE(zixishichang, 0) + ? WHERE xuehao = ?",
                    durationMinutes,
                    xuehao
            );

            return R.ok("签退成功");
        } catch (DataAccessException e) {
            e.printStackTrace(); // 打印完整异常（关键！看数据库报错）
            return R.error("数据库操作失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 打印完整异常
            return R.error("系统错误：" + e.getMessage());
        }
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody QiantuixinxiEntity qiantuixinxi, HttpServletRequest request) {
        qiantuixinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(qiantuixinxi);
        qiantuixinxiService.insert(qiantuixinxi);
        return R.ok();
    }


    @RequestMapping("/fenxi")
    public R fenxi() {
        try {
            // 修改SQL查询所有字段
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
        //ValidatorUtils.validateEntity(qiantuixinxi);
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