package com.xin.springboot.web.method;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义HandlerMethod建造者
 * @date 2018-05-17 17:17
 * @Copyright (C)2018 , Luchaoxin
 */
public class CustomHandlerMethodHolder {

    private String id;

    private boolean isRemote;

    private RootBeanDefinition beanDefinition;

    public static class Builder {

        String id;

        boolean isRemote;

        String author;

        String description;

        String since;

        Object bean;

        Method method;

        boolean isLog;

        public Builder addId(String id) {
            this.id = id;
            return this;
        }

        public Builder addRemote(boolean isRemote) {
            this.isRemote = isRemote;
            return this;
        }

        public Builder addAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder addDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addSince(String since) {
            this.since = since;
            return this;
        }

        public Builder addMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder addBean(Object bean) {
            this.bean = bean;
            return this;
        }

        public Builder addIsLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }


        public CustomHandlerMethodHolder build() {
            HandlerMethod handler = new HandlerMethod(bean, method);

            RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomHandlerMethod.class);

            MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
            propertyValues.add("handler", handler);
            propertyValues.add("id", id);
            propertyValues.add("isRemote", isRemote);
            propertyValues.add("author", author);
            propertyValues.add("description", description);
            propertyValues.add("since", isRemote);
            propertyValues.add("isLog", isLog);

            beanDefinition.setScope("singleton");

            CustomHandlerMethodHolder holder = new CustomHandlerMethodHolder();

            holder.setBeanDefinition(beanDefinition);
            holder.setId(id);
            holder.setRemote(isRemote);
            return holder;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public RootBeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public void setBeanDefinition(RootBeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
    }
}
