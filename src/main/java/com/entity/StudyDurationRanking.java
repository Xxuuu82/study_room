package com.entity;

// 替换为MP 2.3的注解包路径

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * 自习时长排名实体类
 */
@Data
@TableName("study_duration_ranking") // MP 2.3的TableName注解
public class StudyDurationRanking {
    /** 学号（主键） */
    @TableId(type = IdType.INPUT) // MP 2.3的TableId注解
    @NotBlank(message = "学号不能为空")
    @Pattern(regexp = "^[0-9]{9}$", message = "学号必须是9位数字")
    private String studentId;

    /** 学生姓名 */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 自习时长（总分钟数） */
    @TableField("study_duration_min") // 核心：映射数据库字段名
    @Positive(message = "自习时长必须大于0")
    private Integer studyDurationMin;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号必须是11位数字")
    private String phone;

    /** 班级 */
    @TableField("class") // 核心：映射数据库class字段到className属性
    @NotBlank(message = "班级不能为空")
    private String className;
}