package com.hc.param;

import com.hc.pojo.Product;
import lombok.Data;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@Data
public class ProductSaveParam extends Product {

    /**
     * 保存商品详情的图片地址！图片之间使用 + 拼接
     */
    private String pictures;
}
