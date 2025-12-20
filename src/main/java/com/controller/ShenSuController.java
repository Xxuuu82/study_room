package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ShenSuController - 增加新增申诉接口（/api/shensu/add）
 */
@RestController
@RequestMapping("/api/shensu")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShenSuController {

    private static final Logger logger = LoggerFactory.getLogger(ShenSuController.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ShenSuController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String chuliZhuangtai) {

        Map<String, Object> result = new HashMap<>();

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT id, addtime, shensuDanhao, xuehao, xingming, " +
                            "weiguiRecordId, shensuNeirong, shensuShijian, " +
                            "chuliZhuangtai, chuliRen, chuliBeizhu " +
                            "FROM shensu WHERE 1=1 "
            );

            StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM shensu WHERE 1=1 ");

            List<Object> params = new ArrayList<>();
            List<Object> countParams = new ArrayList<>();

            if (hasText(xuehao)) {
                sql.append("AND xuehao LIKE ? ");
                countSql.append("AND xuehao LIKE ? ");
                String pattern = "%" + xuehao.trim() + "%";
                params.add(pattern);
                countParams.add(pattern);
            }

            if (hasText(chuliZhuangtai)) {
                sql.append("AND chuliZhuangtai = ? ");
                countSql.append("AND chuliZhuangtai = ? ");
                params.add(chuliZhuangtai.trim());
                countParams.add(chuliZhuangtai.trim());
            }

            int offset = (page - 1) * size;
            sql.append("ORDER BY addtime DESC ");
            sql.append("LIMIT ?, ?");
            params.add(offset);
            params.add(size);

            List<Map<String, Object>> records =
                    jdbcTemplate.queryForList(sql.toString(), params.toArray());

            Integer total =
                    jdbcTemplate.queryForObject(countSql.toString(), countParams.toArray(), Integer.class);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("records", records);
            dataMap.put("total", total == null ? 0 : total);

            result.put("code", 0);
            result.put("msg", "查询成功");
            result.put("data", dataMap);
        } catch (Exception e) {
            logger.error("查询申诉记录异常", e);
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            result.put("data", Collections.emptyMap());
        }

        return result;
    }

    /**
     * 新增申诉接口：前端应 POST 到 /api/shensu/add
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 提取并计算所有要传入 lambda 的值，确保它们是 final / effectively final
            final String shensuDanhao = params.get("shensuDanhao") == null ? ("SS" + System.currentTimeMillis()) : params.get("shensuDanhao").toString();
            final String xuehao = params.get("xuehao") == null ? "" : params.get("xuehao").toString();
            final String xingming = params.get("xingming") == null ? "" : params.get("xingming").toString();
            final String shensuNeirong = params.get("shensuNeirong") == null ? "" : params.get("shensuNeirong").toString();
            // 前端现在会把诉求字段发为 shensuTupian（也可以当做文本保存），如果没有则默认空
            final String shensuTupian = params.get("shensuTupian") == null ? "" : params.get("shensuTupian").toString();
            final String shensuShijianParam = params.get("shensuShijian") == null ? null : params.get("shensuShijian").toString();

            Object weiguiRecordIdObj = params.get("weiguiRecordId");
            final Long weiguiRecordId;
            if (weiguiRecordIdObj != null) {
                Long tmp = null;
                try { tmp = Long.valueOf(String.valueOf(weiguiRecordIdObj)); } catch (Exception ignored) {}
                weiguiRecordId = tmp;
            } else {
                weiguiRecordId = null;
            }

            final String addtime = sdf.format(new Date());
            // 计算最终的 shensuShijian（如果传入为空则使用当前 addtime）
            final String shensuShijian = hasText(shensuShijianParam) ? shensuShijianParam : addtime;

            // 注意：数据库表结构中没有 weiguiDanhao 和 shensuSuqiu 列。
            // 按照你给的表结构，这里插入存在的列：addtime, shensuDanhao, xuehao, xingming,
            // weiguiRecordId, shensuNeirong, shensuTupian, shensuShijian, chuliZhuangtai, chuliRen, chuliBeizhu
            String insertSql = "INSERT INTO shensu (addtime, shensuDanhao, xuehao, xingming, weiguiRecordId, shensuNeirong, shensuTupian, shensuShijian, chuliZhuangtai, chuliRen, chuliBeizhu) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rows = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, addtime);
                ps.setString(2, shensuDanhao);
                ps.setString(3, xuehao);
                ps.setString(4, xingming);
                if (weiguiRecordId != null) ps.setLong(5, weiguiRecordId);
                else ps.setObject(5, null);
                ps.setString(6, shensuNeirong);
                ps.setString(7, shensuTupian);
                ps.setString(8, shensuShijian);
                ps.setInt(9, 0); // chuliZhuangtai 默认 0（未处理）
                ps.setString(10, ""); // chuliRen 默认空
                ps.setString(11, ""); // chuliBeizhu 默认空
                return ps;
            }, keyHolder);

            if (rows <= 0) {
                result.put("code", -1);
                result.put("msg", "新增申诉失败：数据库未影响行");
                return result;
            }

            Number key = keyHolder.getKey();
            Long newId = key == null ? null : key.longValue();

            Map<String, Object> data = new HashMap<>();
            data.put("id", newId);
            data.put("rows", rows);

            result.put("code", 0);
            result.put("msg", "新增申诉成功");
            result.put("data", data);
        } catch (Exception e) {
            logger.error("新增申诉异常", e);
            result.put("code", -1);
            result.put("msg", "新增申诉异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 处理申诉（原有方法）
     */
    @PostMapping("/handle")
    public Map<String, Object> handle(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (!params.containsKey("id") || !params.containsKey("chuliZhuangtai")) {
                result.put("code", -1);
                result.put("msg", "参数缺失：id 或 chuliZhuangtai 不能为空");
                return result;
            }

            Long id = Long.valueOf(params.get("id").toString());
            Integer status = Integer.valueOf(params.get("chuliZhuangtai").toString());
            String chuliRen = params.get("chuliRen") == null ? "" : params.get("chuliRen").toString();
            String chuliBeizhu = params.get("chuliBeizhu") == null ? "" : params.get("chuliBeizhu").toString();

            String updateSql = "UPDATE shensu SET chuliZhuangtai = ?, chuliRen = ?, chuliBeizhu = ? WHERE id = ?";
            int rows = jdbcTemplate.update(updateSql, status, chuliRen, chuliBeizhu, id);

            if (rows <= 0) {
                result.put("code", -1);
                result.put("msg", "处理失败：申诉记录更新失败");
                return result;
            }

            if (status == 1) {
                String getWeiguiSql = "SELECT weiguiRecordId FROM shensu WHERE id = ?";
                Long weiguiRecordId = jdbcTemplate.queryForObject(getWeiguiSql, Long.class, id);

                if (weiguiRecordId != null) {
                    String updateWeiguiSql = "UPDATE weigui_record SET isValid = 0 WHERE id = ?";
                    jdbcTemplate.update(updateWeiguiSql, weiguiRecordId);
                }
            }

            result.put("code", 0);
            result.put("msg", "处理成功");
        } catch (Exception e) {
            logger.error("处理申诉异常", e);
            result.put("code", -1);
            result.put("msg", "处理异常：" + e.getMessage());
        }

        return result;
    }
}