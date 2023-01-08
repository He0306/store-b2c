package com.hc.admin.controller;

import com.hc.admin.service.CategoryService;
import com.hc.param.PageParam;
import com.hc.pojo.Category;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public R list(PageParam pageParam) {
        return categoryService.list(pageParam);
    }

    @PostMapping("/save")
    public R save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Integer categoryId) {
        return categoryService.remove(categoryId);
    }

    @PostMapping("/update")
    public R update(@RequestBody Category category) {
        return categoryService.update(category);
    }


}
