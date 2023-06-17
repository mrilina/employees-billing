package com.employees.billing.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Аспекты, реализуют логирование начала и окончания работы методов репозитория.
 *
 * @author Irina Ilina
 */
@Component
@Aspect
public class LoggingAspect {

    @Around("execution(* com.employees.billing.repository.*.*(..))")
    public Object aroundAllRepositoryMethodsAdvice(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();

        System.out.println("Начало выполнения метода " + methodName);

        Object targetMethodResult = proceedingJoinPoint.proceed();

        System.out.println("Окончание выполнения метода " + methodName);

        return targetMethodResult;
    }
}
