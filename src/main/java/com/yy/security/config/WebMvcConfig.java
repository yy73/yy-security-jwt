package com.yy.security.config;

import com.yy.security.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ywl
 * @Date 2021/5/27 10:09
 * @Description 添加拦截器，用于 添加用户信息
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 白名单
     */
    @Value("${ignore.urls:}")
    private String[] ingores;

    @Bean
    LoginCheckInterceptor loginCheckInterceptor() {
        return new LoginCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor()).excludePathPatterns(ingores);
    }
}
