package com.xin.springboot.eapservices;

import com.suntek.eap.util.StringUtil;
import com.suntek.eap.web.RequestContext;
import com.xin.springboot.web.annotation.CustomController;
import com.xin.springboot.web.annotation.CustomService;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 摄像机查询服务
 * @date 2018-05-23 21:05
 * @Copyright (C)2018 , Luchaoxin
 */

@CustomController(value = "video")
public class VideoQueryService {

    @CustomService(value = "query", description = "摄像机查询服务", author = "lcx", since = "1.0", isLog = true)
    public void queryVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().setMessage("hello " + name + " v5v5v5v5v5");
    }

    @CustomService(value = "realVideo", description = "摄像机查询服务", author = "lcx", since = "1.0", isLog = true)
    public void realVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().putData("ggg", "fdfdfd");
        context.getResponse().putData("name", name);
    }
}

