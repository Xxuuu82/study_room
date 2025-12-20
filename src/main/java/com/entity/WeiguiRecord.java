package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 违规记录实体类（所有日期字段为String）
 */
@Data
@TableName("weigui_record")
public class WeiguiRecord {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键

    // 核心修改：日期字段改为String
    private String addtime; // 新增时间（String）
    private String weiguiDanhao; // 违规单号
    private String xuehao; // 学号
    private String xingming; // 姓名
    private Integer zixishiId; // 自习室ID
    private String zixishiMingcheng; // 自习室名称
    private Integer zuoWei; // 座位号
    // 核心修改：日期字段改为String
    private String weiguiShijian; // 违规时间（String）
    private String weiguiLeixing; // 违规类型
    private String weiguiLeixingName; // 违规类型名称
    private String weiguiBeizhu; // 违规备注
    private String weiguiTupian; // 违规图片
    private String luRuRen; // 录入人
    private Integer isValid; // 是否有效
}