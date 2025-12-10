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
 * 自习室
 */
@TableName("zixishi")
public class ZixishiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    public ZixishiEntity() {

    }

    public ZixishiEntity(T t) {
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
     * 名称
     */

    private String mingcheng;

    /**
     * 图片
     */

    private String tupian;

    /**
     * 规模
     */

    private String guimo;

    /**
     * 座位
     */

    private Integer zuowei;

    /**
     * 位置
     */

    private String weizhi;

    /**
     * 设施
     */

    private String sheshi;

    /**
     * 可约时间
     */

    private String keyueshijian;

    /**
     * 是否有课
     */

    private String shifouyouke;

    /**
     * 详情
     */

    private String xiangqing;


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
     * 设置：规模
     */
    public void setGuimo(String guimo) {
        this.guimo = guimo;
    }

    /**
     * 获取：规模
     */
    public String getGuimo() {
        return guimo;
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
     * 设置：位置
     */
    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    /**
     * 获取：位置
     */
    public String getWeizhi() {
        return weizhi;
    }

    /**
     * 设置：设施
     */
    public void setSheshi(String sheshi) {
        this.sheshi = sheshi;
    }

    /**
     * 获取：设施
     */
    public String getSheshi() {
        return sheshi;
    }

    /**
     * 设置：可约时间
     */
    public void setKeyueshijian(String keyueshijian) {
        this.keyueshijian = keyueshijian;
    }

    /**
     * 获取：可约时间
     */
    public String getKeyueshijian() {
        return keyueshijian;
    }

    /**
     * 设置：是否有课
     */
    public void setShifouyouke(String shifouyouke) {
        this.shifouyouke = shifouyouke;
    }

    /**
     * 获取：是否有课
     */
    public String getShifouyouke() {
        return shifouyouke;
    }

    /**
     * 设置：详情
     */
    public void setXiangqing(String xiangqing) {
        this.xiangqing = xiangqing;
    }

    /**
     * 获取：详情
     */
    public String getXiangqing() {
        return xiangqing;
    }

}
