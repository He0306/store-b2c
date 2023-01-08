package com.hc.admin.service;

import com.hc.param.PageParam;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
public interface OrderService {

    /**
     * 分页查询全部订单
     *
     * @param pageParam
     * @return
     */
    R list(PageParam pageParam);

}
