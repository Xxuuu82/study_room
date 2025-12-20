package com.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.entity.HeiMingDan;
import com.service.HeiMingDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/heimingdan")
public class HeiMingDanController {

    @Autowired
    private HeiMingDanService heiMingDanService; // 注入服务接口

    /**
     * 获取黑名单列表（分页+筛选）
     * 适配MyBatis-Plus 2.3的Page
     */
    @GetMapping("/list")
    public Map<String, Object> getBlackList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String xuehao,
            @RequestParam(required = false) String shifoujiechu) {
        // MyBatis-Plus 2.3的Page构造
        Page<HeiMingDan> pageParam = new Page<>(page, size);
        // 筛选参数
        Map<String, Object> params = new HashMap<>();
        params.put("xuehao", xuehao);
        params.put("shifoujiechu", shifoujiechu);
        // 调用服务层方法
        Page<HeiMingDan> blackList = heiMingDanService.getBlackList(pageParam, params);

        // 构造返回结果（适配前端格式）
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "查询成功");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("records", blackList.getRecords());
        dataMap.put("total", blackList.getTotal());
        result.put("data", dataMap);
        return result;
    }

    /**
     * 解除黑名单
     */
    @PostMapping("/jiechu")
    public Map<String, Object> jiechuBlackList(@RequestBody Map<String, String> params) {
        String xuehao = params.get("xuehao");
        boolean success = heiMingDanService.jiechuBlackList(xuehao);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 0);
            result.put("msg", "解除成功");
        } else {
            result.put("code", 1);
            result.put("msg", "解除失败");
        }
        return result;
    }
}