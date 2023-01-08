package com.hc.admin.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (null == request.getSession().getAttribute("userInfo")) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        } else {
            return true;
        }
    }
}
