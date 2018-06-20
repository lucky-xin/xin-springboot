package com.xin.springboot.web.servlet.processor;

import com.suntek.eap.log.ServiceLog;
import com.suntek.eap.util.CollectionUtil;
import com.suntek.eap.web.RequestContext;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 远程服务调用处理器。
 * @date 2018-05-19 21:20
 * @Copyright (C)2018 , Luchaoxin
 */
public class RemoteCallServiceProcessor extends AbstractServiceProcessor {

    public RemoteCallServiceProcessor(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object doService(RequestContext context, HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handlerMethod) throws Exception {

        String service = getRemoteServiceId(request);
        long begin = System.currentTimeMillis();
        ServiceLog.debug(service + "(RC) begin.");
        ServiceLog.debug("Parameters: " + context.getParameters());

        if (service.equals("sso/login")) {
            HandlerMethod handler = handlerMethod.getHandler();
            handler.getMethod().invoke(handler.getBean(), context);
            return context.getResponse().getResult();
        } else {
            String token = (String) context.getParameter("token");
            Map<String, Object> parameters = context.getParameters();

            parameters.remove("token");
            parameters.remove("service");

            String param = toQueryString(parameters);

            int port = request.getServerPort();

            Object ret = RemoteServiceAction.invokeMethod(port, token, service, param);
            ServiceLog.debug(service + "(RC) end, use time: " + (System.currentTimeMillis() - begin) + " ms");
            return ret;
        }
    }

    private String toQueryString(Map<String, Object> parameters) {
        if (CollectionUtil.isEmpty(parameters)) {
            return "";
        }

        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            buf.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
        }
        return buf.deleteCharAt(buf.length() - 1).toString();
    }
}
