package com.xin.springboot.learn.model;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 测试bean(用一句话描述该类做什么)
 * @date 2018-06-14 8:46
 * @Copyright (C)2017 , Luchaoxin
 */

@Component
public class XinBean implements DisposableBean {

    @PostConstruct
    public void initMethod1() {
        System.out.println("initMethod1");
    }

    @PostConstruct
    public void initMethod2() {
        System.out.println("initMethod2");
    }

    @PostConstruct
    public void initMethod3() {
        System.out.println("initMethod3");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("调用destroy方法");
    }

    @PreDestroy
    public void destroyMethod1() {
        System.out.println("destroyMethod1");
    }

    @PreDestroy
    public void destroyMethod2() {
        System.out.println("destroyMethod2");
    }

}
