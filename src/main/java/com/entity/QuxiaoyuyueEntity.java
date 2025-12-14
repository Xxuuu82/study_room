package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 取消预约
 */
@TableName("quxiaoyuyue") // 确保与数据库表名完全一致
public class QuxiaoyuyueEntity implements Serializable { // 移除多余的泛型<T>
    private static final long serialVersionUID = 1L;

    public QuxiaoyuyueEntity() {

    }

    // 保留拷贝构造方法，但移除泛型依赖
    public QuxiaoyuyueEntity(Object t) { // 参数改为Object，兼容原有逻辑
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 自习室序号
     */
    private Integer zixishiid;

    /**
     * 座位
     */
    private Integer zuowei;

    /**
     * 预约单号
     */
    private String yuyuedanhao;

    /**
     * 名称
     */
    private String mingcheng;

//    /**
//     * 图片（数据库无此字段的话，需添加@TableField(exist = false)）
//     */
//    @TableField(exist = false) // 关键：如果数据库quxiaoyuyue表没有tupian列，必须加这个注解！
//    private String tupian;

    /**
     * 预约开始时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 补充时间格式，避免解析异常
    private Date yuyue_start;

    /**
     * 预约结束时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 补充时间格式
    private Date yuyue_end;

    /**
     * 取消时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 补充时间格式
    private Date quxiaoshijian;

    /**
     * 取消原因
     */
    private String quxiaoyuanyin;

    /**
     * 学号
     */
    private String xuehao;

    /**
     * 姓名
     */
    private String xingming;

    /**
     * 手机
     */
    private String shouji;

    /**
     * 跨表用户id
     */
    private Long crossuserid;

    /**
     * 跨表主键id
     */
    private Long crossrefid;

    /**
     * 审核回复
     */
    private String shhf;

    /**
     * 用户id
     */
    private Long userid;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 补充时间格式
    private Date addtime;

    // ========== 所有get/set方法保持不变 ==========
    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setZixishiid(Integer zixishiid) {
        this.zixishiid = zixishiid;
    }

    public Integer getZixishiid() {
        return zixishiid;
    }

    public void setZuowei(Integer zuowei) {
        this.zuowei = zuowei;
    }

    public Integer getZuowei() {
        return zuowei;
    }

    public void setYuyuedanhao(String yuyuedanhao) {
        this.yuyuedanhao = yuyuedanhao;
    }

    public String getYuyuedanhao() {
        return yuyuedanhao;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getMingcheng() {
        return mingcheng;
    }

//    public void setTupian(String tupian) {
//        this.tupian = tupian;
//    }
//
//    public String getTupian() {
//        return tupian;
//    }

    public void setYuyue_start(Date yuyue_start) {
        this.yuyue_start = yuyue_start;
    }

    public Date getYuyue_start() {
        return yuyue_start;
    }

    public void setYuyue_end(Date yuyue_end) {
        this.yuyue_end = yuyue_end;
    }

    public Date getYuyue_end() {
        return yuyue_end;
    }

    public void setQuxiaoshijian(Date quxiaoshijian) {
        this.quxiaoshijian = quxiaoshijian;
    }

    public Date getQuxiaoshijian() {
        return quxiaoshijian;
    }

    public void setQuxiaoyuanyin(String quxiaoyuanyin) {
        this.quxiaoyuanyin = quxiaoyuanyin;
    }

    public String getQuxiaoyuanyin() {
        return quxiaoyuanyin;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getXingming() {
        return xingming;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getShouji() {
        return shouji;
    }

    public void setCrossuserid(Long crossuserid) {
        this.crossuserid = crossuserid;
    }

    public Long getCrossuserid() {
        return crossuserid;
    }

    public void setCrossrefid(Long crossrefid) {
        this.crossrefid = crossrefid;
    }

    public Long getCrossrefid() {
        return crossrefid;
    }

    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    public String getShhf() {
        return shhf;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getUserid() {
        return userid;
    }

}