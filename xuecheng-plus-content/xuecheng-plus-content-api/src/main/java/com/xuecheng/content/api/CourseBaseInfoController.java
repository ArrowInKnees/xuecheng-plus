package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CourseBaseInfoController
 * @Description 课程信息编辑接口
 * @Author sdy
 * @Date 2023/8/16 11:40
 */
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RestController("/course") // 相当于Controller和ResponseBody
public class CourseBaseInfoController {

    @ApiOperation("课程查询接口")
    @PostMapping("/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams) {
        CourseBase courseBase = new CourseBase();
        courseBase.setName("测试名称");
        courseBase.setCreateDate(LocalDateTime.now());
        List<CourseBase> courseBases = new ArrayList();
        courseBases.add(courseBase);
        PageResult pageResult = new PageResult<CourseBase>(courseBases,10,1,10);
        return pageResult;
    }

}
