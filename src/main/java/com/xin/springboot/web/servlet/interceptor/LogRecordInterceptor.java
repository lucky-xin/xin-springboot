package com.xin.springboot.web.servlet.interceptor;

import com.alibaba.fastjson.JSON;
import com.suntek.eap.EAP;
import com.suntek.eap.org.UserModel;
import com.suntek.eap.util.StringUtil;
import com.suntek.eap.web.RequestContext;
import com.xin.springboot.utils.BeanBuilder;
import com.xin.springboot.utils.OperateLog;
import com.xin.springboot.utils.ServiceUtil;
import com.xin.springboot.web.method.CustomHandlerMethod;
import com.xin.springboot.web.model.LogInfo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务层拦截器，用户打印log
 * @date 2018-05-21 8:53
 * @Copyright (C)2017 , Luchaoxin
 */
public class LogRecordInterceptor extends HandlerInterceptorAdapter {

    private String startTimeName = this.getClass().getName() + ".startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(startTimeName, System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof CustomHandlerMethod) {
            doLog((RequestContext) request.getAttribute(RequestContext.class.getName() + "_context"), (CustomHandlerMethod) handler, System.currentTimeMillis() - (Long) request.getAttribute(startTimeName));
        }
    }

    protected void doLog(RequestContext context, CustomHandlerMethod customHandlerMethod, long usetime) {
        if (!customHandlerMethod.getIsLog()) {
            return;
        }
        String message = "";
        int code = 20000;
        if (context.getResponse().hasError()) {
            message = JSON.toJSONString(context.getResponse().getError());
            if (message.length() > 1000) {
                message.substring(0, 1000);
            }
        } else if (context.getResponse().hasWarn()) {
            code = 440400;
            message = context.getResponse().getWarn();
        }
        UserModel user = context.getUser();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("logId", EAP.keyTool.getIDGenerator());
        params.put("sessionId", context.getSessionId());
        params.put("description", customHandlerMethod.getDescription());
        params.put("param", JSON.toJSONString(context.getParameters()));
        params.put("type", customHandlerMethod.getIsRemote() ? "remote" : "local");
        params.put("code", code);
        params.put("moduleName", context.getHttpRequest().getContextPath().replaceAll("\\/", ""));
        params.put("visitType", ServiceUtil.getServiceType(context.getHttpRequest()));
        params.put("message", message);
        params.put("userCode", user.getCode());
        params.put("userName", user.getName());
        params.put("host", context.getHttpRequest().getRemoteHost());
        params.put("operateType", StringUtil.toString(context.getParameter("operateType")));
        OperateLog.log(BeanBuilder.build(LogInfo.class, params));
    }
}
