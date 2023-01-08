package com.hc.clients;

import com.hc.param.PageParam;
import com.hc.param.ProductCollectParam;
import com.hc.param.ProductIdParam;
import com.hc.param.ProductSaveParam;
import com.hc.pojo.Product;
import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@FeignClient(value = "product-service")
public interface ProductClient {

    /**
     * 搜索服务调用，进行全部数据查询，用于搜索数据库同步数据
     *
     * @return
     */
    @GetMapping("/product/list")
    List<Product> allList();

    @PostMapping("/product/collect/list")
    R productIds(@RequestBody @Validated ProductCollectParam productCollectParam);

    /**
     * 根据商品ID查询商品信息
     *
     * @param productIdParam
     * @return
     */
    @PostMapping("/product/cart/detail")
    Product cdetail(@RequestBody @Validated ProductIdParam productIdParam);

    /**
     * 根据商品ID查询商品集合
     *
     * @param productCollectParam
     * @return
     */
    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody @Validated ProductCollectParam productCollectParam);

    @PostMapping("/product/admin/count")
    Long adminCount(@RequestBody Integer categoryId);

    @PostMapping("/product/admin/list")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/product/admin/save")
    R adminSave(@RequestBody ProductSaveParam productSaveParam);

    @PostMapping("/product/admin/update")
    R adminUpdate(@RequestBody Product product);

    @PostMapping("/product/admin/remove")
    public R adminRemove(@RequestBody Integer productId);
}
