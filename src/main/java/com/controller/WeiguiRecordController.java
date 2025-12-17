package com.controller;

import com.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/weigui")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeiguiRecordController {
    private static final Logger logger = LoggerFactory.getLogger(WeiguiRecordController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 新增违规记录：用Map接收前端JSON，彻底跳过实体类Date解析
    @PostMapping("/add")
    public ResponseEntity<R> addWeiguiRecord(@RequestBody Map<String, Object> paramMap) {
        try {
            // 1. 从Map中获取前端参数（直接取String，避免Date解析）
            String xuehao = paramMap.get("xuehao").toString().trim();
            String xingming = paramMap.get("xingming").toString().trim();
            String zixishiMingcheng = paramMap.get("zixishiMingcheng").toString().trim();
            String weiguiShijian = paramMap.get("weiguiShijian").toString(); // 前端传的yyyy-MM-dd HH:mm:ss
            String weiguiLeixing = paramMap.get("weiguiLeixing").toString();
            String weiguiBeizhu = paramMap.get("weiguiBeizhu") != null ? paramMap.get("weiguiBeizhu").toString().trim() : "";

            logger.info("新增违规记录参数：xuehao={}, xingming={}, weiguiShijian={}", xuehao, xingming, weiguiShijian);

            // 2. 生成违规单号和当前时间
            String weiguiDanhao = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String addtime = sdf.format(new Date()); // 后端生成当前时间

            // 3. 执行SQL插入（直接用String插入，MySQL自动转DATETIME）
            String sql = "INSERT INTO weigui_record (" +
                    "addtime, weiguiDanhao, xuehao, xingming, " +
                    "zixishiMingcheng, weiguiShijian, weiguiLeixing, " +
                    "weiguiBeizhu, isValid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rows = jdbcTemplate.update(sql,
                    addtime,        // 新增时间（String）
                    weiguiDanhao,   // 违规单号
                    xuehao,         // 学号
                    xingming,       // 姓名
                    zixishiMingcheng,// 自习室名称
                    weiguiShijian,  // 违规时间（前端传的String）
                    weiguiLeixing,  // 违规类型
                    weiguiBeizhu,   // 违规备注
                    1               // 有效标记
            );

            // 4. 构造响应
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            if (rows > 0) {
                return new ResponseEntity<>(R.ok("新增违规记录成功"), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(R.error(500, "新增失败：数据库无影响行数"), headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("新增违规记录异常", e);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            String errorMsg = "新增失败：";
            if (e.getMessage().contains("Incorrect datetime value")) {
                errorMsg += "日期格式错误，请使用yyyy-MM-dd HH:mm:ss";
            } else {
                errorMsg += e.getMessage();
            }
            return new ResponseEntity<>(R.error(500, errorMsg), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 列表查询：手动映射结果，返回Map格式
    @GetMapping("/list")
    public ResponseEntity<R> getWeiguiList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String weiguiLeixing) {
        try {
            // 1. 拼接查询SQL
            StringBuilder sql = new StringBuilder("SELECT id, addtime, weiguiDanhao, xuehao, xingming, " +
                    "zixishiMingcheng, weiguiShijian, weiguiLeixing, weiguiBeizhu, isValid " +
                    "FROM weigui_record WHERE 1=1 ");

            if (xuehao != null && !xuehao.isEmpty()) {
                sql.append("AND xuehao = '").append(xuehao.trim()).append("' ");
            }
            if (weiguiLeixing != null && !weiguiLeixing.isEmpty()) {
                sql.append("AND weiguiLeixing = '").append(weiguiLeixing).append("' ");
            }
            sql.append("AND isValid = 1 ");

            // 2. 分页处理
            int start = (page - 1) * size;
            sql.append("LIMIT ").append(start).append(",").append(size);

            // 3. 手动查询并映射结果（避免实体类解析）
            List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString());

            // 4. 统计总数
            StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM weigui_record WHERE 1=1 ");
            if (xuehao != null && !xuehao.isEmpty()) {
                countSql.append("AND xuehao = '").append(xuehao.trim()).append("' ");
            }
            if (weiguiLeixing != null && !weiguiLeixing.isEmpty()) {
                countSql.append("AND weiguiLeixing = '").append(weiguiLeixing).append("' ");
            }
            countSql.append("AND isValid = 1");
            Integer total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class);

            // 5. 构造响应
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return new ResponseEntity<>(
                    R.ok()
                            .put("data", records)
                            .put("total", total)
                            .put("page", page)
                            .put("size", size),
                    headers,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            logger.error("查询违规记录异常", e);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return new ResponseEntity<>(R.error(500, "查询失败：" + e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除违规记录
    @PostMapping("/delete")
    public ResponseEntity<R> deleteWeiguiRecord(@RequestParam Long id) {
        try {
            String sql = "DELETE FROM weigui_record WHERE id = ?";
            int rows = jdbcTemplate.update(sql, id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            if (rows > 0) {
                return new ResponseEntity<>(R.ok("删除违规记录成功"), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(R.error(404, "删除失败：记录不存在或已删除"), headers, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("删除违规记录异常", e);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return new ResponseEntity<>(R.error(500, "删除失败：" + e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}