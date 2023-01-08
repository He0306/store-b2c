package com.hc.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginProtectInterceptor()).addPathPatterns()
                .excludePathPatterns("/", "/index.html", "/index", "/static/**",
                        "/user/login", "/user/logout",
                        "/api/**", "/css/**", "/images/**",
                        "/js/**", "/lib/**", "/captcha");
    }
}
