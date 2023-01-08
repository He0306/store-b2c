package com.hc.search.service;

import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.utils.R;

import java.io.IOException;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
public interface SearchService {

    /**
     * 根据关键字和分页进行数据库数据查询
     *
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 同步调用，进行商品插入，覆盖更新的
     *
     * @param product
     * @return
     */
    R saveProduct(Product product) throws IOException;

    /**
     * 进行es库删除
     *
     * @param productId
     * @return
     */
    R remove(Integer productId) throws IOException;
}
