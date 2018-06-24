package com.xin.springboot.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义服务注解
 * @date 2018-06-23 22:49
 * @Copyright (C)2018 , Luchaoxin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface CustomController {

    String value();

    String param() default "{}";

    String author() default "";

    String description() default "";

    String since() default "1.0";
}