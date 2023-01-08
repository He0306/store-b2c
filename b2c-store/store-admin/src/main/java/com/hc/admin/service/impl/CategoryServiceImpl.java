package com.hc.admin.service.impl;

import com.hc.admin.service.CategoryService;
import com.hc.clients.CategoryClient;
import com.hc.param.PageParam;
import com.hc.pojo.Category;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryClient categoryClient;

    @Cacheable(value = "list.category", key = "#pageParam.currentPage + '-' + #pageParam.pageSize")
    @Override
    public R list(PageParam pageParam) {
        return categoryClient.listPage(pageParam);
    }

    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R save(Category category) {
        return categoryClient.save(category);
    }

    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R remove(Integer categoryId) {
        return categoryClient.adminRemove(categoryId);
    }

    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R update(Category category) {
        return categoryClient.update(category);
    }
}
