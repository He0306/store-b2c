package com.hc.admin.controller;

import com.hc.admin.service.OrderService;
import com.hc.param.PageParam;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public R list(PageParam pageParam) {
        return orderService.list(pageParam);
    }
}
