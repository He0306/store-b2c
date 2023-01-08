package com.hc.clients;

import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@FeignClient(value = "search-service")
public interface SearchClient {

    @PostMapping("/search/product")
    R searchProduct(@RequestBody ProductSearchParam productSearchParam);

    /**
     * 同步调用，进行商品插入，覆盖更新的
     *
     * @param product
     * @return
     */
    @PostMapping("/search/save")
    R saveProduct(@RequestBody Product product);

    @PostMapping("/search/remove")
    R removeProduct(@RequestBody Integer productId);
}
