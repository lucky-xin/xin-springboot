package com.xin.springboot.web.servlet.processor;

import com.suntek.eap.jdbc.PageQueryResult;
import com.suntek.eap.log.EapServerLog;
import com.suntek.eap.log.ServiceLog;
import com.suntek.eap.util.StringUtil;
import com.suntek.eap.web.AppException;
import com.suntek.eap.web.Errors;
import com.suntek.eap.web.RequestContext;
import com.xin.springboot.utils.ServiceUtil;
import com.xin.springboot.utils.UITmplUtil;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description:bean处理器
 * @date 2018-05-19 20:01
 * @Copyright (C)2018 , Luchaoxin
 */
public class BeanServiceProcessor extends AbstractServiceProcessor {

    public BeanServiceProcessor(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object doService(RequestContext context, HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handler) throws Exception {

        String service = ServiceUtil.getServiceId(request);
        HandlerMethod handlerMethod = handler.getHandler();

        if (context.getResponse().hasError()) {
            return context.getResponse().getResult();
        }

        String serviceName = handler.getDescription();

        try {
            if (service.startsWith("portal/message")) {
                EapServerLog.getTimerLog("portal-timer").debug(service + " begin.");
                EapServerLog.getTimerLog("portal-timer").debug("Parameters: " + context.getParameters());
            } else {
                ServiceLog.debug(service + " begin." + context.getParameters().toString().length());
                if (context.getParameters().toString().length() < 10 * 1024) {
                    ServiceLog.debug("Parameters: " + context.getParameters());
                }

            }

            String elementId = (String) context.getParameter("id");

            elementId = StringUtil.isNull(elementId) ? "data" : elementId;

            context.putParameter("elementId", elementId);

            Object ret = null;

            //前端模板数据加载 add by linsj
            File jsonFile = UITmplUtil.getJsonFileByService(service);

            if (UITmplUtil.isUseTmpl() && jsonFile.exists()) {
                ret = UITmplUtil.getUiTmplObjectByJson(jsonFile);
            } else {
                ret = handlerMethod.getMethod().invoke(handlerMethod.getBean(), context);
            }

            if (ret instanceof PageQueryResult) {
                context.getResponse().putData(elementId, ((PageQueryResult) ret).toMap());
            } else {
                if (ret != null) {
                    context.getResponse().putData(elementId, ret);
                }
            }
        } catch (InvocationTargetException e) {
            if (StringUtil.isNull(serviceName)) {
                serviceName = service;
            }
            if (e.getTargetException() instanceof AppException) {
                context.getResponse().setError(e.getTargetException().getMessage(), e.getTargetException());
            } else {
                context.getResponse().setWarn("服务 " + serviceName + " 执行失败。" + e.getTargetException());
            }

        } catch (UnsupportedOperationException e) {
            context.getResponse().setError(Errors.UndefinedServiceError, e.getMessage());
        } catch (Exception e) {
            if (StringUtil.isNull(serviceName)) {
                serviceName = service;
            }
            if (e instanceof InvocationTargetException) {
                if (!context.getResponse().hasError()) {
                    context.getResponse().setError("服务 " + serviceName + " 执行失败", e.getCause());
                }
            } else {
                context.getResponse().setError(e);
            }
        }
        return context.getResponse().getResult();
    }
}
