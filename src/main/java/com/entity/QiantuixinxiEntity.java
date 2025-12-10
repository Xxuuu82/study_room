package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


/**
 * 签退信息
 */
@TableName("qiantuixinxi")
public class QiantuixinxiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    public QiantuixinxiEntity() {

    }

    public QiantuixinxiEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
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
     * 自习时长
     */

    private Double zixishichang;
    /**
     * 名称
     */

    private String mingcheng;

    /**
     * 签退时间
     */

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date qiantuishijian;


    /**
     * 签退备注
     */

    private String qiantuibeizhu;

    /**
     * 学号
     */

    private String xuehao;

    /**
     * 手机
     */

    private String shouji;

    /**
     * 班级
     */

    private String banji;

    /**
     * 回复内容
     */

    private String shhf;


    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date addtime;

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

    /**
     * 设置：名称
     */
    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    /**
     * 获取：名称
     */
    public String getMingcheng() {
        return mingcheng;
    }

    /**
     * 设置：签退时间
     */
    public void setQiantuishijian(Date qiantuishijian) {
        this.qiantuishijian = qiantuishijian;
    }

    /**
     * 获取：签退时间
     */
    public Date getQiantuishijian() {
        return qiantuishijian;
    }


    /**
     * 设置：签退备注
     */
    public void setQiantuibeizhu(String qiantuibeizhu) {
        this.qiantuibeizhu = qiantuibeizhu;
    }

    /**
     * 获取：签退备注
     */
    public String getQiantuibeizhu() {
        return qiantuibeizhu;
    }

    /**
     * 设置：自习室序号
     */
    public void setZixishiid(Integer zixishiid) {
        this.zixishiid = zixishiid;
    }

    /**
     * 获取：自习室序号
     */
    public Integer getZixishiid() {
        return zixishiid;
    }

    /**
     * 设置：自习时长
     */
    public void setZixishichang(Double zixishichang) {
        this.zixishichang = zixishichang;
    }

    /**
     * 获取：自习时长
     */
    public Double getZixishichang() {
        return zixishichang;
    }

    /**
     * 设置：座位
     */
    public void setZuowei(Integer zuowei) {
        this.zuowei = zuowei;
    }

    /**
     * 获取：座位
     */
    public Integer getZuowei() {
        return zuowei;
    }

    /**
     * 设置：学号
     */
    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    /**
     * 获取：学号
     */
    public String getXuehao() {
        return xuehao;
    }

    /**
     * 设置：手机
     */
    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    /**
     * 获取：手机
     */
    public String getShouji() {
        return shouji;
    }

    /**
     * 设置：班级
     */
    public void setBanji(String banji) {
        this.banji = banji;
    }

    /**
     * 获取：班级
     */
    public String getBanji() {
        return banji;
    }

    /**
     * 设置：回复内容
     */
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    /**
     * 获取：回复内容
     */
    public String getShhf() {
        return shhf;
    }

}
