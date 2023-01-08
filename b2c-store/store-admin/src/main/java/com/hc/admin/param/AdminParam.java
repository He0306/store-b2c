package com.hc.admin.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Data
public class AdminParam {

    @Length(min = 6)
    private String userAccount; //账号
    @Length(min = 6)
    private String userPassword; //密码
    @NotBlank
    private String verCode;  //验证码
}
