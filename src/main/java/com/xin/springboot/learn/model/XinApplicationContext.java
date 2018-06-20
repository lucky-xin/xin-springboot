package com.xin.springboot.learn.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: bean管理
 * @date 2018-06-14 14:28
 * @Copyright (C)2017 , Luchaoxin
 */
@Component
public class XinApplicationContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
        return applicationContext.getAutowireCapableBeanFactory();
    }
}
