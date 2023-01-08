package com.hc.admin.service.impl;

import com.hc.admin.service.ProductService;
import com.hc.clients.ProductClient;
import com.hc.clients.SearchClient;
import com.hc.param.ProductSaveParam;
import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    SearchClient searchClient;

    @Autowired
    ProductClient productClient;

    @Cacheable(value = "list.product", key = "#productSearchParam.currentPage + '-' + #productSearchParam.pageSize + '-' + #productSearchParam.search")
    @Override
    public R list(ProductSearchParam productSearchParam) {
        return searchClient.searchProduct(productSearchParam);
    }

    /**
     * 进行商品数据保存
     *
     * @param productSaveParam
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R save(ProductSaveParam productSaveParam) {
        return productClient.adminSave(productSaveParam);
    }

    /**
     * 进行商品更新
     *
     * @param product
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R adminUpdate(Product product) {
        return productClient.adminUpdate(product);
    }

    /**
     * 商品移除
     *
     * @param productId
     * @return
     */
    @Override
    public R adminRemove(Integer productId) {
        return productClient.adminRemove(productId);
    }
}
