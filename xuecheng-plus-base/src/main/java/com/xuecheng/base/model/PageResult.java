package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页查询结果模型类
 * @Author sdy
 * @Date 2023/8/16 11:04
 */
@Data
@ToString
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 8623468181983035254L;
    // 数据列表
    private List<T> items;

    //总记录数
    private long counts;

    //当前页码
    private long page;

    //每页记录数
    private long pageSize;
}
