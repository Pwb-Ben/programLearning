package com.programlearning.learning.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池和装修公司
 * 以运营一家装修公司做个比喻。公司在办公地点等待客户来提交装修请求；公司有固定数量的正式工以维持运转；
 * 旺季业务较多时，新来的客户请求会被排期，比如接单后告诉用户一个月后才能开始装修；当排期太多时，为避免用户等太久，
 * 公司会通过某些渠道（比如人才市场、熟人介绍等）雇佣一些临时工（注意，招聘临时工是在排期排满之后）；
 * 如果临时工也忙不过来，公司将决定不再接收新的客户，直接拒单。
 *
 * 线程池就是程序中的“装修公司”，代劳各种脏活累活。上面的过程对应到线程池上：
 *
 * // Java线程池的完整构造函数
 * public ThreadPoolExecutor(
 *   int corePoolSize, // 正式工数量
 *   int maximumPoolSize, // 工人数量上限，包括正式工和临时工
 *   long keepAliveTime, TimeUnit unit, // 临时工游手好闲的最长时间，超过这个时间将被解雇
 *   BlockingQueue<Runnable> workQueue, // 排期队列
 *   ThreadFactory threadFactory, // 招人渠道
 *   RejectedExecutionHandler handler) // 拒单方式
 *
 */
public class ExecutorsExample {

    private static ExecutorService executorService;

    private CountDownLatch countDownLatch;

    /**
     * 线程池长期维持的线程数，即使线程处于Idle状态，也不会回收。
     */
    private int poolSize;

    public int getPoolSize(){
        return this.poolSize;
    }

    /**
     * 线程数的上限
     */
    private int maxPoolSize;

    /**
     * 超过corePoolSize的线程的idle时长，超过这个时间，多余的线程会被回收。
     */
    private long keepAliveTime;

    /**
     * 时间单位
     */
    private TimeUnit unit;

    /**
     * 任务的排队队列
     * SynchronousQueue             SynchronousQueue没有容量，是无缓冲等待队列，是一个不存储元素的阻塞队列，
     *                              会直接将任务交给消费者，必须等队列中的添加元素被消费后才能继续添加新的元素。
     *                              拥有公平（FIFO）和非公平(LIFO)策略，非公平侧罗会导致一些数据永远无法被消费的情况。
     *                              使用SynchronousQueue阻塞队列一般要求maximumPoolSizes为无界，避免线程拒绝执行操作。
     *
     * LinkedBlockingQueue          一个单向链表+两把锁+两个条件
     *                              两把锁，一把用于入队，一把用于出队，有效的避免了入队与出队时使用一把锁带来的竞争。
     *                              在入队与出队都高并发的情况下，性能比ArrayBlockingQueue高很多
     *                              采用了链表，最大容量为整数最大值，可看做容量无限
     *
     * LinkedBlockingDeque          双端队列（两头都可以进出）其他性质与LinkedBlockingQueue相同
     *
     * ArrayBlockingQueue           底层数组实现，必须传数组长度初始化
     *                              一个对象数组+一把锁+两个条件，入队与出队都用同一把锁，
     *                              在只有入队高并发或出队高并发的情况下，因为操作数组，且不需要扩容，性能很高，
     *                              采用了数组，必须指定大小，即容量有限
     *
     * PriorityBlockingQueue        底层也是数组实现，可以不传长度，默认长度是11，可以对入队列的元素进行排序
     *                              在构造函数需传入comparator,用于插入元素时继续排序，若没有传入comparator，则插入的元素必须实现Comparatable接口。
     */
    private BlockingQueue queue;

    /**
     * 拒绝策略
     * AbortPolicy	            抛出RejectedExecutionException
     * DiscardPolicy	        什么也不做，直接忽略
     * DiscardOldestPolicy	    丢弃执行队列中最老的任务，尝试为当前提交的任务腾出位置
     * CallerRunsPolicy	        直接由提交任务者执行这个任务
     */
    private RejectedExecutionHandler policy;

    /**
     * 新线程的产生方式
     */
    private ThreadFactory threadFactory;

    /**
     * 无参构造函数，默认配置，控制任务队列大小，防止OOM
     */
    public ExecutorsExample(){
        this.poolSize = Runtime.getRuntime().availableProcessors() * 2;
        this.maxPoolSize = poolSize;
        this.keepAliveTime = 0;
        this.unit = TimeUnit.SECONDS;
        this.queue = new ArrayBlockingQueue<>(512);
        this.policy = new ThreadPoolExecutor.DiscardPolicy();
        this.threadFactory = Executors.defaultThreadFactory();
    }

    /**
     * 自定义配置初始化线程池
     * @param poolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param unit
     * @param queue
     * @param policy
     */
    ExecutorsExample(int poolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingDeque queue, RejectedExecutionHandler policy){
        this.poolSize = poolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.queue = queue;
        this.policy = policy;
        this.threadFactory = Executors.defaultThreadFactory();
    }

