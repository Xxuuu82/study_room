package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
 * 说明：
 * - update 方法不再使用 @RequestBody 注入（避免 Spring 在方法入口尝试解析导致 415）
 * - update 方法会根据 Content-Type 手动解析 JSON 或从 request.getParameterMap() 读取表单字段
 * - 兼容 application/json、application/x-www-form-urlencoded、multipart/form-data
 */
@RestController
@RequestMapping("/xuesheng")
public class XueshengController {
    @Autowired
    private XueshengService xueshengService;

    @Autowired
    private TokenService tokenService;

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

    @IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody XueshengEntity xuesheng) {
        XueshengEntity u = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("xuehao", xuesheng.getXuehao()));
        if (u != null) {
            return R.error("注册用户已存在");
        }
        Long uId = new Date().getTime();
        xuesheng.setId(uId);
        xueshengService.insert(xuesheng);
        return R.ok();
    }

    @RequestMapping("/logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }

    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("userId");
        XueshengEntity u = xueshengService.selectById(id);
        return R.ok().put("data", u);
    }

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

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, XueshengEntity xuesheng,
                  HttpServletRequest request) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuesheng), params), params));
        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, XueshengEntity xuesheng,
                  HttpServletRequest request) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuesheng), params), params));
        return R.ok().put("data", page);
    }

    @RequestMapping("/lists")
    public R list(XueshengEntity xuesheng) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        ew.allEq(MPUtil.allEQMapPre(xuesheng, "xuesheng"));
        return R.ok().put("data", xueshengService.selectListView(ew));
    }

    @RequestMapping("/query")
    public R query(XueshengEntity xuesheng) {
        EntityWrapper<XueshengEntity> ew = new EntityWrapper<XueshengEntity>();
        ew.allEq(MPUtil.allEQMapPre(xuesheng, "xuesheng"));
        XueshengView xueshengView = xueshengService.selectView(ew);
        return R.ok("查询学生成功").put("data", xueshengView);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        XueshengEntity xuesheng = xueshengService.selectById(id);
        return R.ok().put("data", xuesheng);
    }

    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        XueshengEntity xuesheng = xueshengService.selectById(id);
        return R.ok().put("data", xuesheng);
    }

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
     * update — 统一入口，兼容多种 Content-Type，避免 415
     */
    @PostMapping("/update")
    @Transactional
    public R update(HttpServletRequest request) {
        XueshengEntity xuesheng = null;
        try {
            String contentType = request.getContentType();
            if (contentType != null && contentType.toLowerCase().contains("application/json")) {
                // JSON 请求，手动读取并解析
                String body = readInputStreamToString(request.getInputStream());
                if (body != null && !body.trim().isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    xuesheng = mapper.readValue(body, XueshengEntity.class);
                }
            } else {
                // 非 JSON（表单或 multipart）——先尝试参数映射
                Map<String, String[]> paramMap = request.getParameterMap();
                if (paramMap != null && !paramMap.isEmpty()) {
                    xuesheng = new XueshengEntity();
                    if (paramMap.containsKey("id")) {
                        try {
                            String v = paramMap.get("id")[0];
                            if (v != null && !v.isEmpty()) xuesheng.setId(Long.parseLong(v));
                        } catch (Exception ignored) {}
                    }
                    if (paramMap.containsKey("xuehao")) xuesheng.setXuehao(paramMap.get("xuehao")[0]);
                    if (paramMap.containsKey("mima")) xuesheng.setMima(paramMap.get("mima")[0]);
                    if (paramMap.containsKey("xingming")) xuesheng.setXingming(paramMap.get("xingming")[0]);
                    if (paramMap.containsKey("shouji")) xuesheng.setShouji(paramMap.get("shouji")[0]);
                    if (paramMap.containsKey("banji")) xuesheng.setBanji(paramMap.get("banji")[0]);
                    if (paramMap.containsKey("touxiang")) xuesheng.setTouxiang(paramMap.get("touxiang")[0]);
                } else {
                    // 兜底：尝试从流读取（可能是 JSON）
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

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        xueshengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

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

        Wrapper<XueshengEntity> wrapper = new EntityWrapper<XueshengEntity>();
        if (map.get("remindstart") != null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }

        int count = xueshengService.selectCount(wrapper);
        return R.ok().put("count", count);
    }

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
}