package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.QuxiaoyuyueEntity;
import com.entity.YuyuexinxiEntity;
import com.entity.view.QuxiaoyuyueView;
import com.service.QuxiaoyuyueService;
import com.service.YuyuexinxiService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 取消预约
 */
@RestController
@RequestMapping("/quxiaoyuyue")
public class QuxiaoyuyueController {
    @Autowired
    private QuxiaoyuyueService quxiaoyueService;

    @Autowired
    private YuyuexinxiService yuyuexinxiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 日期格式化工具（解决字符串转Date）
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 后端列表
     */
    /**
     * 后端列表（不强制指定字段，恢复自动查询）
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, QuxiaoyuyueEntity quxiaoyuyue,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            quxiaoyuyue.setXuehao((String) request.getSession().getAttribute("username"));
        }
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();

        // ========== 核心修复：手动指定数据库列名（带下划线） ==========
        ew.setSqlSelect(
                "id", "addtime", "yuyuedanhao", "mingcheng",
                "yuyue_start", "yuyue_end", // 直接写数据库的下划线列名
                "quxiaoshijian", "quxiaoyuanyin", "xuehao", "xingming"
        );

        PageUtils page = quxiaoyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quxiaoyuyue), params), params));

        // 调试：打印查询的SQL（需开启MyBatis日志）
        System.out.println("取消预约列表查询SQL：" + ew.getSqlSegment());

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, QuxiaoyuyueEntity quxiaoyuyue,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            quxiaoyuyue.setXuehao((String) request.getSession().getAttribute("username"));
        }
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();

        PageUtils page = quxiaoyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quxiaoyuyue), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(QuxiaoyuyueEntity quxiaoyuyue) {
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();
        ew.allEq(MPUtil.allEQMapPre(quxiaoyuyue, "quxiaoyuyue"));
        return R.ok().put("data", quxiaoyueService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(QuxiaoyuyueEntity quxiaoyuyue) {
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();
        ew.allEq(MPUtil.allEQMapPre(quxiaoyuyue, "quxiaoyuyue"));
        QuxiaoyuyueView quxiaoyuyueView = quxiaoyueService.selectView(ew);
        return R.ok("查询取消预约成功").put("data", quxiaoyuyueView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        QuxiaoyuyueEntity quxiaoyuyue = quxiaoyueService.selectById(id);
        return R.ok().put("data", quxiaoyuyue);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        QuxiaoyuyueEntity quxiaoyuyue = quxiaoyueService.selectById(id);
        return R.ok().put("data", quxiaoyuyue);
    }

    /**
     * 后端保存（全量修复）
     */
    /**
     * 后端保存（按时间段管控座位状态）
     */
    @RequestMapping("/save")
    @Transactional // 事务保证原子性
    public R save(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        try {
            // ========== 1. 核心字段判空 ==========
            Integer zuoweiId = quxiaoyuyue.getZuowei();
            Integer zixishiId = quxiaoyuyue.getZixishiid();
            Long crossrefid = quxiaoyuyue.getCrossrefid();

            if (zixishiId == null || zixishiId <= 0) {
                return R.error("自习室ID不能为空且必须为正整数");
            }
            if (zuoweiId == null || zuoweiId <= 0) {
                return R.error("座位ID不能为空且必须为正整数");
            }
            if (crossrefid == null) {
                return R.error("原预约记录ID不能为空");
            }

            // ========== 2. 生成主键ID + 设置取消时间 ==========
            quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
            quxiaoyuyue.setQuxiaoshijian(new Date());

            // ========== 3. 强制解锁座位（移除status=0条件，按时间段管控） ==========
            String tableName = "seats_" + zixishiId;
            // 严格校验表名合法性（仅允许数字）
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法的自习室ID，表名不合法");
            }
            // 核心修改：移除 WHERE status=0，强制将座位置为可预约（status=1）
            // 无论当前status是0/1，都更新为1（解锁），适配时间段管控逻辑
            jdbcTemplate.update(
                    "UPDATE " + tableName + " SET status = 1 WHERE id = ?",
                    zuoweiId
            );
            System.out.println("座位强制解锁成功：自习室ID=" + zixishiId + ", 座位ID=" + zuoweiId);

            // ========== 4. 校验原预约记录并同步时间 ==========
            YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(crossrefid);
            if (oldYuyue == null) {
                return R.error("原预约记录不存在，无法取消");
            }

            // 学生权限校验（仅能取消自己的预约）
            String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (currentTable.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null || !currentXuehao.equals(oldYuyue.getXuehao())) {
                    return R.error("无权限取消他人的预约记录");
                }
            }

            // 同步原预约时间（空值防护）
            quxiaoyuyue.setYuyue_start(oldYuyue.getYuyueStart() != null ? oldYuyue.getYuyueStart() : new Date());
            quxiaoyuyue.setYuyue_end(oldYuyue.getYuyueEnd() != null ? oldYuyue.getYuyueEnd() : new Date());

            // ========== 5. 删除原预约 + 保存取消记录（核心逻辑，不受座位状态影响） ==========
            yuyuexinxiService.deleteById(crossrefid);
            quxiaoyueService.insert(quxiaoyuyue);
            System.out.println("预约取消成功：crossrefid=" + crossrefid + ", 学生学号=" + oldYuyue.getXuehao());

            return R.ok().put("code", 0).put("msg", "取消预约成功");

        } catch (DataAccessException e) {
            // 数据库异常：手动回滚事务
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("数据库操作失败：" + e.getMessage()).put("code", 500);
        } catch (Exception e) {
            // 所有其他异常：手动回滚事务
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("取消预约失败：" + e.getMessage()).put("code", 500);
        }
    }

    /**
     * 前端保存（同步修复）
     */
    @RequestMapping("/add")
    @Transactional
    public R add(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        try {
            // 核心字段判空
            Long crossrefid = quxiaoyuyue.getCrossrefid();
            if (crossrefid == null) {
                return R.error("原预约记录ID不能为空");
            }

            // 生成ID + 设置取消时间
            quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
            quxiaoyuyue.setUserid((Long) request.getSession().getAttribute("userId"));
            quxiaoyuyue.setQuxiaoshijian(new Date());

            // 校验原预约记录
            YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(crossrefid);
            if (oldYuyue == null) {
                return R.error("原预约记录不存在，无法取消");
            }

            // 学生权限校验
            String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (currentTable.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null || !currentXuehao.equals(oldYuyue.getXuehao())) {
                    return R.error("无权限取消他人的预约记录");
                }
            }

            // 同步时间（空值防护）
            quxiaoyuyue.setYuyue_start(oldYuyue.getYuyueStart() != null ? oldYuyue.getYuyueStart() : new Date());
            quxiaoyuyue.setYuyue_end(oldYuyue.getYuyueEnd() != null ? oldYuyue.getYuyueEnd() : new Date());

            // 删除原预约 + 保存取消记录
            yuyuexinxiService.deleteById(crossrefid);
            quxiaoyueService.insert(quxiaoyuyue);

            return R.ok("取消预约成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("取消预约失败：" + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        quxiaoyueService.updateById(quxiaoyuyue);
        return R.ok("修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        quxiaoyueService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
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

        Wrapper<QuxiaoyuyueEntity> wrapper = new EntityWrapper<QuxiaoyuyueEntity>();
        if (map.get("remindstart") != null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }

        String tableName = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            wrapper.eq("xuehao", (String) request.getSession().getAttribute("username"));
        }

        int count = quxiaoyueService.selectCount(wrapper);
        return R.ok().put("count", count);
    }

    // 工具方法：字符串转Date（解决前端日期字符串转换）
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}