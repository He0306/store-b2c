package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Data
public class ProductPromoParam {

    @NotBlank
    private String categoryName;
}
