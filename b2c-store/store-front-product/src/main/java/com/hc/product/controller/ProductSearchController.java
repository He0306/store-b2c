package com.hc.product.controller;

import com.hc.pojo.Product;
import com.hc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/product")
public class ProductSearchController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public List<Product> allList() {
        return productService.allList();
    }
}
