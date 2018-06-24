package com.xin.springboot.learn.model;

import org.springframework.stereotype.Component;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: aop学习
 * @date 2018-06-24 11:04
 * @Copyright (C)2018 , Luchaoxin
 */

@Component
public class AopBean {

    public String getName(String name, Integer age) {
        System.out.println(name);
        return "Hello," + name;
    }

    public void throwException() {
        throw new RuntimeException("发生了异常");
    }

}
