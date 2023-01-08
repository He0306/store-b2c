package com.hc.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.param.OrderParam;
import com.hc.param.PageParam;
import com.hc.pojo.Order;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
public interface OrderService extends IService<Order> {

    /**
     * 订单数据保存业务
     *
     * @param orderParam
     * @return
     */
    R save(OrderParam orderParam);

    /**
     * 分组查询购物车订单数据
     *
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 检查订单中是否有商品引用
     *
     * @param productId
     * @return
     */
    R check(Integer productId);

    /**
     * 订单管理查询数据
     *
     * @param pageParam
     * @return
     */
    R adminList(PageParam pageParam);
}
