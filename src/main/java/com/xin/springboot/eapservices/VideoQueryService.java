package com.xin.springboot.eapservices;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 摄像机查询服务
 * @date 2018-05-23 21:05
 * @Copyright (C)2018 , Luchaoxin
 */

import com.suntek.eap.pico.annotation.LocalComponent;
import com.suntek.eap.pico.annotation.QueryService;
import com.suntek.eap.util.StringUtil;
import com.suntek.eap.web.RequestContext;

@LocalComponent(id = "video")
public class VideoQueryService {

    @QueryService(id = "query", description = "摄像机查询服务", type = "remote", author = "lcx", since = "6.2", isLog = "true")
    public void queryVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().setMessage("hello " + name + " v5v5v5v5v5");
    }

    @QueryService(id = "realVideo", description = "摄像机查询服务", type = "remote", author = "lcx", since = "6.2", isLog = "true")
    public void realVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().putData("ggg", "fdfdfd");
        context.getResponse().putData("name", name);
    }


}
