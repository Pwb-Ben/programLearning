package com.programlearning.learning.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProductInvocationHandler implements InvocationHandler {

    // 目标对象
    private Object target;

    /**
     * 构造方法
     * @param target 目标对象
     */
    public ProductInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 执行目标对象的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("检查产品");
        // 执行目标对象的方法
        Object result = method.invoke(target, args);
        // 在目标对象的方法执行之后简单的打印一下
        System.out.println("添加完成");
        return result;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class interfaces) {
        return (T)Proxy.newProxyInstance(interfaces.getClassLoader(),
                new Class[]{interfaces},
                this);
    }
}
