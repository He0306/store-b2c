package com.hc.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    void updateState(@Param("orderId") Long tradeNo,@Param("state") String state,@Param("orderTime") String gmtPayment,@Param("alipayNo") String alipayTradeNo);
}
