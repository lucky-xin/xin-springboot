package com.xin.springboot.web.debug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 开发环境配置
 * @date 2018-05-29 19:27
 * @Copyright (C)2017 , Luchaoxin
 */

@Configuration
public class DevelopWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public DevelopInterceptor developInterceptor() {
        return new DevelopInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(developInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
