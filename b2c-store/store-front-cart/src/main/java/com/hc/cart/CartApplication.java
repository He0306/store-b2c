package com.hc.cart;

import com.hc.clients.ProductClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {ProductClient.class})
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
