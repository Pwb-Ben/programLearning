package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.processor;

import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.annotation.RoutingInjected;
import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.proxy.RoutingBeanProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class RoutingBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    @Autowired
    public RoutingBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName){
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetCls = bean.getClass();
        Field[] targetFld = targetCls.getDeclaredFields();
        for (Field field : targetFld) {
            if (field.isAnnotationPresent(RoutingInjected.class)) {
                if (!field.getType().isInterface()) {
                    throw new BeanCreationException("RoutingInjected field must be declared as an interface:" + field.getName() + " @Class " + targetCls.getName());
                }
                try {
                    String beanValue = field.getAnnotation(RoutingInjected.class).value();
                    if (beanValue.isBlank()){
                        throw new BeanCreationException("RoutingInjected's value is blank, field: " + field.getName() + " @Class " + targetCls.getName());
                    }
                    this.handleRoutingInjected(field, bean, field.getType(), beanValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private void handleRoutingInjected(Field field, Object bean, Class type, String beanValue) throws IllegalAccessException {
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        if (candidates.size() == 1) {
            field.set(bean, candidates.values().iterator().next());
        } else {
            Object proxy = RoutingBeanProxyFactory.createProxy(type, candidates, beanValue);
            field.set(bean, proxy);
        }
    }
}
