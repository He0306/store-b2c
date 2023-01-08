package com.hc.product.controller;

import com.hc.param.ProductCollectParam;
import com.hc.product.service.ProductService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/product")
public class ProductCollectController {

    @Autowired
    ProductService productService;

    @PostMapping("/collect/list")
    public R productIds(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.ok("没有收藏数据!");
        }
        return productService.ids(productCollectParam.getIds());
    }
}
