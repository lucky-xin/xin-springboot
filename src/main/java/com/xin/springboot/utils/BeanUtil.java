package com.xin.springboot.utils;

public class BeanUtil {

    public static <T> T getBean(Class<T> clazz) throws Exception{
        try {
            return (T)clazz.newInstance();
        } catch (Exception e) {
            throw e;
        }
    }
}
