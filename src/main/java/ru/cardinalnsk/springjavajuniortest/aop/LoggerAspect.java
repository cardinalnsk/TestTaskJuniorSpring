package ru.cardinalnsk.springjavajuniortest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {


    @Pointcut("within(@ru.cardinalnsk.springjavajuniortest.aop.Loggable *)")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] args = joinPoint.getArgs();
        try {
            log.info("[CONTROLLER]: {}, start endpoint: {}, with arguments: {}", className,
                methodName, args);
            return joinPoint.proceed();
        } finally {
            log.info("[CONTROLLER]: {} end endpoint: {}", className, methodName);
        }
    }
}
