package com.hc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hc.pojo.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Data
public class AddressParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;
}
