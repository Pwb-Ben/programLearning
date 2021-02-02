package com.programlearning.learning;

import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.annotation.RoutingInjected;
import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningApplicationTests {

    @RoutingInjected("helloServiceImplV1")
    private HelloService helloService;

    private String sayHello(){
        return helloService.sayHello();
    }

    @Test
    void contextLoads() {
        System.out.println(sayHello());
    }

}
