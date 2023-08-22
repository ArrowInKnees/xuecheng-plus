package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @ClassName CourseBaseInfoService
 * @Description 课程基本信息管理业务接口
 * @Author sdy
 * @Date 2023/8/17 8:54
 */
public interface CourseBaseInfoService {
    /**
     * @param pageParams           分页参数
     * @param queryCourseParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     * @Description 课程分页查询接口
     */
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * @param companyId    教学机构id
     * @param addCourseDto 课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto 课程详细信息
     * @Description 添加课程基本信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * @param courseId 课程id
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @Description 根据课程id获取课程信息
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * @param companyId     教学机构id
     * @param editCourseDto 课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @Description 修改课程信息
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);

}
