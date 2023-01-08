package com.hc.clients;

import com.hc.param.PageParam;
import com.hc.param.ProductHotParam;
import com.hc.pojo.Category;
import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable("categoryName") String categoryName);

    @PostMapping("/category/hots")
    R hotsCategory(@RequestBody @Validated ProductHotParam productHotParam);

    @GetMapping("/category/list")
    R list();

    @PostMapping("/category/admin/list")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/category/admin/save")
    R save(@RequestBody Category category);

    @PostMapping("/category/admin/remove")
    R adminRemove(@RequestBody Integer categoryId);

    @PostMapping("/update")
    R update(@RequestBody Category category);
}
