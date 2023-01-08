package com.hc.product.controller;

import com.hc.param.ProductCollectParam;
import com.hc.param.ProductIdParam;
import com.hc.pojo.Product;
import com.hc.product.service.ProductService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/product")
public class ProductCartController {

    @Autowired
    ProductService productService;

    @PostMapping("/cart/detail")
    public Product cdetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        R detail = productService.detail(productIdParam.getProductID());
        return (Product) detail.getData();
    }

    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result) {
        if (result.hasErrors()) {
            return new ArrayList<Product>();
        }
        return productService.cartList(productCollectParam.getIds());
    }
}
