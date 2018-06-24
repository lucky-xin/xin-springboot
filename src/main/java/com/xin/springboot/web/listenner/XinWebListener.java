package com.xin.springboot.web.listenner;


import com.xin.springboot.web.method.BeanNameUrlRegisterHepper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//@WebListener
public class XinWebListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        webApplicationContext.getAutowireCapableBeanFactory();
        webApplicationContext.getParentBeanFactory();
        new BeanNameUrlRegisterHepper().registerUrlHandler(event.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
