package com.hc.order.controller;

import com.hc.order.service.OrderService;
import com.hc.param.CartListParam;
import com.hc.param.OrderParam;
import com.hc.param.PageParam;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/save")
    public R save(@RequestBody OrderParam orderParam) {
        return orderService.save(orderParam);
    }

    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常，查询失败！");
        }
        return orderService.list(cartListParam.getUserId());
    }

    @PostMapping("/remove/check")
    public R removeCheck(@RequestBody Integer productId) {
        return orderService.check(productId);
    }

    @PostMapping("/admin/list")
    public R adminList(@RequestBody PageParam pageParam) {
        return orderService.adminList(pageParam);
    }
}
