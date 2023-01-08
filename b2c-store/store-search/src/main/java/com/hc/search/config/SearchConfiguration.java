package com.hc.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 何超
 * @date: 2022/11/15
 * 消息队列配置
 */
@Configuration
public class SearchConfiguration {

    /**
     * mq序列化方式，选择json！
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * es客户端添加到ioc容器
     *
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client =
                new RestHighLevelClient(
                        RestClient.builder(HttpHost.create("http://127.0.0.1:9200")));

        return client;
    }
}
