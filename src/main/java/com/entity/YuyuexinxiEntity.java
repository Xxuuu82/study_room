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
     * 预约时间
     */

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date yuyueshijian;

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
     * 设置：签到状态
     */
    public void setQiandaozhuangtai(String qiandaozhuangtai) {
        this.qiandaozhuangtai = qiandaozhuangtai;
    }

    /**
     * 获取：签到状态
     */
    public String getQiantuizhuangtai() {
        return qiantuizhuangtai;
    }

    /**
     * 设置：签退状态
     */
    public void setQiantuizhuangtai(String qiantuizhuangtai) {
        this.qiantuizhuangtai = qiantuizhuangtai;
    }

    /**
     * 获取：签退状态
     */
    public String getQiandaozhuangtai() {
        return qiandaozhuangtai;
    }

    /**
     * 设置：预约时间
     */
    public void setYuyueshijian(Date yuyueshijian) {
        this.yuyueshijian = yuyueshijian;
    }

    /**
     * 获取：预约时间
     */
    public Date getYuyueshijian() {
        return yuyueshijian;
    }

    /**
     * 设置：备注
     */
    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    /**
     * 获取：备注
     */
    public String getBeizhu() {
        return beizhu;
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
     * 设置：回复
     */
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    /**
     * 获取：回复
     */
    public String getShhf() {
        return shhf;
    }

}
