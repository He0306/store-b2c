package com.hc.admin.controller;

import com.hc.admin.service.ProductService;
import com.hc.admin.util.AliYunOSSUtils;
import com.hc.param.ProductSaveParam;
import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    AliYunOSSUtils aliYunOSSUtils;

    @GetMapping("/list")
    public R list(ProductSearchParam productSearchParam) {
        return productService.list(productSearchParam);
    }

    @PostMapping("/upload")
    public R upload(MultipartFile img) throws Exception {
        String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replace("-", "") + filename;
        String contentType = img.getContentType();
        byte[] content = img.getBytes();
        int hours = 1000;
        String url = aliYunOSSUtils.uploadImage(filename, content, contentType, hours);
        return R.ok("图片上传成功！", url);
    }

    @PostMapping("/save")
    public R adminSave(ProductSaveParam productSaveParam) {
        return productService.save(productSaveParam);
    }

    @PostMapping("/update")
    public R adminUpdate(Product product) {
        return productService.adminUpdate(product);
    }

    @PostMapping("/remove")
    public R adminRemove(@RequestBody Integer productId) {
        return productService.adminRemove(productId);
    }
}
