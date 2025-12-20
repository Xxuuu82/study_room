package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/shensu")   // 注意这里加了前导斜杠
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShenSuController {

    private static final Logger logger = LoggerFactory.getLogger(ShenSuController.class);

    private final JdbcTemplate jdbcTemplate;

    public ShenSuController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 工具方法：判断字符串是否有内容 */
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * 分页查询申诉记录
     * 对应前端：GET /study_room/api/shensu/list
     */
    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String chuliZhuangtai) {

        Map<String, Object> result = new HashMap<>();

        try {
            // ====== 1. 组装查询 SQL ======
            StringBuilder sql = new StringBuilder(
                    "SELECT id, addtime, shensuDanhao, xuehao, xingming, " +
                            "weiguiRecordId, shensuNeirong, shensuShijian, " +
                            "chuliZhuangtai, chuliRen, chuliBeizhu " +
                            "FROM shensu WHERE 1=1 "
            );

            StringBuilder countSql = new StringBuilder(
                    "SELECT COUNT(*) FROM shensu WHERE 1=1 "
            );

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
                // 如果库里是 int，这里直接传字符串也没问题，JDBC 会自动转换
                params.add(chuliZhuangtai.trim());
                countParams.add(chuliZhuangtai.trim());
            }

            int offset = (page - 1) * size;
            sql.append("ORDER BY addtime DESC ");
            sql.append("LIMIT ?, ?");
            params.add(offset);
            params.add(size);

            // ====== 2. 执行查询 ======
            List<Map<String, Object>> records =
                    jdbcTemplate.queryForList(sql.toString(), params.toArray());

            Integer total =
                    jdbcTemplate.queryForObject(countSql.toString(), countParams.toArray(), Integer.class);

            // ====== 3. 封装返回，和黑名单格式对齐 ======
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
     * 处理申诉
     * 对应前端：POST /study_room/api/shensu/handle
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

            // 更新申诉表
            String updateSql = "UPDATE shensu SET chuliZhuangtai = ?, chuliRen = ?, chuliBeizhu = ? WHERE id = ?";
            int rows = jdbcTemplate.update(updateSql, status, chuliRen, chuliBeizhu, id);

            if (rows <= 0) {
                result.put("code", -1);
                result.put("msg", "处理失败：申诉记录更新失败");
                return result;
            }

            // 如果申诉通过，顺带把违规记录标记为无效
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
