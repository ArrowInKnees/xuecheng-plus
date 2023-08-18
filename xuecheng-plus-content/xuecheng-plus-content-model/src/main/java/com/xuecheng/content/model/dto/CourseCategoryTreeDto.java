package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CourseCategoryTreeDto
 * @Description 课程分类树型结点dto
 * @Author sdy
 * @Date 2023/8/17 13:50
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    private static final long serialVersionUID = 3949174185887481049L;
    //  子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;
}
