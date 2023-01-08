package com.hc.admin.service;

import com.hc.param.PageParam;
import com.hc.pojo.Category;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
public interface CategoryService {

    /**
     * 分页查询全部数据
     *
     * @param pageParam
     * @return
     */
    R list(PageParam pageParam);

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    R save(Category category);

    /**
     * 删除类别
     *
     * @param categoryId
     * @return
     */
    R remove(Integer categoryId);

    /**
     * 修改类别
     *
     * @param category
     * @return
     */
    R update(Category category);
}
