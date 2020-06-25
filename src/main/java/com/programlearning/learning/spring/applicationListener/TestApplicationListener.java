package com.programlearning.learning.spring.applicationListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 当ioc容器加载处理完相应的bean之后，
 * 也给我们提供了一个机会（先有InitializingBean，后有ApplicationListener<ContextRefreshedEvent>），
 * 可以去做一些自己想做的事
 */
@Component
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("ContextRefreshedEvent事件被触发");
        System.out.println(contextRefreshedEvent.getApplicationContext().getDisplayName() + "容器触发");
        // 系统会存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）
        // 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码，
        // 其他的容器的初始化完成，则不做任何处理
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            System.out.println("ContextRefreshedEvent事件被触发后我做的事情");
        }
    }
}
