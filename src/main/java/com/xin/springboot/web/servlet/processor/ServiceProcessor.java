package com.xin.springboot.web.servlet.processor;



import com.xin.springboot.web.method.CustomHandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServiceProcessor {
    Object service(HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handlerMethod) throws Exception;
}