package com.programlearning.learning.spring.applicationListener.diyApplicationEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * listener的主要作用是解耦
 * 用@Component把listener注册成了spring的组件，这样listener的用途是在runtime的时候解耦。
 * 而如果我们把listener用配置文件的方式注册的话，主要用途是在部署的时候解耦。在实际应用中，两种情况都有。
 */
@Component
public class TestCreateEventApplicationListener implements ApplicationListener<TestCreateEvent> {

    @Override
    public void onApplicationEvent(TestCreateEvent testCreateEvent) {
        System.out.println("TestCreateEvent触发，内容:" + testCreateEvent.getString());
    }
}
