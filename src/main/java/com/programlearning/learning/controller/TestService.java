package com.programlearning.learning.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static final Log log = LogFactory.get();

//    @Async
    @Cacheable("hello")
    public Integer sayHello() {
        log.info("async-------------->");
        log.info("async say hello");
        return 1;
    }
}
