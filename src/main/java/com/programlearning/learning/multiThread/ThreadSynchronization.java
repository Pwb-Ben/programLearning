package com.programlearning.learning.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSynchronization {

    private ThreadSynchronization() {
    }

    /**
     * 运行结果：
     * 第一种情况：李四（线程）当执行到30时会CPU时间让掉，这时张三（线程）抢到CPU时间并执行。
     * 第二种情况：李四（线程）当执行到30时会CPU时间让掉，这时李四（线程）抢到CPU时间并执行。
     *
     * 网友A:
     * 博主我复制您的代码运行后始终不出现有说服力的结果，始终是张三李四分别30/31，没有说服力，跟您的结果有些出入，不知道问题出在哪儿，不知道您是否可以帮我解答，谢谢。
     * 网友B的回复：
     * 这段代码当然不能给出有说服力的结果.
     * yield的本质是把当前线程重新置入抢CPU时间的”队列”(队列只是说所有线程都在一个起跑线上.并非真正意义上的队列)。
     * 如果李四抢的好.当然就是李四继续上. 张三抢的好就是张三的. 这个示例里面没有耗时操作. 所以看不出什么区别。
     *
     */
     class YieldTest extends Thread {

        public YieldTest(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 1; i <= 50; i++) {
                System.out.println("" + this.getName() + "-----" + i);
                // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                if (i == 30) {
                    this.yield();
                }
            }
        }
    }

    public void runYieldTest() {
        YieldTest yt1 = new YieldTest("张三");
        YieldTest yt2 = new YieldTest("李四");
        yt1.start();
        yt2.start();
    }

    /**
     * 比较
     * 1. 锁的实现
     * synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
     * 2. 性能
     * 新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。
     * 3. 等待可中断
     * 当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。
     * ReentrantLock 可中断，而 synchronized 不行。
     * 4. 公平锁
     * 公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
     * synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。
     * 5. 锁绑定多个条件
     * 一个 ReentrantLock 可以同时绑定多个 Condition 对象。
     * 6. 使用选择
     * 除非需要使用 ReentrantLock 的高级功能，否则优先使用 synchronized。这是因为 synchronized 是 JVM 实现的一
     * 种锁机制，JVM 原生地支持它，而 ReentrantLock 不是所有的 JDK 版本都支持。并且使用 synchronized 不用担心没
     * 有释放锁而导致死锁问题，因为 JVM 会确保锁的释放。
     */
    class ReentrantLockExample {
         private Lock lock = new ReentrantLock();

         public void func() {
             lock.lock();
             try {
                 for (int i = 0; i < 10; i++) {
                     System.out.print(i + " ");
                 }
             } finally {
                lock.unlock(); // 确保释放锁，从而避免发生死锁。
             }
         }
    }

    public void runReentrantLockExample(){
        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> reentrantLockExample.func());
        executorService.execute(() -> reentrantLockExample.func());
    }

    public static void main(String[] args) {
        ThreadSynchronization threadSynchronization = new ThreadSynchronization();
        threadSynchronization.runYieldTest();

        threadSynchronization.runReentrantLockExample();
    }
}
