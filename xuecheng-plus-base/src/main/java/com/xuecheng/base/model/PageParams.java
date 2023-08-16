package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName PageParams
 * @Description 分页查询通用参数
 * @Author sdy
 * @Date 2023/8/16 10:46
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    //当前页码
    private Long pageNo = 1L;

    //每页记录数默认值
    private Long pageSize =10L;
}
