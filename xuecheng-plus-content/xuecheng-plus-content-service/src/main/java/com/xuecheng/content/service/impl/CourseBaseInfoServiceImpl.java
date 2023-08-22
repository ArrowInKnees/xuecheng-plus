package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.*;
import com.xuecheng.content.mapper.*;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.*;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName CourseBaseInfoServiceImpl
 * @Description 课程信息管理业务接口实现类
 * @Author sdy
 * @Date 2023/8/17 9:01
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());
        // 分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        // 获取数据总记录数 counts
        long total = pageResult.getTotal();
        // 构建结果集  List<T> list, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        //  参数的合法性校验
        /*if (StringUtils.isBlank(dto.getName())) {
//            throw new RuntimeException("课程名称为空");
            XueChengPlusException.cast("课程名称为空");
        }
        if (StringUtils.isBlank(dto.getMt())) {
            throw new XueChengPlusException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getSt())) {
            throw new XueChengPlusException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getGrade())) {
            throw new XueChengPlusException("课程等级为空");
        }
        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new XueChengPlusException("教育模式为空");
        }
        if (StringUtils.isBlank(dto.getUsers())) {
            throw new XueChengPlusException("适应人群为空");
        }
        if (StringUtils.isBlank(dto.getCharge())) {
            throw new XueChengPlusException("收费规则为空");
        }*/

        //  向课程基本信息表course_base写入数据
        //  新增对象
        CourseBase courseBaseNew = new CourseBase();
        //  将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto, courseBaseNew); // 只要属性名称一致就可以拷贝，注：如果dto(原始对象)为空，courseBaseNew有值，那么空将会覆盖值
        //  机构id
        courseBaseNew.setCompanyId(companyId);
        //  添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //  设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //  设置发布状态
        courseBaseNew.setStatus("203001");
        //  插入课程基本信息表
        int baseInsert = courseBaseMapper.insert(courseBaseNew);
        //  主键课程的id
        Long courseId = courseBaseNew.getId();

        //  向课程营销系courese_market写入数据
        //  课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        //  两个对象的属性名一致，类型一致
        BeanUtils.copyProperties(dto, courseMarketNew);
        courseMarketNew.setId(courseId);
        //  向数据库保存课程营销信息
        int marketInsert = saveCourseMarket(courseMarketNew);
        //  只要有一个插入不成功抛出异常
        if (baseInsert <= 0 || marketInsert <= 0) {
            log.error("新增课程过出错:{}", dto);
            XueChengPlusException.cast("新增课程程过失败");
        }
        //  查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseId);
    }

    /**
     * @param courseId
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @Description 根据课程id查询课程基本信息，包括基本信息和营销信息
     */
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        //  从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        //  从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //  组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        //  拷贝属性
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        //  查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt()); // 一级分类
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt()); // 二级分类
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;
    }

    /**
     * @param companyId     教学机构id
     * @param editCourseDto 课程信息
     * @return
     * @description 修改课程信息
     */
    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        //  校验
        //  课程id
        Long courseId = editCourseDto.getId();
        //  查询课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }

        //  校验本机构只能修改本机构的课程
        if (!companyId.equals(courseBase.getCompanyId())) {
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        //  封装基本信息的数据
        BeanUtils.copyProperties(editCourseDto, courseBase);
        //  更新，设置更新时间
        courseBase.setChangeDate(LocalDateTime.now());
        //  更新课程基本信息
        int i = courseBaseMapper.updateById(courseBase);
        if (i <= 0) {
            XueChengPlusException.cast("修改课程失败");
        }

        //  封装营销信息的数据
        //  查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //  由于课程营销信息不是必填项，故这里先判断一下
        if (courseMarket == null) {
            courseMarket = new CourseMarket();
        }
        BeanUtils.copyProperties(editCourseDto, courseMarket);
        courseMarket.setId(courseId);
        //  获取课程收费状态并设置
        this.saveCourseMarket(courseMarket);

        return getCourseBaseInfo(courseId);
    }

    /**
     * 保存课程营销信息  逻辑:存在则更新，不存在则添加
     *
     * @param courseMarketNew
     * @return
     */
    private int saveCourseMarket(CourseMarket courseMarketNew) {
        //  收费规则  参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isBlank(charge)) {
            XueChengPlusException.cast("请选择收费规则");
        }
        //  收费规则为收费状态
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0) {
                XueChengPlusException.cast("课程为收费价格不能为空且必须大于0");
            }
        }
        //  根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarketNew.getId());
        if (courseMarketObj == null) {
            //  插入数据库
            return courseMarketMapper.insert(courseMarketNew);
        } else {
            //  更新
            BeanUtils.copyProperties(courseMarketNew, courseMarketObj);
            courseMarketObj.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }
}
