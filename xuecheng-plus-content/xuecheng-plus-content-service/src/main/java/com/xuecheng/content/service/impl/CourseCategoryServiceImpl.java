package com.xuecheng.content.service.impl;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.service.CourseCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //  调用mapper递归查询出分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        //  找到每个节点的子节点，最终封装成List<CourseCategoryTreeDto>

        //  先将list转成map.key就是节点id, value就是CourseCategoryTreeDto对象，目的就是为了方便从map获取节点，filter(item->!id.equals(item.getId()))排除根节点
        Map<String, CourseCategoryTreeDto> map = courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        //  定义一个list作为最终返回的List
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();
        //  从头遍历List<CourseCategoryTreeDto>，一边遍历一边找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).forEach(item->{
            if (item.getParentid().equals(id)){
                courseCategoryList.add(item);
            }
            //  找到节点的父节点
            CourseCategoryTreeDto courseCategoryParent = map.get(item.getParentid());
            if (courseCategoryParent!=null){
                if (courseCategoryParent.getChildrenTreeNodes()==null){
                    //  如果该父节点的ChildrenTreeNodes属性为空要new一个集合，因为要向该集合中放它的子节点
                    courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //  找到每个节点的子节点放在父节点的childrenTreeNodes属性中
                courseCategoryParent.getChildrenTreeNodes().add(item);
            }
        });

        return courseCategoryList;
    }
}
