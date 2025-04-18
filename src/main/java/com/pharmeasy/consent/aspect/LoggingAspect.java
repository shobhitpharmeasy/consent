package com.pharmeasy.consent.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.pharmeasy.consent..*(..))")
    public void allMethods() {
        // Pointcut for all methods in the com.pharmeasy.consent package
    }

    @Before("allMethods() || @annotation(com.pharmeasy.consent.annotation.Loggable)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("📥 Entering: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "allMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("📤 Exiting: " + joinPoint.getSignature() + " with result = " + result);
    }

    @AfterThrowing(pointcut = "allMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("❌ Exception in: " + joinPoint.getSignature() + " - " + ex.getMessage(), ex);
    }
}
