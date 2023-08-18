package com.xuecheng.content.api;


import com.xuecheng.base.model.*;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CourseBaseInfoController
 * @Description 课程信息编辑接口
 * @Author sdy
 * @Date 2023/8/16 11:40
 */
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RestController // 相当于Controller和ResponseBody
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams) {
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);
        return courseBasePageResult;
    }

    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody AddCourseDto addCourseDto){
        //  机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);
    }
}
