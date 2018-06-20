package com.xin.springboot.web.servlet.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suntek.eap.jdbc.PageQueryResult;
import com.suntek.eap.log.ServiceLog;
import com.suntek.eap.tag.service.SysDictionaryService;
import com.suntek.eap.util.StringUtil;
import com.suntek.eap.web.RequestContext;
import com.xin.springboot.utils.BeanUtil;
import com.xin.springboot.utils.UITmplUtil;
import com.xin.springboot.web.method.CustomHandlerMethod;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: ui请求处理器
 * @date 2018-05-19 20:00
 * @Copyright (C)2018 , Luchaoxin
 */
public class UiServiceProcessor extends AbstractServiceProcessor {

    public UiServiceProcessor(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object doService(RequestContext context, HttpServletRequest request, HttpServletResponse response, CustomHandlerMethod handler) throws Exception {

        String items = request.getParameter("items");
        JSONArray array = JSONArray.parseArray(items);
        String service = "", elementId = "";
        String fromURL = request.getParameter("fromURL");

        if (StringUtil.isNull(fromURL)) {
            fromURL = "Unknow";
        }

        ServiceLog.debug("UI init begin -> " + fromURL);
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);

            elementId = json.getString("id");
            context.putParameter("elementId", elementId);

            String type = json.getString("type");

            context.putParameter("type", type);
            Map<Object, Object> parameters = (Map) json.get("parameters");

            if (parameters != null) {
                Iterator<Map.Entry<Object, Object>> iterator = parameters.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    context.putParameter(StringUtil.toString(entry.getKey()), StringUtil.toString(entry.getValue()));
                }
            }

            if ("dictview".equals(type)) {
                service = "dictview";
                ServiceLog.debug("UI " + elementId + " -> dictview, loading");
                new SysDictionaryService().getSysDicDataByKind(context);
            } else {
                service = json.getString("service");
                ServiceLog.debug("UI " + elementId + " -> " + service);
                context.putParameter("service", service);
                Object ret = new Object();
                //前端模板数据加载 add by linsj
                File jsonFile = UITmplUtil.getJsonFileByService(service);

                if (UITmplUtil.isUseTmpl() && jsonFile.exists()) {
                    ret = UITmplUtil.getUiTmplObjectByJson(jsonFile);
                } else {
                    ApplicationContext applicationContext = getApplicationContext();
                    Object bean = applicationContext.getBean(service);
                    if (bean instanceof RootBeanDefinition) {
                        RootBeanDefinition beanDefinition = (RootBeanDefinition) bean;
                        Object instance = BeanUtil.getBean(beanDefinition.getBeanClass());
                        if (CustomHandlerMethod.class.isInstance(instance)) {
                            CustomHandlerMethod handlerMethod = (CustomHandlerMethod) instance;
                            ret = handlerMethod.getHandler().getMethod().invoke(bean, context);
                        }
                    }
                }
                if (ret instanceof PageQueryResult) {
                    context.getResponse().putData(elementId, ((PageQueryResult) ret).toMap());
                }
            }
        }
        return context.getResponse().getResult();
    }
}
