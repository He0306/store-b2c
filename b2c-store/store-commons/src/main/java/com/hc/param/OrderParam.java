package com.hc.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hc.vo.CartVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Data
public class OrderParam implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private Integer userId;

    private List<CartVo> products;
}
