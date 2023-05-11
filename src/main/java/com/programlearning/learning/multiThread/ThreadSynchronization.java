package com.programlearning.learning.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

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
    static class YieldTest extends Thread {

        public YieldTest(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 1; i <= 50; i++) {
                System.out.println("" + this.getName() + "-----" + i);
                // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                if (i == 30) {
                    Thread.yield();
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
    static class ReentrantLockExample {
         private Lock lock = new ReentrantLock(true); //true:公平锁，false:非公平锁

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
        executorService.execute(reentrantLockExample::func);
        executorService.execute(reentrantLockExample::func);
        executorService.shutdown();
    }

    /**
     * ReentrantReadWriteLock使得多个读线程同时持有读锁（只要写锁未被占用），而写锁是独占的。
     * 但是，读写锁如果使用不当，很容易产生“饥饿”问题：
     * 比如在读线程非常多，写线程很少的情况下，很容易导致写线程“饥饿”，虽然使用“公平”策略可以一定程度上缓解这个问题，
     * 但是“公平”策略是以牺牲系统吞吐量为代价的。（在ReentrantLock类的介绍章节中，介绍过这种情况）
     *
     * StampedLock采用乐观读方式缓解饥饿问题
     *
     * StampedLock的主要特点概括一下，有以下几点：
     * 1、所有获取锁的方法，都返回一个邮戳（Stamp），Stamp为0表示获取失败，其余都表示成功；
     * 2、所有释放锁的方法，都需要一个邮戳（Stamp），这个Stamp必须是和成功获取锁时得到的Stamp一致；
     * 3、StampedLock是不可重入的；（如果一个线程已经持有了写锁，再去获取写锁的话就会造成死锁）
     * 4、StampedLock有三种访问模式：
     *      ①Reading（读模式）：功能和ReentrantReadWriteLock的读锁类似
     *      ②Writing（写模式）：功能和ReentrantReadWriteLock的写锁类似
     *      ③Optimistic reading（乐观读模式）：这是一种优化的读模式。
     * 5、StampedLock支持读锁和写锁的相互转换，我们知道RRW中，当线程获取到写锁后，可以降级为读锁，但是读锁是不能直接升级为写锁的。
     *    StampedLock提供了读锁和写锁相互转换的功能，使得该类支持更多的应用场景。
     * 6、无论写锁还是读锁，都不支持Conditon等待
     *
     * 我们知道，在ReentrantReadWriteLock中，当读锁被使用时，如果有线程尝试获取写锁，该写线程会阻塞。
     * 但是，在Optimistic reading中，即使读线程获取到了读锁，写线程尝试获取写锁也不会阻塞，这相当于对读模式的优化，
     * 但是可能会导致数据不一致的问题。所以，当使用Optimistic reading获取到读锁时，必须对获取结果进行校验。
     *
     */
    class StampedLockExample{
        private StampedLock stampedLock = new StampedLock();
        private double x;
        private double y;

        private void move(double deltaX, double deltaY) {
            long stamp = stampedLock.writeLock();    //涉及对共享资源的修改，使用写锁-独占操作
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }

        /**
         * 使用乐观读锁访问共享资源
         * 注意：乐观读锁在保证数据一致性上需要拷贝一份要操作的变量到方法栈，并且在操作数据时候可能其他写线程已经修改了数据，
         * 而我们操作的是方法栈里面的数据，也就是一个快照，所以最多返回的不是最新的数据，但是一致性还是得到保障的。
         */
        private double distanceFromOrigin() {
            long stamp = stampedLock.tryOptimisticRead();    // 使用乐观读锁
            double currentX = x, currentY = y;      // 拷贝共享资源到本地方法栈中
            if (!stampedLock.validate(stamp)) {              // 如果有写锁被占用，可能造成数据不一致，所以要切换到普通读锁模式
                stamp = stampedLock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        private void moveIfAtOrigin(double newX, double newY) { // 锁升级
            // Could instead start with optimistic, not read mode
            long stamp = stampedLock.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    long ws = stampedLock.tryConvertToWriteLock(stamp);  //读锁转换为写锁
                    if (ws != 0L) {
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        stampedLock.unlockRead(stamp);
                        stamp = stampedLock.writeLock();
                    }
                }
            } finally {
                stampedLock.unlock(stamp);
            }
        }
    }

    private void runStampedLockExample(){
        StampedLockExample stampedLockExample = new StampedLockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        stampedLockExample.moveIfAtOrigin(2,3);
        System.out.println(stampedLockExample.distanceFromOrigin());
    }

    public static void main(String[] args) {
        ThreadSynchronization threadSynchronization = new ThreadSynchronization();
        threadSynchronization.runYieldTest();
        threadSynchronization.runReentrantLockExample();
        threadSynchronization.runStampedLockExample();
    }
}
