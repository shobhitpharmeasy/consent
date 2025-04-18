package com.pharmeasy.consent.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class ArgsLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.pharmeasy.consent.service..*(..))")
    public void logArgs(JoinPoint joinPoint) {
        logger.debug("📦 Args for {}: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }
}
