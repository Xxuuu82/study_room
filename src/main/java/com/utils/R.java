package com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据（适配前端格式，继承HashMap版本）
 * 适配项目上下文路径/study_room，兼容MySQL8.0，支持下划线转驼峰
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    // 构造方法：默认code=0（成功），msg默认"操作成功"
    public R() {
        super(); // 调用HashMap父类构造
        put("code", 0);
        put("msg", "操作成功"); // 默认成功提示，避免前端msg为空
    }

    /**
     * 通用错误（默认500+未知异常）
     */
    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    /**
     * 自定义错误提示（默认code=500）
     */
    public static R error(String msg) {
        return error(500, msg);
    }

    /**
     * 自定义错误码+提示
     * 关键优化：先清空默认值，再设置错误码/提示，避免覆盖
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.clear(); // 清空构造方法中默认的code=0和msg=操作成功
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 成功+自定义提示
     */
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg); // 覆盖默认的"操作成功"
        return r;
    }

    /**
     * 成功+自定义map数据
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * 通用成功（默认code=0，msg=操作成功）
     */
    public static R ok() {
        return new R();
    }

    /**
     * 链式添加数据（比如put("data", list)）
     * 重写保证返回R类型，支持链式调用
     */
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}