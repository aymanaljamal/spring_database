package com.Spring.demo.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.Spring.demo.service..*(..))")
    public void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[BEFORE] Executing method: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName() + "()");
    }

    @AfterReturning("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AFTER] Method executed: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName() + "()");
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        System.out.println("[EXCEPTION] An error occurred in: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName() + "(): " + ex.getMessage());
    }
}
