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
 * 签到信息
 */
@TableName("qiandaoxinxi")
public class QiandaoxinxiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    public QiandaoxinxiEntity() {

    }

    public QiandaoxinxiEntity(T t) {
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
     * 签到时间
     */

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date qiandaoshijian;


    /**
     * 签到备注
     */

    private String qiandaobeizhu;

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

    private String yuyuedanhao;   //关联预约单号

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
     * 设置：签到时间
     */
    public void setQiandaoshijian(Date qiandaoshijian) {
        this.qiandaoshijian = qiandaoshijian;
    }

    /**
     * 获取：签到时间
     */
    public Date getQiandaoshijian() {
        return qiandaoshijian;
    }


    /**
     * 设置：签到备注
     */
    public void setQiandaobeizhu(String qiandaobeizhu) {
        this.qiandaobeizhu = qiandaobeizhu;
    }

    /**
     * 获取：签到备注
     */
    public String getQiandaobeizhu() {
        return qiandaobeizhu;
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


    // 新增 get/set 方法
    public String getYuyuedanhao() {
        return yuyuedanhao;
    }
    public void setYuyuedanhao(String yuyuedanhao) {
        this.yuyuedanhao = yuyuedanhao;
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
