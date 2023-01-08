package com.hc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Data
public class CartSaveParam {

    @NotNull
    @JsonProperty("product_id")
    private Integer productId;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
