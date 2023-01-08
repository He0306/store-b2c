package com.hc.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.category.mapper.CategoryMapper;
import com.hc.category.service.CategoryService;
import com.hc.clients.ProductClient;
import com.hc.param.PageParam;
import com.hc.param.ProductHotParam;
import com.hc.pojo.Category;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductClient productClient;

    @Override
    public R byName(String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, categoryName);
        Category category = categoryMapper.selectOne(queryWrapper);
        if (category == null) {
            return R.fail("类别查询失败！");
        }
        return R.ok("类别查询成功！", category);
    }

    @Override
    public R hotsCategory(ProductHotParam productHotParam) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Category::getCategoryName, productHotParam.getCategoryName());
        queryWrapper.select(Category::getCategoryId);
        List<Object> ids = categoryMapper.selectObjs(queryWrapper);
        return R.ok("类别集合查询成功！", ids);
    }

    @Override
    public R list() {
        List<Category> categoryList = categoryMapper.selectList(null);
        return R.ok("查询成功！", categoryList);
    }

    @Override
    public R listPage(PageParam pageParam) {
        IPage<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = categoryMapper.selectPage(page, null);
        return R.ok("分页查询成功！", page.getRecords(), page.getTotal());
    }

    @Override
    public R save(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, category.getCategoryName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("分类名称已存在，添加失败！");
        }
        categoryMapper.insert(category);
        return R.ok("类别添加成功！");
    }

    @Override
    public R adminRemove(Integer categoryId) {
        Long count = productClient.adminCount(categoryId);
        if (count > 0) {
            return R.fail("类别删除失败，有：" + count + "商品正在引用!");
        }
        categoryMapper.deleteById(categoryId);
        return R.ok("类别删除成功！");
    }

    @Override
    public R update(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName, category.getCategoryName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("类别已存在，修改失败！");
        }
        categoryMapper.updateById(category);
        return R.ok("修改类别成功！");
    }
}
