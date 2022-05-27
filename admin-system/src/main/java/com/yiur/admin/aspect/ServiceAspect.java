package com.yiur.admin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Yiur
 */
@Aspect
@Component
public class ServiceAspect {

    @Pointcut("execution(public * com.yiur.admin.service.*.*(..))")
    public void pointcut() {
    }


    @Before("pointcut()")
    public void deBefore(JoinPoint joinpoint) {
    }

}
