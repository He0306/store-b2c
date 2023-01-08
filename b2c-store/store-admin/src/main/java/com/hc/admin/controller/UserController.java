package com.hc.admin.controller;

import com.hc.admin.service.UserService;
import com.hc.param.CartListParam;
import com.hc.param.PageParam;
import com.hc.pojo.User;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public R userList(PageParam pageParam) {
        return userService.userList(pageParam);
    }

    @PostMapping("/remove")
    public R remove(CartListParam cartListParam) {
        return userService.remove(cartListParam);
    }

    @PostMapping("/update")
    public R update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/save")
    public R save(@RequestBody User user) {
        return userService.save(user);
    }
}
