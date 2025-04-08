package com.pharmeasy.consent.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method for logging its execution details.
 * Can be used with AOP to log method entry, exit, execution time, etc.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Loggable {

    /**
     * Optional description to clarify the purpose of logging.
     */
    String value() default "";

    /**
     * Whether to log method arguments.
     */
    boolean logArgs() default true;

    /**
     * Whether to log method return value.
     */
    boolean logReturn() default true;
}
