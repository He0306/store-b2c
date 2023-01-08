package com.hc.product.controller;

import com.hc.param.PageParam;
import com.hc.param.ProductSaveParam;
import com.hc.pojo.Product;
import com.hc.product.service.ProductService;
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
@RequestMapping("/product")
public class ProductAdminController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId) {
        return productService.adminCount(categoryId);
    }

    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam) {
        return productService.listPage(pageParam);
    }

    @PostMapping("/admin/save")
    public R adminSave(@RequestBody ProductSaveParam productSaveParam) {
        return productService.adminSave(productSaveParam);
    }

    @PostMapping("/admin/update")
    public R adminUpdate(@RequestBody Product product) {
        return productService.adminUpdate(product);
    }

    @PostMapping("/admin/remove")
    public R adminRemove(@RequestBody Integer productId) {
        return productService.adminRemove(productId);
    }
}
