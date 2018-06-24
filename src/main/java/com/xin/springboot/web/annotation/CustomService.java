package com.xin.springboot.web.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomService {

    String value();

    String param() default "{}";

    String author() default "";

    String description() default "";

    String since() default "1.0";

    boolean isLog() default false;
}
