package com.hc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@TableName("orders")
public class Order implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @JsonProperty("order_id")
    private Long orderId; //订单编号,选择使用时间戳

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_num")
    private Integer productNum;

    @JsonProperty("product_price")
    private Double productPrice;

    private String state;

    private String alipayNo;

    private String productName;

    @JsonProperty("order_time")
    private String orderTime;
}
