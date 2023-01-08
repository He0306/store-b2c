package com.hc.category.controller;

import com.hc.category.service.CategoryService;
import com.hc.param.ProductHotParam;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable("categoryName") String categoryName) {
        if (StringUtils.isEmpty(categoryName)) {
            return R.fail("类别名称为空，无法查询数据！");
        }
        return categoryService.byName(categoryName);
    }

    /**
     * 热门类别ID查询
     *
     * @param productHotParam
     * @param result
     * @return
     */
    @PostMapping("/hots")
    public R hotsCategory(@RequestBody @Validated ProductHotParam productHotParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("类别集合查询失败！");
        }
        return categoryService.hotsCategory(productHotParam);
    }

    @GetMapping("/list")
    public R list() {
        return categoryService.list();
    }
}
