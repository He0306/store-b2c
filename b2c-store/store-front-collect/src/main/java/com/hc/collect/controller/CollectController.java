package com.hc.collect.controller;

import com.hc.collect.service.CollectService;
import com.hc.pojo.Collect;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    CollectService collectService;

    @PostMapping("/save")
    public R save(@RequestBody Collect collect) {
        return collectService.save(collect);
    }

    @PostMapping("/list")
    public R list(@RequestBody Collect collect) {
        return collectService.list(collect.getUserId());
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Collect collect) {
        return collectService.remove(collect);
    }

    @PostMapping("/remove/check")
    public R removeCheck(@RequestBody Integer productId) {
        return collectService.removeById(productId);
    }
}
