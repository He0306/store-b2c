package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: 何超
 * @date: 2022/11/13
 */
@Data
public class UserLoginParam {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
