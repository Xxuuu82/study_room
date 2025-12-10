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
 * 取消预约
 */
@TableName("quxiaoyuyue")
public class QuxiaoyuyueEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    public QuxiaoyuyueEntity() {

    }

    public QuxiaoyuyueEntity(T t) {
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
     * 预约单号
     */
    private String yuyuedanhao;

    /**
     * 名称
     */

    private String mingcheng;

    /**
     * 图片
     */

    private String tupian;

    /**
     * 预约时间
     */

    private String yuyueshijian;

    /**
     * 取消时间
     */

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
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
     * 设置：预约单号
     */
    public void setYuyuedanhao(String yuyuedanhao) {
        this.yuyuedanhao = yuyuedanhao;
    }

    /**
     * 获取：预约单号
     */
    public String getYuyuedanhao() {
        return yuyuedanhao;
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
     * 设置：图片
     */
    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    /**
     * 获取：图片
     */
    public String getTupian() {
        return tupian;
    }

    /**
     * 设置：预约时间
     */
    public void setYuyueshijian(String yuyueshijian) {
        this.yuyueshijian = yuyueshijian;
    }

    /**
     * 获取：预约时间
     */
    public String getYuyueshijian() {
        return yuyueshijian;
    }

    /**
     * 设置：取消时间
     */
    public void setQuxiaoshijian(Date quxiaoshijian) {
        this.quxiaoshijian = quxiaoshijian;
    }

    /**
     * 获取：取消时间
     */
    public Date getQuxiaoshijian() {
        return quxiaoshijian;
    }

    /**
     * 设置：取消原因
     */
    public void setQuxiaoyuanyin(String quxiaoyuanyin) {
        this.quxiaoyuanyin = quxiaoyuanyin;
    }

    /**
     * 获取：取消原因
     */
    public String getQuxiaoyuanyin() {
        return quxiaoyuanyin;
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
     * 设置：姓名
     */
    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    /**
     * 获取：姓名
     */
    public String getXingming() {
        return xingming;
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
     * 设置：跨表用户id
     */
    public void setCrossuserid(Long crossuserid) {
        this.crossuserid = crossuserid;
    }

    /**
     * 获取：跨表用户id
     */
    public Long getCrossuserid() {
        return crossuserid;
    }

    /**
     * 设置：跨表主键id
     */
    public void setCrossrefid(Long crossrefid) {
        this.crossrefid = crossrefid;
    }

    /**
     * 获取：跨表主键id
     */
    public Long getCrossrefid() {
        return crossrefid;
    }

    /**
     * 设置：审核回复
     */
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    /**
     * 获取：审核回复
     */
    public String getShhf() {
        return shhf;
    }

    /**
     * 设置：用户id
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * 获取：用户id
     */
    public Long getUserid() {
        return userid;
    }

}
