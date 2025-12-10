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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 取消预约
 */
@RestController
@RequestMapping("/quxiaoyuyue")
public class QuxiaoyuyueController {
    @Autowired
    private QuxiaoyuyueService quxiaoyueService;

    // 新增：注入预约信息服务，用于删除原预约记录
    @Autowired
    private YuyuexinxiService yuyuexinxiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, QuxiaoyuyueEntity quxiaoyuyue,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName").toString();
        if (tableName.equals("xuesheng")) {
            quxiaoyuyue.setXuehao((String) request.getSession().getAttribute("username"));
        }
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();

        PageUtils page = quxiaoyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quxiaoyuyue), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, QuxiaoyuyueEntity quxiaoyuyue,
                  HttpServletRequest request) {
        String tableName = request.getSession().getAttribute("tableName").toString();
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
     * 后端保存
     */
    @RequestMapping("/save")
    @Transactional // 新增事务注解，确保删除原记录和保存取消记录原子性
    public R save(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        Integer zuoweiId = quxiaoyuyue.getZuowei();
        Integer zixishiId = quxiaoyuyue.getZixishiid();
        quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());

        // 设置取消时间
        quxiaoyuyue.setQuxiaoshijian(new Date());

        try {
            // 更新座位状态为1（解锁）
            String tableName = "seats_" + zixishiId;
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法表名");
            }

            int updatedRows = jdbcTemplate.update(
                    "UPDATE " + tableName + " SET status = 1 WHERE id = ? AND status = 0",
                    zuoweiId
            );

            // 核心逻辑：利用crossrefid关联原预约ID，删除原预约记录
            if (quxiaoyuyue.getCrossrefid() != null) {
                // 查询原预约记录
                YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(quxiaoyuyue.getCrossrefid());
                if (oldYuyue == null) {
                    return R.error("原预约记录不存在");
                }

                // 权限校验：学生只能取消自己的预约
                String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
                if (currentTable.equals("xuesheng")) {
                    String currentXuehao = (String) request.getSession().getAttribute("username");
                    if (!currentXuehao.equals(oldYuyue.getXuehao())) {
                        return R.error("无权限取消该预约记录");
                    }
                }

                // 删除原预约记录
                yuyuexinxiService.deleteById(quxiaoyuyue.getCrossrefid());
            }

            // 保存取消预约记录
            quxiaoyueService.insert(quxiaoyuyue);
            return R.ok();
        } catch (DataAccessException e) {
            // 事务回滚
            return R.error("数据库操作失败：" + e.getMessage());
        }
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    @Transactional // 新增事务注解
    public R add(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        quxiaoyuyue.setUserid((Long) request.getSession().getAttribute("userId"));
        // 设置取消时间
        quxiaoyuyue.setQuxiaoshijian(new Date());

        // 核心逻辑：利用crossrefid关联原预约ID，删除原预约记录
        if (quxiaoyuyue.getCrossrefid() != null) {
            // 查询原预约记录
            YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(quxiaoyuyue.getCrossrefid());
            if (oldYuyue == null) {
                return R.error("原预约记录不存在");
            }

            // 权限校验：学生只能取消自己的预约
            String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (currentTable.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (!currentXuehao.equals(oldYuyue.getXuehao())) {
                    return R.error("无权限取消该预约记录");
                }
            }

            // 删除原预约记录
            yuyuexinxiService.deleteById(quxiaoyuyue.getCrossrefid());
        }

        // 保存取消预约记录
        quxiaoyueService.insert(quxiaoyuyue);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        quxiaoyueService.updateById(quxiaoyuyue);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        quxiaoyueService.deleteBatchIds(Arrays.asList(ids));
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

        Wrapper<QuxiaoyuyueEntity> wrapper = new EntityWrapper<QuxiaoyuyueEntity>();
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

        int count = quxiaoyueService.selectCount(wrapper);
        return R.ok().put("count", count);
    }
}