package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author pwbco
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingSwitch {
    /**
     * 需要注入的实现类Bean名称
     * @return
     */
    String value() default "";
}
