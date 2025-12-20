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
    // 新增调试日志标记
    private static final Logger logger = LoggerFactory.getLogger(WeiguiRecordController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 新增：根据ID查询单条违规记录（申诉页面查看详情用）
    @GetMapping("/detail")
    public ResponseEntity<R> getWeiguiDetail(@RequestParam Long id) {
        try {
            String sql = "SELECT id, addtime, weiguiDanhao, xuehao, xingming, " +
                    "zixishiMingcheng, weiguiShijian, weiguiLeixing, weiguiBeizhu, isValid " +
                    "FROM weigui_record WHERE id = ? AND isValid = 1";

            Map<String, Object> record = jdbcTemplate.queryForMap(sql, id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return new ResponseEntity<>(
                    R.ok().put("data", record),
                    headers,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            logger.error("查询违规记录详情异常", e);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return new ResponseEntity<>(R.error(500, "查询失败：" + e.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增违规记录 + 强制自动拉黑（移除次数判断，确保触发）
    @PostMapping("/add")
    public ResponseEntity<R> addWeiguiRecord(@RequestBody Map<String, Object> paramMap) {
        // 核心调试日志：确认方法被调用
        logger.info("=== 新增违规记录接口被调用，入参：{} ===", paramMap);
        try {
            // 1. 从Map中获取前端参数
            String xuehao = paramMap.get("xuehao").toString().trim();
            String xingming = paramMap.get("xingming").toString().trim();
            String zixishiMingcheng = paramMap.get("zixishiMingcheng").toString().trim();
            String weiguiShijian = paramMap.get("weiguiShijian").toString();
            String weiguiLeixing = paramMap.get("weiguiLeixing").toString();
            String weiguiBeizhu = paramMap.get("weiguiBeizhu") != null ? paramMap.get("weiguiBeizhu").toString().trim() : "";

            logger.info("解析参数完成：学号={}，姓名={}，违规类型={}", xuehao, xingming, weiguiLeixing);

            // 2. 生成违规单号和当前时间
            String weiguiDanhao = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String addtime = sdf.format(new Date());

            // 3. 执行SQL插入违规记录
            String insertSql = "INSERT INTO weigui_record (" +
                    "addtime, weiguiDanhao, xuehao, xingming, " +
                    "zixishiMingcheng, weiguiShijian, weiguiLeixing, " +
                    "weiguiBeizhu, isValid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rows = jdbcTemplate.update(insertSql,
                    addtime, weiguiDanhao, xuehao, xingming,
                    zixishiMingcheng, weiguiShijian, weiguiLeixing,
                    weiguiBeizhu, 1);

            logger.info("违规记录插入结果：影响行数={}", rows);

            // 4. 强制自动拉黑逻辑（核心修改：移除次数≥3判断，确保触发）
            if (rows > 0) {
                // 4.1 统计该学生所有有效违规次数
                String countTotalSql = "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND isValid = 1";
                Integer totalWeiguiCount = jdbcTemplate.queryForObject(countTotalSql, Integer.class, xuehao);
                logger.info("学生{}累计有效违规次数：{}", xuehao, totalWeiguiCount);

                // 4.2 检查是否已在未解除的黑名单中
                String checkBlackSql = "SELECT CAST(COUNT(*) AS UNSIGNED) FROM heimingdan WHERE xuehao = ? AND shifoujiechu = 0";
                Integer isInBlack = jdbcTemplate.queryForObject(checkBlackSql, Integer.class, xuehao);
                logger.info("学生{}是否已在黑名单：{}", xuehao, isInBlack > 0 ? "是" : "否");

                // 4.3 未在黑名单则强制插入
                if (isInBlack == 0) {
                    // 统计各类违规次数
                    Integer chifanCount = jdbcTemplate.queryForObject(
                            "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND weiguiLeixing = 'chifan' AND isValid = 1",
                            Integer.class, xuehao
                    );
                    Integer weiqiandaoCount = jdbcTemplate.queryForObject(
                            "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND weiguiLeixing = 'weiqiandao' AND isValid = 1",
                            Integer.class, xuehao
                    );
                    Integer xuanhuaCount = jdbcTemplate.queryForObject(
                            "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND weiguiLeixing = 'xuanhua' AND isValid = 1",
                            Integer.class, xuehao
                    );
                    Integer suibiaobianCount = jdbcTemplate.queryForObject(
                            "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND weiguiLeixing = 'suibiaobian' AND isValid = 1",
                            Integer.class, xuehao
                    );
                    Integer otherCount = jdbcTemplate.queryForObject(
                            "SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND weiguiLeixing = 'other' AND isValid = 1",
                            Integer.class, xuehao
                    );

                    logger.info("各类违规次数统计：乱吃乱喝={}，未签到={}，大声喧哗={}，随地大小便={}，其他={}",
                            chifanCount, weiqiandaoCount, xuanhuaCount, suibiaobianCount, otherCount);

                    // 构造拉黑原因
                    String heimingdanYuanyin = String.format(
                            "教室乱吃乱喝%d次+未签到%d次+大声喧哗%d次+随地大小便%d次+其他违规%d次，累计%d次违规达到拉黑阈值",
                            chifanCount, weiqiandaoCount, xuanhuaCount, suibiaobianCount, otherCount, totalWeiguiCount
                    );

                    // 计算拉黑时间（当前时间+7天）
                    Date now = new Date();
                    Date endTime = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000L);

                    // 插入黑名单记录
                    String insertBlackSql = "INSERT INTO heimingdan (" +
                            "xuehao, xingming, total_weigui_count, " +
                            "weigui_chifan_count, weigui_weiqiandao_count, weigui_xuanhua_count, weigui_suibiaobian_count, " +
                            "other_weigui_desc, heimingdan_yuanyin, " +
                            "heimingdan_start_time, heimingdan_end_time, shifoujiechu) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    int blackRows = jdbcTemplate.update(insertBlackSql,
                            xuehao, xingming, totalWeiguiCount,
                            chifanCount, weiqiandaoCount, xuanhuaCount, suibiaobianCount,
                            otherCount > 0 ? "其他违规" : null, heimingdanYuanyin,
                            sdf.format(now), sdf.format(endTime), 0
                    );

                    logger.info("黑名单记录插入结果：影响行数={}，学生{}已强制加入黑名单", blackRows, xuehao);
                }
            }

            // 5. 构造响应
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            if (rows > 0) {
                return new ResponseEntity<>(R.ok("新增违规记录成功，当前累计违规次数：" +
                        jdbcTemplate.queryForObject("SELECT CAST(COUNT(*) AS UNSIGNED) FROM weigui_record WHERE xuehao = ? AND isValid = 1", Integer.class, xuehao)),
                        headers, HttpStatus.OK);
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

    // 列表查询：保持原有逻辑不变
    @GetMapping("/list")
    public ResponseEntity<R> getWeiguiList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String weiguiLeixing) {
        try {
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

            int start = (page - 1) * size;
            sql.append("LIMIT ").append(start).append(",").append(size);

            List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString());

            StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM weigui_record WHERE 1=1 ");
            if (xuehao != null && !xuehao.isEmpty()) {
                countSql.append("AND xuehao = '").append(xuehao.trim()).append("' ");
            }
            if (weiguiLeixing != null && !weiguiLeixing.isEmpty()) {
                countSql.append("AND weiguiLeixing = '").append(weiguiLeixing).append("' ");
            }
            countSql.append("AND isValid = 1");
            Integer total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class);

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

    // 删除违规记录：保持原有逻辑不变
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