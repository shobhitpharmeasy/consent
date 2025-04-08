package com.pharmeasy.consent.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    // Set your threshold (e.g., 500ms)
    private static final long WARNING_THRESHOLD_MS = 500;

    @Around("execution(* com.pharmeasy.consent.service..*(..))")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        if (duration > WARNING_THRESHOLD_MS) {
            logger.warn("⚠️ SLOW METHOD: {} took {} ms", joinPoint.getSignature(), duration);
        } else {
            logger.info("✅ Method {} executed in {} ms", joinPoint.getSignature(), duration);
        }

        return result;
    }
}
