package com.hc.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.clients.ProductClient;
import com.hc.order.mapper.OrderMapper;
import com.hc.order.service.OrderService;
import com.hc.param.OrderParam;
import com.hc.param.PageParam;
import com.hc.param.ProductCollectParam;
import com.hc.pojo.AliPay;
import com.hc.pojo.Order;
import com.hc.pojo.Product;
import com.hc.to.OrderToProduct;
import com.hc.utils.R;
import com.hc.vo.AdminOrderVo;
import com.hc.vo.CartVo;
import com.hc.vo.OrderVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ProductClient productClient;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 订单数据保存业务
     * 1、将购物车数据转换成订单数据
     * 2、进行订单批量插入
     * 3、商品库存修改消息
     * 4、发送购物车库存修改消息
     *
     * @param orderParam
     * @return
     */
    @Transactional
    @Override
    public R save(OrderParam orderParam) {
        //准备数据
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        //生成数据
        Integer userId = orderParam.getUserId();
        long orderId = System.currentTimeMillis();
        for (CartVo cartVo : orderParam.getProducts()) {
            cartIds.add(cartVo.getId());   //保存删除的购物车项的ID
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProduct.setNum(cartVo.getNum());
            orderToProducts.add(orderToProduct);  //商品服务修改的数据
            AliPay aliPay = new AliPay();
            aliPay.setOrderId(String.valueOf(orderId));
            aliPay.setProductName(cartVo.getProductName());
            aliPay.setProductPrice(cartVo.getPrice());

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(new Date().toString());
            order.setState("已支付");
            order.setUserId(userId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order);
        }
        //订单数据批量保存
        saveBatch(orderList);
        //发送购物车消息
        rabbitTemplate.convertAndSend("topic.ex", "clear.cart", cartIds);
        //发送商品服务消息
        rabbitTemplate.convertAndSend("topic.ex", "sub.number", orderToProducts);
        return R.ok("订单保存成功！");
    }

    /**
     * 分组查询购物车订单数据
     * 1、查询用户对应的全部订单项
     * 2、利用stream进行订单分组
     * 3、查询订单的全部商品集合，并stream组成map
     * 4、封装返回的OrderVo对象
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId);
        queryWrapper.eq(Order::getState,"已支付");
        List<Order> list = list(queryWrapper);
        //分组
        Map<Long, List<Order>> orderMap = list.stream().collect(Collectors.groupingBy(Order::getOrderId));
        //查询商品数据
        List<Integer> productIds = list.stream().map(Order::getProductId).collect(Collectors.toList());
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();
        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order, orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }
        return R.ok("订单数据获取成功！", result);

    }

    /**
     * 检查订单中是否有商品引用
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getProductId, productId);
        Long count = orderMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("购物车中：" + count + "项引用该商品，不能删除！");
        }
        return R.ok("无购物车引用，可以删除！");
    }

    /**
     * 订单管理查询数据
     *
     * @param pageParam
     * @return
     */
    @Override
    public R adminList(PageParam pageParam) {
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        int pageSize = pageParam.getPageSize();
        Long total = orderMapper.selectCount(null);
        List<AdminOrderVo> adminOrderVoList = orderMapper.selectAdminOrder(offset, pageSize);
        return R.ok("查询成功！", adminOrderVoList, total);
    }
}
