package com.programlearning.learning.spring.applicationRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner和ApplicationRunner我们只要实现这两个中的任何一个接口便可以完成我们的资源初始化任务，
 * 可以看到它们的加载是在容器完全启动之前。它两的区别是：前者的run方法参数是String...args，直接传入字符串，
 * 后者的参数是ApplicationArguments，对参数进行了封装。功能上是一样的
 */
@Component
public class TestApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("容器刷新上下文后，执行这里的内容");
    }
}
