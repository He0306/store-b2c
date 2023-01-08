package com.hc.user.controller;

import com.hc.param.UserCheckParam;
import com.hc.param.UserLoginParam;
import com.hc.pojo.User;
import com.hc.user.service.UserService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 检查账号是否可用的接口
     *
     * @param userCheckParam 接收检查的账号实体
     * @param result         获取校验结构的实体对象
     * @return 返回封装对象R
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result) {
        boolean b = result.hasErrors();
        if (b) {
            return R.fail("账号为null，不可使用！");
        }
        return userService.check(userCheckParam);
    }

    /**
     * 注册
     *
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常，不可注册！");
        }
        return userService.regsiter(user);
    }

    /**
     * 登录
     *
     * @param userLoginParam
     * @param result
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam userLoginParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常，不可登录！");
        }
        return userService.login(userLoginParam);
    }

}
