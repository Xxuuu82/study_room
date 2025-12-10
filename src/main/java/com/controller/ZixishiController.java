package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ZixishiEntity;
import com.entity.view.ZixishiView;
import com.service.StoreupService;
import com.service.ZixishiService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自习室
 * 后端接口
 */
@RestController
@RequestMapping("/zixishi")
public class ZixishiController {
    @Autowired
    private ZixishiService zixishiService;

    @Autowired
    private StoreupService storeupService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ZixishiEntity zixishi,
                  HttpServletRequest request) {
        EntityWrapper<ZixishiEntity> ew = new EntityWrapper<ZixishiEntity>();

        PageUtils page = zixishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zixishi), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, ZixishiEntity zixishi,
                  HttpServletRequest request) {
        EntityWrapper<ZixishiEntity> ew = new EntityWrapper<ZixishiEntity>();

        PageUtils page = zixishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zixishi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(ZixishiEntity zixishi) {
        EntityWrapper<ZixishiEntity> ew = new EntityWrapper<ZixishiEntity>();
        ew.allEq(MPUtil.allEQMapPre(zixishi, "zixishi"));
        return R.ok().put("data", zixishiService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ZixishiEntity zixishi) {
        EntityWrapper<ZixishiEntity> ew = new EntityWrapper<ZixishiEntity>();
        ew.allEq(MPUtil.allEQMapPre(zixishi, "zixishi"));
        ZixishiView zixishiView = zixishiService.selectView(ew);
        return R.ok("查询自习室成功").put("data", zixishiView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ZixishiEntity zixishi = zixishiService.selectById(id);
        return R.ok().put("data", zixishi);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        ZixishiEntity zixishi = zixishiService.selectById(id);
        return R.ok().put("data", zixishi);
    }


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZixishiEntity zixishi, HttpServletRequest request) {
        zixishi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(zixishi);
        zixishiService.insert(zixishi);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ZixishiEntity zixishi, HttpServletRequest request) {
        zixishi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(zixishi);
        zixishiService.insert(zixishi);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ZixishiEntity zixishi, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(zixishi);
        zixishiService.updateById(zixishi);//全部更新
        try {
            // 删除旧表
            jdbcTemplate.execute("DROP TABLE IF EXISTS seats_" + zixishi.getId()); // 按自习室ID区分表

            // 创建新表
            jdbcTemplate.execute("CREATE TABLE seats_" + zixishi.getId() + " (" +
                    "id INT PRIMARY KEY, " +
                    "status INT DEFAULT 1" +
                    ")");

            // 批量插入新座位（优化版）
            String sql = "INSERT INTO seats_" + zixishi.getId() + " (id) VALUES (?)";
            List<Object[]> batchArgs = new ArrayList<>();
//            System.out.println(zixishi.getZuowei());
            for (int i = 1; i <= zixishi.getZuowei(); i++) {
                batchArgs.add(new Object[]{i});
            }
            jdbcTemplate.batchUpdate(sql, batchArgs);

            return R.ok();
        } catch (DataAccessException e) {
            throw new RuntimeException("座位表更新失败：" + e.getMessage()); // 触发事务回滚
        }
    }

    @GetMapping("/seats/{zixishiId}")
    public ResponseEntity<List<Map<String, Object>>> getSeats(
            @PathVariable Long zixishiId
    ) {
        String tableName = "seats_" + zixishiId; // 动态表名

        // 1. 验证表是否存在
        if (!isTableExist(tableName)) {
            return ResponseEntity.notFound().build();
        }

        // 2. 查询座位数据
        List<Map<String, Object>> seats = jdbcTemplate.queryForList(
                "SELECT id, status FROM " + tableName + " ORDER BY id"
        );

        return ResponseEntity.ok(seats);
    }

    private boolean isTableExist(String tableName) {
        // MySQL检查表是否存在
        String sql = "SELECT COUNT(*) FROM information_schema.tables " +
                "WHERE table_schema = DATABASE() AND table_name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, tableName) > 0;
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        zixishiService.deleteBatchIds(Arrays.asList(ids));
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

        Wrapper<ZixishiEntity> wrapper = new EntityWrapper<ZixishiEntity>();
        if (map.get("remindstart") != null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }


        int count = zixishiService.selectCount(wrapper);
        return R.ok().put("count", count);
    }


}
