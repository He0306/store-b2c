package com.hc.statics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StaticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaticsApplication.class, args);
    }
}
