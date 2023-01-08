package com.hc.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.carousel.mapper.CarouselMapper;
import com.hc.carousel.service.CarouselService;
import com.hc.pojo.Carousel;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    @Cacheable(value = "list.carousel ", key = "#root.methodName")
    @Override
    public R list() {
        LambdaQueryWrapper<Carousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Carousel::getPriority);
        List<Carousel> carouselList = carouselMapper.selectList(queryWrapper);
        List<Carousel> collect = carouselList.stream().limit(6).collect(Collectors.toList());
        return R.ok("查询成功！", collect);
    }
}
