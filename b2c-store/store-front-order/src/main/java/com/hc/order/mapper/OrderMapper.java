package com.hc.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.pojo.Order;
import com.hc.vo.AdminOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<AdminOrderVo> selectAdminOrder(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
