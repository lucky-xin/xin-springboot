package com.xin.springboot.learn.controller;

import com.xin.springboot.learn.model.AopBean;
import com.xin.springboot.learn.model.XinApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 测试controller(用一句话描述该类做什么)
 * @date 2018-06-14 8:59
 * @Copyright (C)2017 , Luchaoxin
 */
@RestController(value = "/luchaoxin")
public class TestController {

    @Value("${age}")
    Integer age;

    @Autowired
    private XinApplicationContext applicationContext;

    @Autowired
    AopBean aopBean;

    private Object getBean(String name) {
        return applicationContext.getApplicationContext().getBean(name);
    }

    private void destroyBean(Object bean) {
        applicationContext.getApplicationContext().getAutowireCapableBeanFactory().destroyBean(bean);
    }


    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request, @PathVariable("id") Integer id, @RequestParam(value = "name", required = false, defaultValue = "luchaoxin") String userName) {
        //localhost:9080/luchaoxin/xin/hello/122?name=lcx
        System.out.println("注入aopBean：" + aopBean.getClass());
        aopBean.throwException();
        System.out.println(aopBean.getName("lcx", 18));
        return "Hello,Spring Boot!" + " age:" + age + "---- id:" + id + "---name:" + userName;
    }

    @RequestMapping(value = "/index/hello/{id}", method = RequestMethod.GET)
    public String hello(@PathVariable("id") Integer id, @RequestParam(value = "name", required = false, defaultValue = "luchaoxin") String userName) {
        //localhost:9080/xin/hello/122?name=lcx
        return "Hello,Spring Boot!" + "age:" + age + "----id:" + id + "----name:" + userName;
    }

    @Caching
    public String getUserName() {
        return "luchaoxin";
    }
}
