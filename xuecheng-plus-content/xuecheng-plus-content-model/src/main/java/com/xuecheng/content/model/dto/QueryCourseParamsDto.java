package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName QueryCourseParamsDto
 * @Description 课程查询参数Dto     dto:数据传输对象，一般接受对象
 * @Author sdy
 * @Date 2023/8/16 10:58
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
