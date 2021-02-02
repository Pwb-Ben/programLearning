package com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.impl;

import com.programlearning.learning.spring.beanPostProcessor.beanPostProcessorExample.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImplV1 implements HelloService {

    @Override
    public String sayHello() {
        return "Hello from V1";
    }

    @Override
    public String sayHi() {
        return "Hi from V1";
    }
}
