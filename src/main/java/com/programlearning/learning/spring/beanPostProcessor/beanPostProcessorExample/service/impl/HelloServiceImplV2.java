package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.impl;

import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImplV2 implements HelloService {

    @Override
    public String sayHello() {
        return "Hello from V2";
    }

    @Override
    public String sayHi() {
        return "Hi from V2";
    }
}
