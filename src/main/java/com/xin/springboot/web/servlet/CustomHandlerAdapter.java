package com.xin.springboot.web.servlet;

import com.xin.springboot.web.debug.XinHttpServletRequestWrapper;
import com.xin.springboot.web.method.CustomHandlerMethod;
import com.xin.springboot.web.servlet.processor.*;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1./rest/ui     2./mx/ui   ->ui UiServiceProxy
 * 1./mx/v6       2./rest/v6 ->v6 BeanServiceProxy
 * 1./mx/service  2./rest/mx ->mx MxServiceProxy
 * <p>
 * rest/rc -> RemoteCallServiceProxy
 *
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义HandlerAdapter适配SpringBoot
 * @date 2018-05-18 23:00
 * @Copyright (C)2018 , Luchaoxin
 */
@Component
public class CustomHandlerAdapter extends WebApplicationObjectSupport implements HandlerAdapter, PriorityOrdered {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof CustomHandlerMethod;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = getUri(request);
        Object result = null;
        ServiceProcessor processor = getServiceProcessor(uri);
        request = new XinHttpServletRequestWrapper(request);
        if (processor != null) {
            String user = request.getRemoteUser();
            result = processor.service(request, response, (CustomHandlerMethod) handler);
        }
/*
        //输出流还没关闭
        if (!response.isCommitted()) {
            PrintWriter print = response.getWriter();
            if (result == null) {
                Response resp = new Response();
                resp.setError(Errors.UndefinedServiceError, PropertiesUtil.getError(Errors.UndefinedServiceError, request.getRequestURI()));
                result = resp.getResult();
            }
            System.out.println(result);
            System.out.println(JSON.toJSONString(result));
            print.print(JSON.toJSONString(result));
        }
        */
        return null;
    }

    private ServiceProcessor getServiceProcessor(String uri) {
        if (uri.startsWith("/rest/ui") || uri.startsWith("/mx/ui")) {
            return new UiServiceProcessor(getApplicationContext());
        } else if (uri.startsWith("/mx/v6") || uri.startsWith("/rest/v6")) {
            return new BeanServiceProcessor(getApplicationContext());
        } else if (uri.startsWith("/mx/service") || uri.startsWith("/rest/mx")) {
            return new MxServiceProcessor(getApplicationContext());
        } else if (uri.startsWith("/rest/rc")) {
            return new RemoteCallServiceProcessor(getApplicationContext());
        }
        throw new RuntimeException("cannot found a match ServiceProcessor.");
    }

    protected String getUri(HttpServletRequest request) {
        String query = request.getRequestURI();
        String contextPath = request.getContextPath();
        return query.replaceAll("^" + contextPath, "");
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return 0;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
