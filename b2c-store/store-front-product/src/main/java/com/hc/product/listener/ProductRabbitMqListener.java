package com.hc.product.listener;

import com.hc.product.service.ProductService;
import com.hc.to.OrderToProduct;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Component
public class ProductRabbitMqListener {

    @Autowired
    ProductService productService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "sub.queue"),
                    exchange = @Exchange("topic.ex"),
                    key = "sub.number"
            )
    })
    public void subNumber(List<OrderToProduct> orderToProducts) {
        productService.subNumber(orderToProducts);
    }
}
