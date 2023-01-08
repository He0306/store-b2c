package com.hc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Data
public class AddressListParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
