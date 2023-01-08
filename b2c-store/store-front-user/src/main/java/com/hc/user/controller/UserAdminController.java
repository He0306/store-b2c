package com.hc.user.controller;

import com.hc.param.CartListParam;
import com.hc.param.PageParam;
import com.hc.pojo.User;
import com.hc.user.service.UserService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@RestController
@RequestMapping("/user")
public class UserAdminController {

    @Autowired
    UserService userService;

    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam) {
        return userService.listPage(pageParam);
    }

    @PostMapping("/admin/remove")
    public R remove(@RequestBody CartListParam cartListParam) {
        return userService.remove(cartListParam.getUserId());
    }

    @PostMapping("/admin/update")
    public R update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/admin/save")
    public R save(@RequestBody User user) {
        return userService.save(user);
    }
}
