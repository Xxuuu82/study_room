package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shensu")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShenSuController {
    private static final Logger logger = LoggerFactory.getLogger(ShenSuController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 分页查询申诉记录（返回格式和黑名单完全一致）
    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String chuliZhuangtai) {
        // 构造返回结果（和黑名单格式完全一致）
        Map<String, Object> result = new HashMap<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT id, addtime, shensuDanhao, xuehao, xingming, " +
                    "weiguiRecordId, shensuNeirong, shensuShijian, chuliZhuangtai, chuliRen, chuliBeizhu " +
                    "FROM shensu WHERE 1=1 ");

            if (xuehao != null && !xuehao.isEmpty()) {
                sql.append("AND xuehao LIKE '%").append(xuehao.trim()).append("%' ");
            }
            if (chuliZhuangtai != null && !chuliZhuangtai.isEmpty()) {
                sql.append("AND chuliZhuangtai = ").append(chuliZhuangtai).append(" ");
            }

            int start = (page - 1) * size;
            sql.append("LIMIT ").append(start).append(",").append(size);

            List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString());

            StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM shensu WHERE 1=1 ");
            if (xuehao != null && !xuehao.isEmpty()) {
                countSql.append("AND xuehao LIKE '%").append(xuehao.trim()).append("%' ");
            }
            if (chuliZhuangtai != null && !chuliZhuangtai.isEmpty()) {
                countSql.append("AND chuliZhuangtai = ").append(chuliZhuangtai).append(" ");
            }
            Integer total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class);

            // 核心：和黑名单返回格式对齐（data.records + data.total）
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("records", records);
            dataMap.put("total", total);

            result.put("code", 0);
            result.put("msg", "查询成功");
            result.put("data", dataMap);
        } catch (Exception e) {
            logger.error("查询申诉记录异常", e);
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            result.put("data", new HashMap<>());
        }
        return result;
    }

    // 处理申诉（返回格式和黑名单完全一致）
    @PostMapping("/handle")
    public Map<String, Object> handle(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 增加参数非空校验
            if (!params.containsKey("id") || !params.containsKey("chuliZhuangtai")) {
                result.put("code", -1);
                result.put("msg", "参数缺失：id或chuliZhuangtai不能为空");
                return result;
            }

            Long id = Long.parseLong(params.get("id").toString());
            Integer status = Integer.parseInt(params.get("chuliZhuangtai").toString());
            String chuliRen = params.get("chuliRen") != null ? params.get("chuliRen").toString() : "";
            String chuliBeizhu = params.get("chuliBeizhu") != null ? params.get("chuliBeizhu").toString() : "";

            // 更新申诉状态
            String updateSql = "UPDATE shensu SET chuliZhuangtai=?, chuliRen=?, chuliBeizhu=? WHERE id=?";
            int rows = jdbcTemplate.update(updateSql, status, chuliRen, chuliBeizhu, id);

            if (rows <= 0) {
                result.put("code", -1);
                result.put("msg", "处理失败：申诉记录更新失败");
                return result;
            }

            // 申诉通过则标记违规记录为无效
            if (status == 1) {
                String getWeiguiIdSql = "SELECT weiguiRecordId FROM shensu WHERE id=?";
                Long weiguiRecordId = jdbcTemplate.queryForObject(getWeiguiIdSql, Long.class, id);
                // 直接更新违规记录，无需依赖其他控制器
                String updateWeiguiSql = "UPDATE weigui_record SET isValid=0 WHERE id=?";
                jdbcTemplate.update(updateWeiguiSql, weiguiRecordId);
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