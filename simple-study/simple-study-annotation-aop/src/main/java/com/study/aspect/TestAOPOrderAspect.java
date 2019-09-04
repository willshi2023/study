package com.study.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAOPOrderAspect {
    // @Pointcut("execution(public * com.study.annotation.TestController.*(..)) && @annotation(com.study.annotation.MyAnnotation)" )
    @Pointcut("@annotation(com.study.annotation.TestAOPOrderAnnotation)" )
    public void addAdvice(){}
    @Around("addAdvice()")
    public Object Interceptor(ProceedingJoinPoint joinPoint){
        System.out.println("======进入Around切面方法======");
        Object retmsg=null;
        try {
            retmsg = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("======完成Around切面方法======,得到结果是:" + retmsg);
        return retmsg;
    }
    @Before("addAdvice()")
    public void before(JoinPoint joinPoint){
        System.out.println("======进入Before切面方法======");
    }

    @After("addAdvice()")
    public void after() {
        System.out.println("======进入After切面方法======");
    }
}
