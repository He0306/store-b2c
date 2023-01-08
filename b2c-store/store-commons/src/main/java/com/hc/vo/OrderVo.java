package com.hc.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hc.pojo.Order;
import lombok.Data;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
//查询订单需要返回结果
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderVo extends Order {

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_picture")
    private String productPicture;

}