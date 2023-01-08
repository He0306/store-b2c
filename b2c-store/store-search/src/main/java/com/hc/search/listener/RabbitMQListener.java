package com.hc.search.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.doc.ProductDoc;
import com.hc.pojo.Product;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Component
public class RabbitMQListener {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 监听并且插入数据的方法
     *
     * @param product
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "insert.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "product.insert"
                    )
            }
    )
    public void insert(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product");

        indexRequest.id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 监听并且删除数据的方法
     *
     * @param productId
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "remove.queue"),
                            exchange = @Exchange("topic.ex"),
                            key = "product.remove"
                    )
            }
    )
    public void remove(Integer productId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("product");
        deleteRequest.id(productId.toString());
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}
