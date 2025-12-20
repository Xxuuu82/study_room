package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data // 自动生成getter/setter（依赖Lombok）
@TableName("heimingdan") // 匹配数据库表名（必须和数据库表名一致）
public class HeiMingDan {
    @TableId(type = IdType.AUTO) // MyBatis-Plus的主键注解
    private Long id; // 主键

    private String xuehao; // 学号（匹配数据库字段xuehao）
    private String xingming; // 姓名（匹配数据库字段xingming）
    private Integer totalWeiguiCount; // 累计违规次数（匹配total_weigui_count）
    private Integer weiguiChifanCount; // 乱吃乱喝次数（匹配weigui_chifan_count）
    private Integer weiguiWeiqiandaoCount; // 未签到次数（匹配weigui_weiqiandao_count）
    private Integer weiguiXuanhuaCount; // 大声喧哗次数（匹配weigui_xuanhua_count）
    private Integer weiguiSuibiaobianCount; // 随地大小便次数（匹配weigui_suibiaobian_count）
    private String otherWeiguiDesc; // 其他违规说明（匹配other_weigui_desc）
    private String heimingdanYuanyin; // 拉黑原因（匹配heimingdan_yuanyin）
    private Date heimingdanStartTime; // 拉黑开始时间（匹配heimingdan_start_time）
    private Date heimingdanEndTime; // 拉黑结束时间（匹配heimingdan_end_time）
    private Integer shifoujiechu; // 是否解除（匹配shifoujiechu）
}