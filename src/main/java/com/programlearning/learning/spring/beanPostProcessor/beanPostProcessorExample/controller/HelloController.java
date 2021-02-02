package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.controller;

import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.annotation.RoutingInjected;
import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RoutingInjected("helloServiceImplV2")
    private HelloService helloService;

    @GetMapping
    public String sayHello(){
        return helloService.sayHello();
    }
}
