package com.programlearning.learning.spring.applicationListener.diyApplicationEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

/**
 * 触发要实现ApplicationContextAware，用于引入ApplicationContext，由于bookingService也 是spring组件，
 * 所以在系统启动的时候，ApplicationContext已经注入。
 */
@Service
public class TestCreateEventService implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * service和listener是同步的，在service中的testCreateEventPubilshService有注册@Transactional的情况下，
     * listener中的onApplicationEvent方法和service中的testCreateEventPubilshService是在同一个tansaction下。
     * 如果要做异步，需要通过MQ或者数据库中转。
     */
//    @Transactional(rollbackFor = Exception.class)
    public void testCreateEventPubilshService() {
        TestCreateEvent testCreateEvent = new TestCreateEvent(this, "TestCreateEventService事件发布");
        this.applicationContext.publishEvent(testCreateEvent);
    }
}
