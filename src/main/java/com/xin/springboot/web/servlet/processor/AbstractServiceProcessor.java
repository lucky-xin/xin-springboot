package com.xin.springboot.web.servlet.processor;

import com.alibaba.fastjson.JSON;
import com.suntek.eap.util.PropertiesUtil;
import com.suntek.eap.web.Errors;
import com.suntek.eap.web.RequestContext;
import com.suntek.eap.web.Response;
import com.xin.springboot.utils.ServiceUtil;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务处理器
 * @date 2018-05-19 20:16
 * @Copyright (C)2018 , Luchaoxin
 */
public abstract class AbstractServiceProcessor implements ServiceProcessor {

    private ApplicationContext applicationContext;

    public AbstractServiceProcessor(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            throw new RuntimeException("applicationContext must not be null.");
        }
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public Object service(HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handlerMethod) throws Exception {
        RequestContext context = new RequestContext(request);
        context.setHttpResponse(response);
        request.setAttribute(RequestContext.class.getName() + "_context", context);
        Object result = doService(context, request, response, handlerMethod);
        render(request, response, result);
        return result;
    }

    protected abstract Object doService(RequestContext context, HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handlerMethod) throws Exception;


    /**
     * 从 request 查询远程服务 id
     *
     * @param request
     * @return
     */
    protected String getRemoteServiceId(HttpServletRequest request) {
        String query = request.getRequestURI();
        String context = request.getContextPath();
        String id = query.replace(context + "/rest/rc/", "");
        return id;
    }

    /**
     * @param uri
     * @param errorMessage
     * @return
     */
    protected Object toError(String uri, String errorMessage) {
        Response resp = new Response();
        resp.setError(errorMessage + ": " + uri);
        return resp.getResult();
    }

    protected void render(HttpServletRequest request, HttpServletResponse response, Object ret) throws IOException {
        //输出流还没关闭
        if (!response.isCommitted()) {
            PrintWriter print = response.getWriter();

            if (ret == null) {
                Response resp = new Response();
                resp.setError(Errors.UndefinedServiceError, PropertiesUtil.getError(Errors.UndefinedServiceError, request.getRequestURI()));
                ret = resp.getResult();
            }
            if (ServiceUtil.getServiceUri(request).startsWith("/rest/rc")) {
                print.print(ret);
            } else {
                print.print(JSON.toJSONString(ret));
            }
        }
    }

}
