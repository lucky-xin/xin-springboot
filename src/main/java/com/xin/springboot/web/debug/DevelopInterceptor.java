package com.xin.springboot.web.debug;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务层拦截器
 * @date 2018-05-21 8:53
 * @Copyright (C)2017 , Luchaoxin
 */

public class DevelopInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(new XinHttpServletRequestWrapper(request), response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

}
