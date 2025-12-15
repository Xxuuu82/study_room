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
@TableName("quxiaoyuyue")
public class QuxiaoyuyueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public QuxiaoyuyueEntity() {
    }

    public QuxiaoyuyueEntity(Object t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @TableId
    private Long id;

    private Integer zixishiid;
    private Integer zuowei;
    private String yuyuedanhao;
    private String mingcheng;

    // 映射到数据库列 yuyue_start
    @TableField("yuyue_start")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yuyueStart;

    // 映射到数据库列 yuyue_end
    @TableField("yuyue_end")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yuyueEnd;

    // 映射到数据库列 quxiaoshijian
    @TableField("quxiaoshijian")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date quxiaoshijian;

    private String quxiaoyuanyin;
    private String xuehao;
    private String xingming;
    private String shouji;
    private Long crossuserid;
    private Long crossrefid;
    private String shhf;
    private Long userid;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    // ========== Getter / Setter ==========
    public Date getAddtime() { return addtime; }
    public void setAddtime(Date addtime) { this.addtime = addtime; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getZixishiid() { return zixishiid; }
    public void setZixishiid(Integer zixishiid) { this.zixishiid = zixishiid; }

    public Integer getZuowei() { return zuowei; }
    public void setZuowei(Integer zuowei) { this.zuowei = zuowei; }

    public String getYuyuedanhao() { return yuyuedanhao; }
    public void setYuyuedanhao(String yuyuedanhao) { this.yuyuedanhao = yuyuedanhao; }

    public String getMingcheng() { return mingcheng; }
    public void setMingcheng(String mingcheng) { this.mingcheng = mingcheng; }

    public Date getYuyueStart() { return yuyueStart; }
    public void setYuyueStart(Date yuyueStart) { this.yuyueStart = yuyueStart; }

    public Date getYuyueEnd() { return yuyueEnd; }
    public void setYuyueEnd(Date yuyueEnd) { this.yuyueEnd = yuyueEnd; }

    public Date getQuxiaoshijian() { return quxiaoshijian; }
    public void setQuxiaoshijian(Date quxiaoshijian) { this.quxiaoshijian = quxiaoshijian; }

    public String getQuxiaoyuanyin() { return quxiaoyuanyin; }
    public void setQuxiaoyuanyin(String quxiaoyuanyin) { this.quxiaoyuanyin = quxiaoyuanyin; }

    public String getXuehao() { return xuehao; }
    public void setXuehao(String xuehao) { this.xuehao = xuehao; }

    public String getXingming() { return xingming; }
    public void setXingming(String xingming) { this.xingming = xingming; }

    public String getShouji() { return shouji; }
    public void setShouji(String shouji) { this.shouji = shouji; }

    public Long getCrossuserid() { return crossuserid; }
    public void setCrossuserid(Long crossuserid) { this.crossuserid = crossuserid; }

    public Long getCrossrefid() { return crossrefid; }
    public void setCrossrefid(Long crossrefid) { this.crossrefid = crossrefid; }

    public String getShhf() { return shhf; }
    public void setShhf(String shhf) { this.shhf = shhf; }

    public Long getUserid() { return userid; }
    public void setUserid(Long userid) { this.userid = userid; }
}