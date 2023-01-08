package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Data
public class ProductHotParam {

    @NotEmpty
    private List<String> categoryName;
}
