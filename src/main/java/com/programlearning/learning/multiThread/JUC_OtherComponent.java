package com.programlearning.learning.multiThread;

import java.util.concurrent.*;

public class JUC_OtherComponent {

    static class ForkJoinExample extends RecursiveTask<Integer>{

        private final int threshold = 5;

        private int first; private int last;

        public ForkJoinExample(int first, int last) { this.first = first; this.last = last; }

        @Override
        protected Integer compute() {
            int result = 0;
            if (last - first <= threshold) {
                // 任务足够小则直接计算
                for (int i = first; i <= last; i++) {
                    result += i;
                }
            } else {
                // 拆分成小任务
                int middle = first + (last - first) / 2;
                ForkJoinExample leftTask = new ForkJoinExample(first, middle);
                ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
                leftTask.fork();
                rightTask.fork();
                result = leftTask.join() + rightTask.join();
            }
            return result;
        }
    }

    public static class ProducerAndConsumerWithBlockingQueue{

        private Runnable producer;

        private Runnable consumer;

        private ArrayBlockingQueue<String> blockingDeque;

        private int count = 1;

        public ProducerAndConsumerWithBlockingQueue(int queueSize){
            blockingDeque = new ArrayBlockingQueue<>(queueSize);

            producer = () -> {
                while (!Thread.interrupted()){
                    try {
                        String tmp = "produce"+count;
                        blockingDeque.put(tmp);
                        count++;
                        System.out.println(tmp);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            };

            consumer = () -> {
                while (!Thread.interrupted()){
                    try {
                        Object p = blockingDeque.take();
                        System.out.println("consume " + p);
                    } catch (InterruptedException e) {
                        break;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            };
        }

        public Runnable getConsumer() {
            return consumer;
        }

        public Runnable getProducer() {
            return producer;
        }
    }


    public static void main(String[] args) {
        /**
         * FutureTask 实现了 RunnableFuture 接口，该接口继承自 Runnable 和 Future 接口，这使得 FutureTask 既可以当做一个任务执行，也可以有返回值。
         * FutureTask 可用于异步获取执行结果或取消执行任务的场景。当一个计算任务需要执行很长时间，那么就可以用
         * FutureTask 来封装这个任务，主线程在完成自己的任务之后再去获取结果。
         */
        FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
            int result = 0;
            for (int i = 0; i < 10; i++) {
                Thread.sleep(10);
                result += i;
            }
            return result;
        });
        Thread computeThread = new Thread(futureTask);
        computeThread.start();
        try {
            System.out.println("任务返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * ForkJoin
         * 主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算。
         * ForkJoinPool 实现了工作窃取算法来提高 CPU 的利用率。每个线程都维护了一个双端队列，用来存储需要执行的任
         * 务。工作窃取算法允许空闲的线程从其它线程的双端队列中窃取一个任务来执行。窃取的任务必须是最晚的任务，避
         * 免和队列所属线程发生竞争。例如下图中，Thread2 从 Thread1 的队列中拿出最晚的 Task1 任务，Thread1 会拿出
         * Task2 来执行，这样就避免发生竞争。但是如果队列中只有一个任务时还是会发生竞争。
         */
        ForkJoinExample example = new ForkJoinExample(1, 10000);
        // ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数。
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future result = forkJoinPool.submit(example);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * 使用BlockingQueue实现消费者-生产者模型
         */
        ProducerAndConsumerWithBlockingQueue producerAndConsumerWithBlockingQueue = new ProducerAndConsumerWithBlockingQueue(5);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producerAndConsumerWithBlockingQueue.getProducer());
        executorService.execute(producerAndConsumerWithBlockingQueue.getConsumer());
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //executorService.shutdownNow();
    }
}
