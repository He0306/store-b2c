package com.hc.search.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.clients.ProductClient;
import com.hc.doc.ProductDoc;
import com.hc.pojo.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Component
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ProductClient productClient;

    private String indexStr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    /**
     * 需要在此方法，完成es数据的同步
     * 1、判断下es中product索引是否存在
     * 2、不存在，Java代码创建一个
     * 3、存在删除原来的数据
     * 4、查询商品全部数据
     * 5、进行es库的更新工作[插入]
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1、判断es中是否存在product索引
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        // 2、判断处理
        if (exists) {
            //存在 删除原有数据
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("product");
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery()); //全部删除
            restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        } else {
            //不存在 创建新的索引表即可
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(indexStr, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }

        // 3、查询全部商品数据
        List<Product> productList = productClient.allList();

        // 4、批量数据插入
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Product product : productList) {
            ProductDoc productDoc = new ProductDoc(product);
            //用于插入数据
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            //productDoc转换为JSON放入
            String json = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(json, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
