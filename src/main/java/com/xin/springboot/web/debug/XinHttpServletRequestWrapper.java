package com.xin.springboot.web.debug;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 重写request过滤用户信息(用一句话描述该类做什么)
 * @date 2018-05-29 19:53
 * @Copyright (C)2017 , Luchaoxin
 */
public class XinHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XinHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getRemoteUser() {
        return "develop";
    }
}
