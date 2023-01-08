package com.hc.carousel.service;

import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
public interface CarouselService {

    /**
     * 只查询优先级前6的数据
     *
     * @return
     */
    R list();
}
