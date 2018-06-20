package com.xin.springboot.utils;


import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 根据map的key-value生成bean对象，key为对象属性名称。属性不能被static final修饰
 * @date 2018-05-20 18:09
 * @Copyright (C)2018 , Luchaoxin
 */
public class BeanBuilder {

    public static <T> T build(Class<T> clazz, Map<String, Object> params) {
        AssertUtil.checkNotNull(clazz, "clazz must not be null");
        AssertUtil.checkNotEmpty(params, "params must not be Empty");
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {

        }
        if (object == null) {
            throw new RuntimeException("this class:" + clazz + " cannot be instantiated.");
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                Object value = params.get(field.getName());
                if (value == null) {
                    continue;
                }
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
