package com.programlearning.learning.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib原理：动态生成一个要代理类的子类，子类重写要代理的类的所有不是final的方法，
 * 在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑，它比使用java反射的JDK动态代理要快。
 *
 * CGLib底层：使用字节码处理框架ASM，来转换字节码并生成新的。
 *
 * CGLib缺点：对于final类和final方法，无法进行代理。
 *
 * JDK动态代理和CGLib动态代理的区别
 * java动态代理是利用反射机制生成一个实现代理接口的类（这个类看不见按，再jvm内存中有这个类），在调用具体方法前调用InvokeHandler来处理，
 * 核心是实现InvocationHandler接口，使用invoke()方法进行面向切面的处理，调用相应的通知。
 *
 * 而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理，核心是实现MethodInterceptor接口，
 * 使用intercept()方法进行面向切面的处理，调用相应通知。
 *
 */
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
