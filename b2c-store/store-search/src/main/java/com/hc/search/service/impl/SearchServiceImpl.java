package com.hc.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.doc.ProductDoc;
import com.hc.param.ProductSearchParam;
import com.hc.pojo.Product;
import com.hc.search.service.SearchService;
import com.hc.utils.R;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 更加关键字和分页进行数据库数据查询
     * 1、判断关键字是否为null  null查询全部  不为null  all 字段查询
     * 2、添加分页属性
     * 3、es查询
     * 4、结果处理
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        SearchRequest searchRequest = new SearchRequest("product");
        String search = productSearchParam.getSearch();
        if (StringUtils.isEmpty(search)) {
            //查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        } else {
            // 关键字查询
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }
        searchRequest.source().from((productSearchParam.getCurrentPage() - 1) * productSearchParam.getPageSize());
        searchRequest.source().size(productSearchParam.getPageSize());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }
        SearchHits hits = searchResponse.getHits();
        //查询符合的数量
        long total = hits.getTotalHits().value;
        //数据集合
        SearchHit[] hitsHits = hits.getHits();
        List<Product> productList = new ArrayList<>();
        //json处理器
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitsHit : hitsHits) {
            //查询的内容数据
            String sourceAsString = hitsHit.getSourceAsString();
            try {
                Product product = objectMapper.readValue(sourceAsString, Product.class);
                productList.add(product);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return R.ok("查询成功！", productList, total);
    }

    /**
     * 同步调用，进行商品插入，覆盖更新的
     *
     * @param product
     * @return
     */
    @Override
    public R saveProduct(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product");

        indexRequest.id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return R.ok("数据同步成功！");
    }

    /**
     * 进行es库删除
     *
     * @param productId
     * @return
     */
    @Override
    public R remove(Integer productId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("product");
        deleteRequest.id(productId.toString());
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return R.ok("es库的数据删除成功！");
    }
}
