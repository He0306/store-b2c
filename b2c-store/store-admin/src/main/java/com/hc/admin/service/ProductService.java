package com.hc.admin.service;

import com.hc.param.ProductSaveParam;
import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
public interface ProductService {

    /**
     * 分页查询全部数据
     *
     * @param productSearchParam
     * @return
     */
    R list(ProductSearchParam productSearchParam);

    /**
     * 进行商品数据保存
     *
     * @param productSaveParam
     * @return
     */
    R save(ProductSaveParam productSaveParam);

    /**
     * 进行商品更新
     *
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    /**
     * 商品移除
     *
     * @param productId
     * @return
     */
    R adminRemove(Integer productId);
}
