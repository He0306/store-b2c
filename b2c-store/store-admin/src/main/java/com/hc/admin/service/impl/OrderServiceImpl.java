package com.hc.admin.service.impl;

import com.hc.admin.service.OrderService;
import com.hc.clients.OrderClient;
import com.hc.param.PageParam;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderClient orderClient;

    /**
     * 分页查询全部订单
     *
     * @param pageParam
     * @return
     */
    @Override
    public R list(PageParam pageParam) {
        return orderClient.adminList(pageParam);
    }
}
