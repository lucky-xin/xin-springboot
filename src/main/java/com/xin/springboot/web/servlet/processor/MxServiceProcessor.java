package com.xin.springboot.web.servlet.processor;


import com.suntek.eap.web.RequestContext;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 管理类服务处理器
 * @date 2018-05-19 19:59
 * @Copyright (C)2018 , Luchaoxin
 */
public class MxServiceProcessor extends AbstractServiceProcessor {

    public MxServiceProcessor(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object doService(RequestContext context, HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handlerMethod) throws Exception {

        Map<String, CustomHandlerMethod> handlerMethods = getApplicationContext().getBeansOfType(CustomHandlerMethod.class);

        Map<String, Object> filterServices = new HashMap<String, Object>();

        Iterator<Map.Entry<String, CustomHandlerMethod>> iterator = handlerMethods.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, CustomHandlerMethod> entry = iterator.next();
            String id = entry.getKey();
            CustomHandlerMethod handler = entry.getValue();
            filterServices.put(id, handler.getHandler().getBeanType());
        }

        context.getResponse().putData("services", filterServices);

        return context.getResponse().getResult();
    }
}
