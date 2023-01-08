package com.hc.admin.controller;

import com.hc.admin.param.AdminParam;
import com.hc.admin.pojo.AdminUser;
import com.hc.admin.service.AdminUserService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@RestController
@RequestMapping("/user")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    @PostMapping("/login")
    public R login(@Validated AdminParam adminParam, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return R.fail("参数错误，登录失败！");
        }
        //验证码校验
        String captcha = (String) session.getAttribute("captcha");
        if (adminParam.getVerCode().equals(captcha)) {
            return R.fail("验证码错误！");
        }
        AdminUser adminUser = adminUserService.login(adminParam);

        if (adminUser == null) {
            return R.fail("登录失败！账号或密码错误！");
        }
        session.setAttribute("userInfo", adminUser);
        return R.ok("登录成功！");
    }

    @GetMapping("/logout")
    public R logout(HttpSession session) {
        session.invalidate();
        return R.ok("退出登录成功！");
    }
}
