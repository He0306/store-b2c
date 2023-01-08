package com.hc.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Data
public class ProductIdsParam {

    @NotNull
    private List<Integer> categoryID;

    private int currentPage = 1;

    private int pageSize = 15;
}
