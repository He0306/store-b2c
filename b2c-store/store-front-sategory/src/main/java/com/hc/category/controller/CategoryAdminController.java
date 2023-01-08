package com.hc.category.controller;

import com.hc.category.service.CategoryService;
import com.hc.param.PageParam;
import com.hc.pojo.Category;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@RestController
@RequestMapping("/category")
public class CategoryAdminController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam) {
        return categoryService.listPage(pageParam);
    }

    @PostMapping("/admin/save")
    public R save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PostMapping("/admin/remove")
    public R adminRemove(@RequestBody Integer categoryId) {
        return categoryService.adminRemove(categoryId);
    }

    @PostMapping("/update")
    public R update(@RequestBody Category category) {
        return categoryService.update(category);
    }
}
