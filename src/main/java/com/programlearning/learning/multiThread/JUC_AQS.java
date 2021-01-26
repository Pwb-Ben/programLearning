package com.programlearning.learning.multiThread;

import java.util.concurrent.*;

public class JUC_AQS {

    private static final int TOTAL_THREAD = 10;

    private static final int CLIENT_COUNT = 3;

    /**
     * 用来控制一个线程等待多个线程。
     * 维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，那些因为调用 await() 方
     * 法而在等待的线程就会被唤醒。
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(TOTAL_THREAD);

    /**
     * 用来控制多个线程互相等待，只有当多个线程都到达时，这些线程才会继续执行。
     * 和 CountdownLatch 相似，都是通过维护计数器来实现的。线程执行 await() 方法之后计数器会减 1，并进行等待，
     * 直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行。
     * CyclicBarrier 和 CountdownLatch 的一个区别是，CyclicBarrier 的计数器通过调用 reset() 方法可以循环使用，所以它才叫做循环屏障。
     * CyclicBarrier 有两个构造函数，其中 parties 指示计数器的初始值，barrierAction 在所有线程都到达屏障的时候会执行一次。
     */
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(TOTAL_THREAD);

    /**
     * Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。
     */
    private static Semaphore semaphore = new Semaphore(CLIENT_COUNT);

    static class CountDownLatchExample implements Runnable{
        @Override
        public void run() {
            System.out.println("run..");
            countDownLatch.countDown();
        }
    }

    static class CyclicBarrierExample implements Runnable{
        @Override
        public void run() {
            System.out.println("before..");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println("after..");
        }
    }

    /**
     * 以下代码模拟了对某个服务的并发请求，每次只能有 3 个客户端同时访问，请求总数为 10
     */
    static class SemaphoreExample implements Runnable{
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(semaphore.availablePermits() + " ");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < TOTAL_THREAD; i++) {
            executorService.execute(new CountDownLatchExample());
        }
        try {
            System.out.println("before await..");
            countDownLatch.await();
            System.out.println("after await..");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < TOTAL_THREAD; i++){
            executorService.execute(new CyclicBarrierExample());
        }

        for (int i = 0; i < TOTAL_THREAD; i++){
            executorService.execute(new SemaphoreExample());
        }

        executorService.shutdown();
    }
}
