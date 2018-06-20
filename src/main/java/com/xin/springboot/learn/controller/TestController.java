package com.xin.springboot.learn.controller;

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

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public String sayHello(HttpServletRequest request, @PathVariable("id") Integer id, @RequestParam(value = "name", required = false, defaultValue = "luchaoxin") String userName) {
        //localhost:9080/luchaoxin/xin/hello/122?name=lcx
        System.out.println(request.getRequestURI());
        Object xinBean = applicationContext.getApplicationContext().getBean("xinBean");
        System.out.println(xinBean);
        applicationContext.getApplicationContext().getAutowireCapableBeanFactory().destroyBean(xinBean);

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
