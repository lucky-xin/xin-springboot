package com.xin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.xin.springboot")
@ServletComponentScan//扫描监听类
@EnableAspectJAutoProxy
public class XinSpringBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(XinSpringBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(XinSpringBootApplication.class);
        return super.configure(builder);
    }
}
