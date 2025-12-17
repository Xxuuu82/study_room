package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.XueshengEntity;
import com.entity.view.XueshengView;
import com.service.TokenService;
import com.service.XueshengService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 学生
 * 后端接口
 *
 * 说明（本文件针对你要求做的最小且充分的修改）：
 * - 将 register 接口改为手工解析请求（不使用 @RequestBody），兼容 application/json、application/x-www-form-urlencoded、multipart/form-data，
 *   从而彻底避免 415 Unsupported Media Type。
 * - 保持其它接口逻辑不变（包含之前对 update 的兼容修改）。
 * - 读取请求体使用 Java8 兼容实现（readInputStreamToString）。
 */
@RestController
@RequestMapping("/xuesheng")
public class XueshengController {
    @Autowired
    private XueshengService xueshengService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", username));
        if (u == null || !u.getMima().equals(password)) {
            return R.error("账号或密码不正确");
        }

        String token = tokenService.generateToken(u.getId(), username, "xuesheng", "学生");
        return R.ok().put("token", token);
    }

    /**
     * 注册（兼容所有 Content-Type）
     */
    @IgnoreAuth
    @PostMapping("/register")
    public R register(HttpServletRequest request) {
        try {
            XueshengEntity xuesheng = null;
            String ct = request.getContentType();

            // 1) JSON
            if (ct != null && ct.toLowerCase().contains("application/json")) {
                String body = readInputStreamToString(request.getInputStream());
                if (body != null && !body.trim().isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    xuesheng = mapper.readValue(body, XueshengEntity.class);
                }
            } else {
                // 2) 参数表单（application/x-www-form-urlencoded）或 multipart/form-data
                Map<String, String[]> paramMap = request.getParameterMap();
                if (paramMap != null && !paramMap.isEmpty()) {
                    xuesheng = buildFromParamMap(paramMap);
                } else {
                    // 3) 兜底：尝试以流解析为 JSON
                    String body = readInputStreamToString(request.getInputStream());
                    if (body != null && !body.trim().isEmpty()) {
                        ObjectMapper mapper = new ObjectMapper();
                        xuesheng = mapper.readValue(body, XueshengEntity.class);
                    }
                }
            }

            if (xuesheng == null) {
                return R.error("请求参数为空或格式不被识别");
            }

            // 必填校验（xuehao, mima, xingming 在数据库为 NOT NULL）
            if (xuesheng.getXuehao() == null || xuesheng.getXuehao().trim().isEmpty()) {
                return R.error("学号不能为空");
            }
            if (xuesheng.getMima() == null || xuesheng.getMima().trim().isEmpty()) {
                return R.error("密码不能为空");
            }
            if (xuesheng.getXingming() == null || xuesheng.getXingming().trim().isEmpty()) {
                return R.error("姓名不能为空");
            }

            // 检查是否已存在用户
            XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", xuesheng.getXuehao()));
            if (u != null) {
                return R.error("注册用户已存在");
            }

            // 生成ID并插入（保持原有行为）
            xuesheng.setId(new Date().getTime());
            xueshengService.insert(xuesheng);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("注册失败：" + e.getMessage());
        }
    }

    /**
     * 退出
     */
    @RequestMapping("/logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }

    /**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("userId");
        XueshengEntity u = xueshengService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 密码重置
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", username));
        if (u == null) {
            return R.error("账号不存在");
        }
        u.setMima("123456");
        xueshengService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, XueshengEntity xuesheng,
                  HttpServletRequest request) {
        PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(new EntityWrapper<XueshengEntity>(), xuesheng), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, XueshengEntity xuesheng,
                  HttpServletRequest request) {
        PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(new EntityWrapper<XueshengEntity>(), xuesheng), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R lists(XueshengEntity xuesheng) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        ew.allEq(MPUtil.allEQMapPre(xuesheng, "xuesheng"));
        return R.ok().put("data", xueshengService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XueshengEntity xuesheng) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        ew.allEq(MPUtil.allEQMapPre(xuesheng, "xuesheng"));
        XueshengView xueshengView = xueshengService.selectView(ew);
        return R.ok("查询学生成功").put("data", xueshengView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        XueshengEntity xuesheng = xueshengService.selectById(id);
        return R.ok().put("data", xuesheng);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        XueshengEntity xuesheng = xueshengService.selectById(id);
        return R.ok().put("data", xuesheng);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XueshengEntity xuesheng, HttpServletRequest request) {
        xuesheng.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", xuesheng.getXuehao()));
        if (u != null) {
            return R.error("用户已存在");
        }
        xuesheng.setId(new Date().getTime());
        xueshengService.insert(xuesheng);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XueshengEntity xuesheng, HttpServletRequest request) {
        xuesheng.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", xuesheng.getXuehao()));
        if (u != null) {
            return R.error("用户已存在");
        }
        xuesheng.setId(new Date().getTime());
        xueshengService.insert(xuesheng);
        return R.ok();
    }

    /**
     * 修改（兼容多种 Content-Type）
     */
    @PostMapping("/update")
    @Transactional
    public R update(HttpServletRequest request) {
        XueshengEntity xuesheng = null;
        try {
            String contentType = request.getContentType();
            if (contentType != null && contentType.toLowerCase().contains("application/json")) {
                String body = readInputStreamToString(request.getInputStream());
                if (body != null && !body.trim().isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    xuesheng = mapper.readValue(body, XueshengEntity.class);
                }
            } else {
                Map<String, String[]> paramMap = request.getParameterMap();
                if (paramMap != null && !paramMap.isEmpty()) {
                    xuesheng = new XueshengEntity();
                    if (paramMap.containsKey("id")) {
                        String[] vs = paramMap.get("id");
                        if (vs != null && vs.length > 0 && vs[0] != null && !vs[0].isEmpty()) {
                            try {
                                xuesheng.setId(Long.parseLong(vs[0]));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                    if (paramMap.containsKey("xuehao")) xuesheng.setXuehao(paramMap.get("xuehao")[0]);
                    if (paramMap.containsKey("mima")) xuesheng.setMima(paramMap.get("mima")[0]);
                    if (paramMap.containsKey("xingming")) xuesheng.setXingming(paramMap.get("xingming")[0]);
                    if (paramMap.containsKey("shouji")) xuesheng.setShouji(paramMap.get("shouji")[0]);
                    if (paramMap.containsKey("banji")) xuesheng.setBanji(paramMap.get("banji")[0]);
                    if (paramMap.containsKey("touxiang")) xuesheng.setTouxiang(paramMap.get("touxiang")[0]);
                } else {
                    String body = readInputStreamToString(request.getInputStream());
                    if (body != null && !body.trim().isEmpty()) {
                        ObjectMapper mapper = new ObjectMapper();
                        xuesheng = mapper.readValue(body, XueshengEntity.class);
                    }
                }
            }

            if (xuesheng == null) {
                return R.error("请求参数为空，无法更新");
            }

            if (xuesheng.getId() == null) {
                return R.error("更新失败：请提供用户ID");
            }

            xueshengService.updateById(xuesheng);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        xueshengService.deleteBatchIds(Arrays.asList(ids));
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

        EntityWrapper<XueshengEntity> wrapper = new EntityWrapper<XueshengEntity>();
        if (map.get("remindstart") != null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }

        int count = xueshengService.selectCount(wrapper);
        return R.ok().put("count", count);
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

    // ====== 辅助方法：从 paramMap 构建 XueshengEntity（对常用字段做解析和类型转换） ======
    private XueshengEntity buildFromParamMap(Map<String, String[]> paramMap) {
        XueshengEntity e = new XueshengEntity();
        try {
            if (paramMap.containsKey("id")) {
                String v = paramMap.get("id")[0];
                if (v != null && !v.isEmpty()) e.setId(Long.parseLong(v));
            }
            if (paramMap.containsKey("xuehao")) e.setXuehao(paramMap.get("xuehao")[0]);
            if (paramMap.containsKey("mima")) e.setMima(paramMap.get("mima")[0]);
            if (paramMap.containsKey("xingming")) e.setXingming(paramMap.get("xingming")[0]);
            if (paramMap.containsKey("xingbie")) e.setXingbie(paramMap.get("xingbie")[0]);
            if (paramMap.containsKey("touxiang")) e.setTouxiang(paramMap.get("touxiang")[0]);
            if (paramMap.containsKey("shouji")) e.setShouji(paramMap.get("shouji")[0]);
            if (paramMap.containsKey("banji")) e.setBanji(paramMap.get("banji")[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
}