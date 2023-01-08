package com.hc.pay.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.pay.mapper.OrderMapper;
import com.hc.pay.service.OrderService;
import com.hc.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/12/16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;
}
