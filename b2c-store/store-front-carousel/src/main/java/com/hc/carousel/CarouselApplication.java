package com.hc.carousel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CarouselApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class, args);
    }
}
