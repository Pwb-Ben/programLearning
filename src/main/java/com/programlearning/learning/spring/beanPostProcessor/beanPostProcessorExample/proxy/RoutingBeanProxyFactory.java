package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class RoutingBeanProxyFactory {

    public static Object createProxy(Class targetClass, Map<String, Object> beans, String beanValue) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, beans, beanValue));
        return proxyFactory.getProxy();
    }

    static class VersionRoutingMethodInterceptor implements MethodInterceptor {
        private String classSwitch;
        private Map<String, Object> beanOfSwitchMap;

        VersionRoutingMethodInterceptor(Class targetClass, Map<String, Object> beans, String beanValue){
//            if(targetClass.isAnnotationPresent(RoutingSwitch.class)){
//                this.classSwitch = ((RoutingSwitch)targetClass.getAnnotation(RoutingSwitch.class)).value();
//            }
            this.classSwitch = beanValue;
            this.beanOfSwitchMap = beans;
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Method method = methodInvocation.getMethod();
            String switchName = classSwitch;
//            if (method.isAnnotationPresent(RoutingSwitch.class)){
//                switchName = method.getAnnotation(RoutingSwitch.class).value();
//            }
//            if (switchName == null || switchName.isBlank()){
//                throw new IllegalStateException("RoutingSwitch's value is blank, method: " + method.getName());
//            }
            return methodInvocation.getMethod().invoke(beanOfSwitchMap.get(switchName), methodInvocation.getArguments());
        }
    }
}
