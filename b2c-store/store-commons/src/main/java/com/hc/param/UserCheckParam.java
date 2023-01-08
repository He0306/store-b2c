package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: 何超
 * @date: 2022/11/13
 */
@Data
public class UserCheckParam {

    @NotBlank
    private String userName;


}
