package com.programlearning.learning.redisson;

import cn.hutool.core.thread.ThreadUtil;
import org.redisson.Redisson;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class SchedulerMaster {

    public static void main(String[] args) {
        //任务队列名
        String key = "service:task_scheduler";
        //连接配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456").setDatabase(0);
        System.out.println("创建master实例");
        RedissonClient client = Redisson.create(config);
        RScheduledExecutorService executorService = client.getExecutorService(key);
        System.out.println("发布任务");
        executorService.scheduleAtFixedRate(new MyRunnableTask(), 2, 1, TimeUnit.SECONDS);
        ThreadUtil.sleep(10000);
        System.out.println("遍历并取消任务");
        executorService.getTaskIds().forEach(id -> {
            System.out.println("取消任务 任务id: " + id);
            executorService.cancelTask(id);
        });
    }
}