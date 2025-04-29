package com.programlearning.learning.redisson;

import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MyRunnableTask implements Runnable, Serializable {

    @RInject
    private RedissonClient client;

    @Override
    public void run() {
        LocalDateTime date = LocalDateTime.now();
        System.out.println("task run ...");
        System.out.println("today is " + date);
        System.out.println("redisson client is " + client.getId());
    }
}