package com.hc.carousel.controller;

import com.hc.carousel.service.CarouselService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    CarouselService carouselService;

    @PostMapping("/list")
    public R list() {
        return carouselService.list();
    }

}
