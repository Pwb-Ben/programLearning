package com.programlearning.learning.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProductMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("检查产品");
        // 执行目标对象的方法
        Object result = methodProxy.invokeSuper(o,objects);
        // 在目标对象的方法执行之后简单的打印一下
        System.out.println("添加完成");
        return result;
    }

    public <T> T getProxy(Class clazz){
        //字节码增强的一个类
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(clazz);
        //设置回调类
        enhancer.setCallback(this);
        //创建代理类
        return (T)enhancer.create();
    }
}
