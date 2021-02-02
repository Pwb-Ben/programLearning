package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author pwbco
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingInjected {
    /**
     * 需要注入的实现类Bean名称
     * @return
     */
    String value() default "";
}
