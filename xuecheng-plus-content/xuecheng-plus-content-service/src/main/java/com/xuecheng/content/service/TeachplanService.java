package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @ClassName TeachplanService
 * @Description 课程基本信息管理业务接口
 * @Author sdy
 * @Date 2023/8/22 15:50
 */
public interface TeachplanService {
    /**
     * @description     查询课程计划树型结构
     * @param courseId  课程 id
     * @return List<TeachplanDto>
     * @date 2022/9/9 11:13
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

}
