package com.hc.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminOrderVo implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("product_num")
    private Integer productNum; //商品种类

    @JsonProperty("order_num")
    private Integer orderNum; //订单中商品数量

    @JsonProperty("order_price")
    private Double orderPrice; //订单金额

    @JsonProperty("order_time")
    private Long orderTime; //订单时间
}

