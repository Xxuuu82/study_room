package com.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ShenSu {
    private Long id;             // 主键ID
    private Date addtime;        // 记录创建时间
    private String shensuDanhao; // 申诉单号
    private String xuehao;       // 学号
    private String xingming;     // 姓名
    private Long weiguiRecordId; // 关联违规记录ID
    private String shensuNeirong;// 申诉内容
    private String shensuTupian; // 申诉图片
    private Date shensuShijian;  // 申诉时间
    private Integer chuliZhuangtai; // 处理状态：0-待处理，1-确认，2-驳回
    private String chuliRen;     // 处理人
    private String chuliBeizhu;  // 处理备注
}
