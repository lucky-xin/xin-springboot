package com.xin.springboot;

import com.xin.springboot.learn.model.AopBean;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AopInfrastructureBean;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: jsbdtest
 * @date 2018-06-24 15:31
 * @Copyright (C)2018 , Luchaoxin
 */
public class JdbcTest {

    @Test
    public void testMethod() {
        Class<?> beanClass = AopBean.class;
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass) ||
                AopInfrastructureBean.class.isAssignableFrom(beanClass);
        System.out.println(retVal);
    }
}
