package com.programlearning.learning.spring.initializingBean;

import com.programlearning.learning.spring.applicationListener.diyApplicationEvent.TestCreateEventService;
import com.programlearning.learning.spring.applicationRunner.TestApplicationRunner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * InitializingBean 是 Spring 提供的一个接口，只包含一个方法 afterPropertiesSet()。
 * 凡是实现了该接口的类，当其对应的 Bean 交由 Spring 管理后，当其必要的属性全部设置完成后，
 * Spring 会调用该 Bean 的 afterPropertiesSet()。在Bean在实例化的过程中执执行顺序为：
 * Constructor > @Autowired > @PostConstruct > InitializingBean > init-method
 *
 * 更多详见：http://www.likecs.com/show-63032.html
 * springboot容器启动详解：https://www.cnblogs.com/dennyzhangdd/p/8028950.html
 */
@Component
public class TestInitializingBean implements InitializingBean {

    @Autowired
    TestCreateEventService testCreateEventService;

    public TestInitializingBean() {
        System.out.println("TestInitializingBean的构造函数被调用");
        if (testCreateEventService == null) {
            System.out.println("TestCreateEventService的Bean还没注入");
        }
    }

    /**
     * 在具体Bean的实例化过程中执行，@PostConstruct注解的方法，会在构造方法之后执行，
     * 顺序为Constructor > @Autowired > @PostConstruct > 静态方法，
     * 所以这个注解就避免了一些需要在构造方法里使用依赖组件的尴尬（与之对应的还有@PreDestroy，在对象消亡之前执行，原理差不多)
     * 使用特点如下：
     * 1.只有一个非静态方法能使用此注解
     * 2.被注解的方法不得有任何参数
     * 3.被注解的方法返回值必须为void
     * 4.被注解方法不得抛出已检查异常
     * 5.此方法只会被执行一次
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("TestInitializingBean的PostConstruct方法被调用");
        if (testCreateEventService != null) {
            System.out.println("TestCreateEventService的Bean已经注入");
            testCreateEventService.testCreateEventPubilshService();
        }
    }

    public void initMethod() {
        System.out.println("TestInitializingBean的init-method被调用");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestInitializingBean的afterPropertiesSet方法被调用");
    }
}
