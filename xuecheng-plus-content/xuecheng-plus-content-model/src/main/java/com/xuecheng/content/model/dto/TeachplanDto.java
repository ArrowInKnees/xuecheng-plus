package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName TeachplanDto
 * @Description 课程计划树型结构 dto
 * @Author sdy
 * @Date 2023/8/22 14:04
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {
    //课程计划关联的媒资信息
    private TeachplanMedia teachplanMedia;
    // 子结点 小章节
    private List<TeachplanDto> teachPlanTreeNodes;
}
