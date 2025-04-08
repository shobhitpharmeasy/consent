package com.pharmeasy.consent.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.pharmeasy.consent.service..*(..))")
    public void audit(JoinPoint joinPoint) {
        // Replace with real user fetch logic if using SecurityContext
        String currentUser = "demo-user";
        logger.info("🔍 User '{}' invoked {}", currentUser, joinPoint.getSignature());
    }
}
