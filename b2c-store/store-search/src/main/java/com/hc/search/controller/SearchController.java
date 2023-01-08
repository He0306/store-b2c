package com.hc.search.controller;

import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.search.service.SearchService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam) {
        return searchService.search(productSearchParam);
    }

    /**
     * 同步调用，进行商品插入，覆盖更新的
     *
     * @param product
     * @return
     */
    @PostMapping("/save")
    public R saveProduct(@RequestBody Product product) throws IOException {
        return searchService.saveProduct(product);
    }

    @PostMapping("/remove")
    public R removeProduct(@RequestBody Integer productId) throws IOException {
        return searchService.remove(productId);
    }
}
