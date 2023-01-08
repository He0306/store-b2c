package com.hc.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Data
public class OrderToProduct implements Serializable {

    @JsonProperty("product_id")
    private Integer productId;

    private Integer num;
}