    /**
     * 自定义配置初始化线程池
     * @param poolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @param unit
     * @param queue
     * @param policy
     * @param threadFactory
     */
    ExecutorsExample(int poolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingDeque queue, RejectedExecutionHandler policy, ThreadFactory threadFactory){
        this.poolSize = poolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.queue = queue;
        this.policy = policy;
        this.threadFactory = threadFactory==null?Executors.defaultThreadFactory():threadFactory;
    }

    /**
     * newFixedThreadPool(int nThreads)	    创建固定大小的线程池
     * newSingleThreadExecutor()	        创建只有一个线程的线程池
     * newCachedThreadPool()	            创建一个不限线程数上限的线程池，任何提交的任务都将立即执行
     */
    public ExecutorService getExecutorServiceWithNewFixedThreadPool(int nThreads){
        if (executorService==null){
            synchronized (ExecutorsExample.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(nThreads);
                }
            }
        }
        return executorService;
    }

    public ExecutorService getExecutorServiceWithNewSingleThreadExecutor(){
        if (executorService==null){
            synchronized (ExecutorsExample.class) {
                if (executorService == null) {
                    executorService = Executors.newSingleThreadExecutor();
                }
            }
        }
        return executorService;
    }

    public ExecutorService getExecutorServiceWithNewCachedThreadPool(){
        if (executorService==null){
            synchronized (ExecutorsExample.class) {
                if (executorService == null) {
                    executorService = Executors.newCachedThreadPool();
                }
            }
        }
        return executorService;
    }

    /**
     * 使用ThreadPoolExecutor构造方法
     * @return
     */
    public ExecutorService getExecutorService(){
        if (executorService == null) {
            synchronized (ExecutorsExample.class) {
                if (executorService == null) {
                    /**
                     * 不要使用Executors.newXXXThreadPool()(Executors.newFixedThreadPool(int nThreads);)快捷方法创建线程池，
                     * 因为这种方式会使用无界的任务队列，为避免OOM，我们应该使用ThreadPoolExecutor的构造方法手动指定队列的最大长度
                     */
                    executorService = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, unit, queue, threadFactory, policy);
                }
            }
        }
        return executorService;
    }

    public static void main(String[] args) {

        ExecutorsExample executorsExample = new ExecutorsExample();
        ExecutorService executorService = executorsExample.getExecutorService();

        ArrayList<Runnable> runnableTasks = new ArrayList<>();
        ArrayList<Callable> callableTasks = new ArrayList<>();

        for(int i = 0; i<10; i++) {
            runnableTasks.add(() -> {
                System.out.println("runnableTask is running...");
            });
            callableTasks.add(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return "callableTask finish!";
                }
            });
        }

        // void execute(Runnable command) 不关心返回结果
        for(Runnable r:runnableTasks) {
            executorService.execute(r);
        }

        // Future<T> submit(Callable<T> task) 有返回结果
        for(Callable c:callableTasks) {
            Future future = executorService.submit(c);
            try {
                Object result = future.get();
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Future<?> submit(Runnable task) 没有返回结果，get方法总返回空
        for(Runnable r:runnableTasks){
            Future<?> future = executorService.submit(r);
            try {
                Object result = future.get();
                System.out.println(result==null?"result is null":"result is not null");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 构造器
        CompletionService<Object> executorCompletionService = new ExecutorCompletionService<Object>(executorService);
        for (Callable c:callableTasks){
            executorCompletionService.submit(c);
        }

        /**
         * 获取多个返回结果，ExecutorCompletionService，该类的take()方法总是阻塞等待某一个任务完成，
         * 然后返回该任务的Future对象。向CompletionService批量提交任务后，只需调用相同次数的CompletionService.take()方法，
         * 就能获取所有任务的执行结果，获取顺序是任意的，取决于任务的完成顺序
         */
        for (int i = 0; i<callableTasks.size(); i++) {
            try {
                Object result = executorCompletionService.take().get();
                System.out.println("第"+(i+1)+"个任务完成，结果"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        /**
         * 单个任务的超时时间
         * V Future.get(long timeout, TimeUnit unit)方法可以指定等待的超时时间，超时未完成会抛出TimeoutException
         */
        Future future = executorService.submit(() -> {
            System.out.println("线程开始睡眠...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            Object result = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("线程耗时过长，结果返回失败");
            e.printStackTrace();
        }

        /**
         * 多个任务的超时时间
         * 等待多个任务完成，并设置最大等待时间，可以通过CountDownLatch完成
         */
        CountDownLatch latch = new CountDownLatch(10);
        for (int i=0; i<10; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("多任务线程正在执行");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            });
        }
        boolean flag = false;
        try {
            flag = latch.await(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //用future.cancel(true)可以中断某一个线程
        if (flag){
            System.out.println("多任务执行完毕，关闭线程池");
            executorService.shutdown();
        }else {
            System.out.println("任务执行超时，强制关闭线程池");
            List<Runnable> l = executorService.shutdownNow();
            System.out.println("有"+l.size()+"个任务没有执行");
        }
    }
}
