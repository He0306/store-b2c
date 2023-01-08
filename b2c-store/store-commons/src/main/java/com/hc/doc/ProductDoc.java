package com.hc.doc;

import com.hc.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product {

    private String all;

    public ProductDoc(Product product) {
        super(product.getProductId(), product.getProductName(),
                product.getCategoryId(), product.getProductTitle(),
                product.getProductIntro(), product.getProductPicture(),
                product.getProductPrice(), product.getProductSellingPrice(),
                product.getProductNum(), product.getProductSales());
        this.all = product.getProductName() + product.getProductTitle() + product.getProductIntro();
    }
}
