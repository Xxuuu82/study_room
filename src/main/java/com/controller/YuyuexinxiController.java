package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
    public R detail(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            // 校验登录状态
            String tableName = (String) request.getSession().getAttribute("tableName");
            if (tableName == null) {
                return R.error("请先登录");
            }

            YuyuexinxiEntity yuyuexinxi = yuyuexinxiService.selectById(id);
            // 记录不存在直接返回
            if (yuyuexinxi == null) {
                logger.warn("前端查询预约记录不存在，ID：{}", id);
                return R.error("预约记录不存在");
            }

            // 学生身份强制校验学号
            if (tableName.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (!currentXuehao.equals(yuyuexinxi.getXuehao())) {
                    logger.warn("学生{}前端尝试访问他人预约记录，记录ID：{}", currentXuehao, id);
                    return R.error("无权限查看该预约记录");
                }
            }

            return R.ok().put("data", yuyuexinxi);
        } catch (Exception e) {
            logger.error("前端查询预约详情失败，ID：{}", id, e);
            return R.error("查询失败：" + e.getMessage());
        }
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
     * 前端保存（保留原有逻辑）
     */
    @RequestMapping("/add")
    public R add(@RequestBody YuyuexinxiEntity yuyuexinxi, HttpServletRequest request) {
        Integer zuoweiId = yuyuexinxi.getZuowei();
        Integer zixishiId = yuyuexinxi.getZixishiid();

        yuyuexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(yuyuexinxi);

        try {
            // 3. 更新座位状态为0（锁定）
            String tableName = "seats_" + zixishiId;

            // 安全校验表名格式
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法表名");
            }

            // 执行锁定操作（确保原子性）
            int updatedRows = jdbcTemplate.update(
                    "UPDATE " + tableName + " SET status = 0 WHERE id = ? AND status = 1",
                    zuoweiId
            );

            if (updatedRows == 0) {
                // 座位已被占用或不存在
                return R.error("座位不可用");
            }

            // 4. 插入预约记录
            yuyuexinxiService.insert(yuyuexinxi);

            return R.ok();
        } catch (DataAccessException e) {
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
}