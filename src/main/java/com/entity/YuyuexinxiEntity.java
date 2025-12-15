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
 * 预约信息
 */
@TableName("yuyuexinxi")
public class YuyuexinxiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public YuyuexinxiEntity() {

    }

    public YuyuexinxiEntity(T t) {
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
     * 预约单号
     */
    private String yuyuedanhao;

    /**
     * 名称
     */
    private String mingcheng;



    /**
     * 自习室序号
     */
    private Integer zixishiid;

    /**
     * 座位
     */
    private Integer zuowei;

    /**
     * 签到状态
     */
    private String qiandaozhuangtai;

    /**
     * 签退状态
     */
    private String qiantuizhuangtai;

    /**
     * 预约开始时间（替换原单一预约时间）
     * 关键修复：加@TableField指定数据库字段名yuyue_start
     */
    @TableField("yuyue_start") // 映射数据库下划线字段
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yuyueStart;

    /**
     * 预约结束时间
     * 关键修复：加@TableField指定数据库字段名yuyue_end
     */
    @TableField("yuyue_end") // 映射数据库下划线字段
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yuyueEnd;

    /**
     * 备注
     */
    private String beizhu;

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
     * 审核回复
     */
    private String shhf;


    @TableField(exist = false)
    private Date qiandaoshijian; // 签到时间（从签到表关联）
    @TableField(exist = false)
    private Date qiantuishijian; // 签退时间（从签退表关联）
    @TableField(exist = false)
    private Double zixishichang; // 自习时长（从签退表关联）

// 需生成对应的get/set方法


    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    // ========== getter/setter 方法 ==========
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

    public String getYuyuedanhao() {
        return yuyuedanhao;
    }

    public void setYuyuedanhao(String yuyuedanhao) {
        this.yuyuedanhao = yuyuedanhao;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

//    public String getTupian() {
//        return tupian;
//    }
//
//    public void setTupian(String tupian) {
//        this.tupian = tupian;
//    }

    public Integer getZuowei() {
        return zuowei;
    }

    public void setZuowei(Integer zuowei) {
        this.zuowei = zuowei;
    }

    public Integer getZixishiid() {
        return zixishiid;
    }

    public void setZixishiid(Integer zixishiid) {
        this.zixishiid = zixishiid;
    }

    public String getQiandaozhuangtai() {
        return qiandaozhuangtai;
    }

    public void setQiandaozhuangtai(String qiandaozhuangtai) {
        this.qiandaozhuangtai = qiandaozhuangtai;
    }

    public String getQiantuizhuangtai() {
        return qiantuizhuangtai;
    }

    public void setQiantuizhuangtai(String qiantuizhuangtai) {
        this.qiantuizhuangtai = qiantuizhuangtai;
    }

    // 预约开始时间 getter/setter
    public Date getYuyueStart() {
        return yuyueStart;
    }

    public void setYuyueStart(Date yuyueStart) {
        this.yuyueStart = yuyueStart;
    }

    // 预约结束时间 getter/setter
    public Date getYuyueEnd() {
        return yuyueEnd;
    }

    public void setYuyueEnd(Date yuyueEnd) {
        this.yuyueEnd = yuyueEnd;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getShouji() {
        return shouji;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getShhf() {
        return shhf;
    }

    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    public Double getZixishichang() {
        return zixishichang;
    }

    public void setZixishichang(Double zixishichang) {
        this.zixishichang = zixishichang;
    }

    public Date getQiandaoshijian() {
        return qiandaoshijian;
    }

    public void setQiandaoshijian(Date qiandaoshijian) {
        this.qiandaoshijian = qiandaoshijian;
    }

    public Date getQiantuishijian() {
        return qiantuishijian;
    }

    public void setQiantuishijian(Date qiantuishijian) {
        this.qiantuishijian = qiantuishijian;
    }
}