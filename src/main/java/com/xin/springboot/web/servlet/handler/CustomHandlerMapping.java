package com.xin.springboot.web.servlet.handler;


import com.xin.springboot.web.method.CustomHandlerMethod;
import com.xin.springboot.web.servlet.interceptor.TimeRecordInterceptor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义HandlerMapping设配SpringBoot
 * @date 2018-05-19 00:14
 * @Copyright (C)2018 , Luchaoxin
 */
@Component
public class CustomHandlerMapping extends AbstractDetectingUrlHandlerMapping implements PriorityOrdered {

    private static List<String> prefixs = new ArrayList<String>();

    static {
        prefixs.add("/mx/ui");
        prefixs.add("/mx/v6");
        prefixs.add("/mx/service");
        prefixs.add("/rest/ui");
        prefixs.add("/rest/v6");
        prefixs.add("/rest/rc");
        prefixs.add("/rest/mx");
    }

    @Override
    protected void initInterceptors() {
//        this.setInterceptors(new TimeRecordInterceptor());
        super.initInterceptors();
    }

    @Override
    protected String[] determineUrlsForHandler(String beanName) {
        List<String> urls = new ArrayList<>();
        if (beanName.startsWith("/")) {
            if (getApplicationContext().getBean(beanName) instanceof CustomHandlerMethod) {
                for (String prefix : prefixs) {
                    urls.add(prefix + beanName);
                }
            }
        }

        return StringUtils.toStringArray(urls);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String query = request.getRequestURI();
        String context = request.getContextPath();
        String uri = query.replaceAll("^" + context, "");
        return lookupHandler(uri, request);
    }

    @Override
    protected Object lookupHandler(String urlPath, HttpServletRequest request) throws Exception {
        Map<String, Object> handlerMap = getHandlerMap();
        return handlerMap.get(urlPath);
    }

}
