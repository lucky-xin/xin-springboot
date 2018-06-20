package com.xin.springboot.web.method;

import com.suntek.eap.pico.annotation.BeanService;
import com.suntek.eap.pico.annotation.LocalComponent;
import com.suntek.eap.pico.annotation.QueryService;
import com.xin.springboot.utils.BeanUtil;

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
        if (!clazz.isAnnotationPresent(LocalComponent.class) && !isSupport(method)) {
            return null;
        }
        Annotation classAnnotation = clazz.getAnnotation(LocalComponent.class);
        String annotationClassId = "";
        String classDescription = "";
        if (clazz.isAnnotationPresent(LocalComponent.class)) {
            annotationClassId = ((LocalComponent) classAnnotation).id();
            classDescription = ((LocalComponent) classAnnotation).description();

        }

        String handlerMethodId = null;
        String type = null;
        String description = "";
        String author = "";
        String since = "";
        String isLog = "";
        Annotation methodAnnotation;
        if (method.isAnnotationPresent(BeanService.class)) {
            methodAnnotation = method.getAnnotation(BeanService.class);
            type = ((BeanService) methodAnnotation).type();
            handlerMethodId = ((BeanService) methodAnnotation).id();
            author = ((BeanService) methodAnnotation).author();
            since = ((BeanService) methodAnnotation).since();
            description = ((BeanService) methodAnnotation).description();
            isLog = ((BeanService) methodAnnotation).isLog();
        }

        if (method.isAnnotationPresent(QueryService.class)) {
            methodAnnotation = method.getAnnotation(QueryService.class);
            type = ((QueryService) methodAnnotation).type();
            handlerMethodId = ((QueryService) methodAnnotation).id();
            author = ((QueryService) methodAnnotation).author();
            since = ((QueryService) methodAnnotation).since();
            description = ((QueryService) methodAnnotation).description();
            isLog = ((QueryService) methodAnnotation).isLog();
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
