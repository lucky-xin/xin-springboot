package com.xin.springboot.web.method;

import com.suntek.eap.pico.annotation.BeanService;
import com.suntek.eap.pico.annotation.QueryService;
import com.xin.springboot.utils.BeanUtil;
import com.xin.springboot.web.annotation.CustomController;
import com.xin.springboot.web.annotation.CustomService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: get HandlerMethod util
 * @date 2018-05-17 20:29
 * @Copyright (C)2017 , Luchaoxin
 */
public class HandlerMethodUtil {

    public static CustomHandlerMethodHolder getHandlerMethod(Class clazz, Method method) throws Exception {
        if (!clazz.isAnnotationPresent(CustomController.class) && !isSupport(method)) {
            return null;
        }
        Annotation classAnnotation = clazz.getAnnotation(CustomController.class);
        String annotationClassId = "";
        String classDescription = "";
        if (clazz.isAnnotationPresent(CustomController.class)) {
            annotationClassId = ((CustomController) classAnnotation).value();
            classDescription = ((CustomController) classAnnotation).description();

        }

        String handlerMethodId = null;
        String type = null;
        String description = "";
        String author = "";
        String since = "";
        boolean isLog = false;
        Annotation methodAnnotation;
        if (method.isAnnotationPresent(CustomService.class)) {
            methodAnnotation = method.getAnnotation(CustomService.class);
//            type = ((CustomService) methodAnnotation).type();
            handlerMethodId = ((CustomService) methodAnnotation).value();
            author = ((CustomService) methodAnnotation).author();
            since = ((CustomService) methodAnnotation).since();
            description = ((CustomService) methodAnnotation).description();
            isLog = ((CustomService) methodAnnotation).isLog();
        }

        return new CustomHandlerMethodHolder.Builder()
                .addMethod(method)
                .addAuthor(author)
                .addBean(BeanUtil.getBean(clazz))
                .addId("/" + annotationClassId + "/" + handlerMethodId)
                .addDescription(description.isEmpty() ? classDescription : description)
                .addSince(since)
                .addRemote("remote".equals(type))
                .addIsLog("true".equals(isLog))
                .build();
    }

    public static boolean isSupport(Method method) {
        return method == null ? false : method.isAnnotationPresent(BeanService.class) || method.isAnnotationPresent(QueryService.class);
    }
}
