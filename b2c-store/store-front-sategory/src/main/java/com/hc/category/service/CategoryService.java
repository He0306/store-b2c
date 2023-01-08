package com.hc.category.service;

import com.hc.param.PageParam;
import com.hc.param.ProductHotParam;
import com.hc.pojo.Category;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
public interface CategoryService {

    /**
     * 根据类别名称，查询类别对象
     *
     * @param categoryName
     * @return
     */
    R byName(String categoryName);

    /**
     * 根据传入的热门类别名称集合，返回类别对应的id集合
     *
     * @param productHotParam
     * @return
     */
    R hotsCategory(ProductHotParam productHotParam);

    /**
     * 查询类别数据，进行返回
     *
     * @return
     */
    R list();

    /**
     * 分页查询全部分类
     *
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 添加类别
     *
     * @param category
     * @return
     */
    R save(Category category);

    /**
     * 删除数据
     *
     * @param categoryId
     * @return
     */
    R adminRemove(Integer categoryId);

    /**
     * 修改类别
     *
     * @param category
     * @return
     */
    R update(Category category);
}
