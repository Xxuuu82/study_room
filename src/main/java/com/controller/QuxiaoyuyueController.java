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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 取消预约
 */
@RestController
@RequestMapping("/quxiaoyuyue")
public class QuxiaoyuyueController {
    private static final Logger logger = LoggerFactory.getLogger(QuxiaoyuyueController.class);

    @Autowired
    private QuxiaoyuyueService quxiaoyuyueService;

    @Autowired
    private YuyuexinxiService yuyuexinxiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 日期格式化工具（解决字符串转Date）
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        // 手动指定数据库列名（带下划线）
        ew.setSqlSelect(
                "id", "addtime", "yuyuedanhao", "mingcheng",
                "yuyue_start", "yuyue_end",
                "quxiaoshijian", "quxiaoyuanyin", "xuehao", "xingming"
        );

        PageUtils page = quxiaoyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quxiaoyuyue), params), params));

        // 调试：打印查询的SQL（需开启MyBatis日志）
        logger.debug("取消预约列表查询SQL segment: {}", ew.getSqlSegment());

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

        PageUtils page = quxiaoyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quxiaoyuyue), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R lists(QuxiaoyuyueEntity quxiaoyuyue) {
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();
        ew.allEq(MPUtil.allEQMapPre(quxiaoyuyue, "quxiaoyuyue"));
        return R.ok().put("data", quxiaoyuyueService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(QuxiaoyuyueEntity quxiaoyuyue) {
        EntityWrapper<QuxiaoyuyueEntity> ew = new EntityWrapper<QuxiaoyuyueEntity>();
        ew.allEq(MPUtil.allEQMapPre(quxiaoyuyue, "quxiaoyuyue"));
        QuxiaoyuyueView quxiaoyuyueView = quxiaoyuyueService.selectView(ew);
        return R.ok("查询取消预约成功").put("data", quxiaoyuyueView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        QuxiaoyuyueEntity quxiaoyuyue = quxiaoyuyueService.selectById(id);
        return R.ok().put("data", quxiaoyuyue);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        QuxiaoyuyueEntity quxiaoyuyue = quxiaoyuyueService.selectById(id);
        return R.ok().put("data", quxiaoyuyue);
    }

    /**
     * 后端保存（按时间段管控座位状态）
     *
     * 重要修改：为了兼容应用层请求的多种 Content-Type（避免 415），此处不使用 @RequestBody 注入实体，
     * 而是手动从 HttpServletRequest 读取 JSON 或表单参数，并构建实体对象。
     */
    @RequestMapping("/save")
    @Transactional // 事务保证原子性
    public R save(HttpServletRequest request) {
        QuxiaoyuyueEntity quxiaoyuyue = null;
        try {
            // 1. 解析请求体：支持 application/json 或 表单（application/x-www-form-urlencoded 或 multipart/form-data）
            String ct = request.getContentType();
            if (ct != null && ct.toLowerCase().contains("application/json")) {
                String body = readInputStreamToString(request.getInputStream());
                if (body != null && !body.trim().isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    quxiaoyuyue = mapper.readValue(body, QuxiaoyuyueEntity.class);
                }
            } else {
                // 尝试从参数 map 获取
                Map<String, String[]> paramMap = request.getParameterMap();
                if (paramMap != null && !paramMap.isEmpty()) {
                    quxiaoyuyue = buildFromParamMap(paramMap);
                } else {
                    // 兜底：尝试解析流为 JSON
                    String body = readInputStreamToString(request.getInputStream());
                    if (body != null && !body.trim().isEmpty()) {
                        ObjectMapper mapper = new ObjectMapper();
                        quxiaoyuyue = mapper.readValue(body, QuxiaoyuyueEntity.class);
                    }
                }
            }

            if (quxiaoyuyue == null) {
                return R.error("请求参数为空或格式无法识别");
            }

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

            // ========== 2. 获取原预约并校验 ==========
            YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(crossrefid);
            if (oldYuyue == null) {
                return R.error("原预约记录不存在，无法取消");
            }

            // 学生权限校验（学生只能取消自己的预约）
            String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (currentTable.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null || !currentXuehao.equals(oldYuyue.getXuehao())) {
                    return R.error("无权限取消他人的预约记录");
                }
            }

            // 若已违纪（weigui_flag==1）不允许取消
            Integer wf = oldYuyue.getWeiguiFlag();
            if (wf != null && wf == 1) {
                return R.error("该预约已被记录为违纪，不允许取消");
            }

            // 若已经签到且未签退，不允许取消
            if ("已签到".equals(oldYuyue.getQiandaozhuangtai()) && !"已签退".equals(oldYuyue.getQiantuizhuangtai())) {
                return R.error("该预约已签到，不能取消预约");
            }

            // 仅当当前时间 < yuyueStart 时允许取消（即“未到可签到时间”）
            Date now = new Date();
            Date start = oldYuyue.getYuyueStart();
            if (start == null) {
                return R.error("预约开始时间缺失，无法取消");
            }
            if (now.getTime() >= start.getTime()) {
                return R.error("只能在未到可签到时间取消预约");
            }

            // ========== 3. 生成取消记录ID + 设置取消时间 ==========
            quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
            quxiaoyuyue.setQuxiaoshijian(new Date());

            // ========== 4. 强制解锁座位（更新 status = 1） ==========
            String tableName = "seats_" + zixishiId;
            if (!tableName.matches("seats_\\d+")) {
                return R.error("非法的自习室ID，表名不合法");
            }
            jdbcTemplate.update(
                    "UPDATE " + tableName + " SET status = 1 WHERE id = ?",
                    zuoweiId
            );

            // ========== 5. 同步时间到取消记录 ==========
            quxiaoyuyue.setYuyueStart(oldYuyue.getYuyueStart() != null ? oldYuyue.getYuyueStart() : new Date());
            quxiaoyuyue.setYuyueEnd(oldYuyue.getYuyueEnd() != null ? oldYuyue.getYuyueEnd() : new Date());

            // ========== 6. 删除原预约 + 保存取消记录（事务内） ==========
            yuyuexinxiService.deleteById(crossrefid);
            quxiaoyuyueService.insert(quxiaoyuyue);
            logger.info("预约取消成功：crossrefid={}, 学号={}", crossrefid, oldYuyue.getXuehao());
            return R.ok().put("code", 0).put("msg", "取消预约成功");

        } catch (DataAccessException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("数据库操作失败：" + e.getMessage()).put("code", 500);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("取消预约失败：" + e.getMessage()).put("code", 500);
        }
    }

    /**
     * 前端保存（同步修复：同样在 add 中做相同兼容解析）
     */
    @RequestMapping("/add")
    @Transactional
    public R add(HttpServletRequest request) {
        QuxiaoyuyueEntity quxiaoyuyue = null;
        try {
            String ct = request.getContentType();
            if (ct != null && ct.toLowerCase().contains("application/json")) {
                String body = readInputStreamToString(request.getInputStream());
                if (body != null && !body.trim().isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    quxiaoyuyue = mapper.readValue(body, QuxiaoyuyueEntity.class);
                }
            } else {
                Map<String, String[]> paramMap = request.getParameterMap();
                if (paramMap != null && !paramMap.isEmpty()) {
                    quxiaoyuyue = buildFromParamMap(paramMap);
                } else {
                    String body = readInputStreamToString(request.getInputStream());
                    if (body != null && !body.trim().isEmpty()) {
                        ObjectMapper mapper = new ObjectMapper();
                        quxiaoyuyue = mapper.readValue(body, QuxiaoyuyueEntity.class);
                    }
                }
            }

            if (quxiaoyuyue == null) {
                return R.error("请求参数为空，无法取消预约");
            }

            Long crossrefid = quxiaoyuyue.getCrossrefid();
            if (crossrefid == null) {
                return R.error("原预约记录ID不能为空");
            }

            YuyuexinxiEntity oldYuyue = yuyuexinxiService.selectById(crossrefid);
            if (oldYuyue == null) {
                return R.error("原预约记录不存在，无法取消");
            }

            String currentTable = request.getSession().getAttribute("tableName") == null ? "" : request.getSession().getAttribute("tableName").toString();
            if (currentTable.equals("xuesheng")) {
                String currentXuehao = (String) request.getSession().getAttribute("username");
                if (currentXuehao == null || !currentXuehao.equals(oldYuyue.getXuehao())) {
                    return R.error("无权限取消他人的预约记录");
                }
            }

            Integer wf = oldYuyue.getWeiguiFlag();
            if (wf != null && wf == 1) {
                return R.error("该预约已被记录为违纪，不允许取消");
            }

            if ("已签到".equals(oldYuyue.getQiandaozhuangtai()) && !"已签退".equals(oldYuyue.getQiantuizhuangtai())) {
                return R.error("该预约已签到，不能取消预约");
            }

            Date now = new Date();
            Date start = oldYuyue.getYuyueStart();
            if (start == null) {
                return R.error("预约开始时间缺失，无法取消");
            }
            if (now.getTime() >= start.getTime()) {
                return R.error("只能在未到可签到时间取消预约");
            }

            quxiaoyuyue.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
            quxiaoyuyue.setUserid((Long) request.getSession().getAttribute("userId"));
            quxiaoyuyue.setQuxiaoshijian(new Date());
            quxiaoyuyue.setYuyueStart(oldYuyue.getYuyueStart() != null ? oldYuyue.getYuyueStart() : new Date());
            quxiaoyuyue.setYuyueEnd(oldYuyue.getYuyueEnd() != null ? oldYuyue.getYuyueEnd() : new Date());

            try {
                String tableName = "seats_" + quxiaoyuyue.getZixishiid();
                if (tableName.matches("seats_\\d+")) {
                    jdbcTemplate.update("UPDATE " + tableName + " SET status = 1 WHERE id = ?", quxiaoyuyue.getZuowei());
                }
            } catch (Exception e) {
                logger.warn("解锁座位失败：{}", e.getMessage());
            }

            yuyuexinxiService.deleteById(crossrefid);
            quxiaoyuyueService.insert(quxiaoyuyue);

            return R.ok("取消预约成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("取消预约失败：" + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody QuxiaoyuyueEntity quxiaoyuyue, HttpServletRequest request) {
        quxiaoyuyueService.updateById(quxiaoyuyue);
        return R.ok("修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        quxiaoyuyueService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

    /**
     * 提醒接口（保持原样）
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

        int count = quxiaoyuyueService.selectCount(wrapper);
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

    // ====== 辅助方法：从 InputStream 读取到 String（兼容 Java 8） ======
    private String readInputStreamToString(InputStream is) {
        if (is == null) return null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // ====== 辅助方法：从 paramMap 构建实体（对常用字段做解析和类型转换） ======
    private QuxiaoyuyueEntity buildFromParamMap(Map<String, String[]> paramMap) {
        QuxiaoyuyueEntity e = new QuxiaoyuyueEntity();
        try {
            if (paramMap.containsKey("id")) {
                String v = paramMap.get("id")[0];
                if (v != null && !v.isEmpty()) e.setId(Long.parseLong(v));
            }
            if (paramMap.containsKey("crossrefid")) {
                String v = paramMap.get("crossrefid")[0];
                if (v != null && !v.isEmpty()) e.setCrossrefid(Long.parseLong(v));
            }
            if (paramMap.containsKey("yuyuedanhao")) e.setYuyuedanhao(paramMap.get("yuyuedanhao")[0]);
            if (paramMap.containsKey("mingcheng")) e.setMingcheng(paramMap.get("mingcheng")[0]);
            if (paramMap.containsKey("zuowei")) {
                String v = paramMap.get("zuowei")[0];
                if (v != null && !v.isEmpty()) {
                    try { e.setZuowei(Integer.parseInt(v)); } catch (Exception ignored) {}
                }
            }
            if (paramMap.containsKey("zixishiid")) {
                String v = paramMap.get("zixishiid")[0];
                if (v != null && !v.isEmpty()) {
                    try { e.setZixishiid(Integer.parseInt(v)); } catch (Exception ignored) {}
                }
            }
            if (paramMap.containsKey("yuyue_start")) {
                Date d = parseDate(paramMap.get("yuyue_start")[0]);
                if (d != null) e.setYuyueStart(d);
            }
            if (paramMap.containsKey("yuyue_end")) {
                Date d = parseDate(paramMap.get("yuyue_end")[0]);
                if (d != null) e.setYuyueEnd(d);
            }
            if (paramMap.containsKey("quxiaoshijian")) {
                Date d = parseDate(paramMap.get("quxiaoshijian")[0]);
                if (d != null) e.setQuxiaoshijian(d);
            }
            if (paramMap.containsKey("quxiaoyuanyin")) e.setQuxiaoyuanyin(paramMap.get("quxiaoyuanyin")[0]);
            if (paramMap.containsKey("xuehao")) e.setXuehao(paramMap.get("xuehao")[0]);
            if (paramMap.containsKey("xingming")) e.setXingming(paramMap.get("xingming")[0]);
            if (paramMap.containsKey("shouji")) e.setShouji(paramMap.get("shouji")[0]);
            if (paramMap.containsKey("userid")) {
                String v = paramMap.get("userid")[0];
                if (v != null && !v.isEmpty()) {
                    try { e.setUserid(Long.parseLong(v)); } catch (Exception ignored) {}
                }
            }
        } catch (Exception ex) {
            logger.warn("从 paramMap 构建实体时发生异常：{}", ex.getMessage());
        }
        return e;
    }
}