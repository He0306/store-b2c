package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Data
public class AddressRemoveParam {

    @NotNull
    private Integer userId;
}
