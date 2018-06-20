package com.xin.springboot.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务工具类
 * @date 2018-05-21 20:35
 * @Copyright (C)2018 , Luchaoxin
 */
public class ServiceUtil {

    /**
     * 从 request 查询服务 id
     *
     * @param request
     * @return
     */
    public static String getServiceId(HttpServletRequest request) {
        String query = request.getRequestURI();

        String context = request.getContextPath();
        String id = query.replace(context + "/rest/v6/", "");
        if (query.contains("/mx/")) {
            id = query.replace(context + "/mx/v6/", "");
        }
        return id;
    }

    /**
     * 从 request 查询服务访问类型{rest; mx;}
     *
     * @param request
     * @return
     */
    public static String getServiceType(HttpServletRequest request) {
        String query = request.getRequestURI();
        String context = request.getContextPath();
        String id = query.replace(context + "/", "");
        String serviceType = id.split("\\/")[0];
        return serviceType;
    }

    public static String getServiceUri(HttpServletRequest request) {
        String query = request.getRequestURI();
        String context = request.getContextPath();
        String uri = query.replaceAll("^" + context, "");
        return uri;
    }
}
