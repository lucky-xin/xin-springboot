package com.xin.springboot.web.servlet.interceptor;


import com.suntek.eap.log.EapServerLog;
import com.suntek.eap.log.ServiceLog;
import com.xin.springboot.utils.ServiceUtil;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务层拦截器，记录服务花费时间
 * @date 2018-05-21 8:53
 * @Copyright (C)2017 , Luchaoxin
 */

public class TimeRecordInterceptor extends HandlerInterceptorAdapter {

    private String startTimeName = this.getClass().getName() + ".startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(startTimeName, System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof CustomHandlerMethod) {
            String service = ServiceUtil.getServiceId(request);
            long useTime = (System.currentTimeMillis() - (Long) request.getAttribute(startTimeName));
            if (service.startsWith("portal/message")) {
                EapServerLog.getTimerLog("portal-timer").debug(service + " end, use time: " + useTime + " ms");
            } else {
                ServiceLog.debug(service + " end, use time: " + useTime + " ms");
            }
        }
    }

}
