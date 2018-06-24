package com.xin.springboot.learn.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: AOP配置(用一句话描述该类做什么)
 * @date 2018-06-15 8:27
 * @Copyright (C)2017 , Luchaoxin
 */

@Aspect
@Configuration
public class AopConfig {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(AopConfig.class);

    @Pointcut("execution(public * com.xin.springboot.learn.model.AopBean.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("Begin execute Before method...");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "log()", returning = "resVal")
    public void doAfter(Object resVal) {
        System.out.println("Begin execute AfterReturning method...");
        System.out.println("After return : return value is " + resVal);
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("Begin execute Around Method...");
        //获取开始执行的时间
        long startTime = System.currentTimeMillis();

        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("=====>统计某方法执行耗时环绕通知出错" + e.getMessage());
        }
        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        // 打印耗时的信息
        logger.info("=====>处理本次请求共耗时：{} ms", endTime - startTime);
        System.out.println("Exist Around Method...");
        return obj;
    }

    @AfterThrowing(pointcut = "log()", throwing = "ex")
    public void doRecoveryActions(Throwable ex) {
        System.out.println("Begin execute AfterThrowing Method...");
        if (null != ex) {
            System.out.println("打印日志记录异常,异常信息：" + ex.getMessage());
        }
        System.out.println("Exist AfterThrowing Method...");
    }


}
