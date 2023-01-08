package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Data
public class ProductCollectParam {

    @NotEmpty
    private List<Integer> ids;
}
