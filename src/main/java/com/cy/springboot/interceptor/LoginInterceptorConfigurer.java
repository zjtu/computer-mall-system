package com.cy.springboot.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/** 注册处理器拦截器 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    /** 拦截器配置 */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 创建拦截器对象
        HandlerInterceptor loginInterceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<String>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/user/reg");
        patterns.add("/user/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
