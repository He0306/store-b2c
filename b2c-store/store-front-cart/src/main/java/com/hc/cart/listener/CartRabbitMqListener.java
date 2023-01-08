package com.hc.cart.listener;

import com.hc.cart.service.CartService;
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
public class CartRabbitMqListener {

    @Autowired
    private CartService cartService;

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(name = "clear.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "clear.cart")
    })
    public void clear(List<Integer> cartIds) {
        cartService.clearIds(cartIds);
    }
}
