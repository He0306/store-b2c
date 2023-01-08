package com.hc.admin;

import com.hc.clients.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@EnableFeignClients(clients = {UserClient.class, CategoryClient.class, SearchClient.class, ProductClient.class, OrderClient.class})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
