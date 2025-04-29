package com.programlearning.learning.redisson;

import org.redisson.Redisson;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.config.Config;

public class SchedulerWorker {

    public static void main(String[] args) {
        //任务队列名
        String key = "service:task_scheduler";
        //连接配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456").setDatabase(0);
        //模拟多个worker实例
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("创建worker实例");
                RedissonClient client = Redisson.create(config);
                RScheduledExecutorService executorService = client.getExecutorService(key);
                System.out.println("注册为worker节点");
                executorService.registerWorkers(WorkerOptions.defaults());
            }).start();
        }
    }
}