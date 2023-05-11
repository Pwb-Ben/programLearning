package com.programlearning.learning.multiThread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer{

    public ProducerAndConsumer() {
    }

    /**
     * 1、永远在synchronized的函数或代码块里使用wait、notify和notifyAll，不然Java虚拟机会生成IllegalMonitorStateException。
     * 2、永远在while循环里而不是if语句下使用wait。这样，循环会在线程睡眠前后都检查wait的条件，并在条件实际上并未改变的情况下处理唤醒通知。
     * 3、永远在多线程间共享的对象上使用wait。
     * 4、notify随机通知一个阻塞在对象上的线程，notifyAll通知阻塞在对象上所有的线程。
     */
    static class Producer implements Runnable{

        private Queue<Integer> queue;
        private int maxSize;

        public Producer(Queue<Integer> queue, int maxSize) {
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()){
                synchronized (queue){
                    while (queue.size() == maxSize){
                        try{
                            System.out.println("Queue is Full");
                            queue.wait();
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("Produce " + i);
                    queue.add(i);
                    queue.notifyAll();
                }
            }
        }
    }
    static class Consumer implements Runnable{

        private Queue<Integer> queue;
        private int maxSize;

        public Consumer(Queue<Integer> queue, int maxSize) {
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()){
                synchronized (queue){
                    while (queue.isEmpty()){
                        System.out.println("Queue is Empty");
                        try{
                            queue.wait();
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                    int v = queue.remove();
                    System.out.println("Consume " + v);
                    queue.notifyAll();
                }
            }
        }
    }

    /**
     * java.util.concurrent 类库中提供了 Condition 类来实现线程之间的协调，可以在 Condition 上调用 await() 方法使线程等待，
     * 其它线程调用 signal() 或 signalAll() 方法唤醒等待的线程。
     * 相比于 wait() 这种等待方式，await() 可以指定等待的条件，因此更加灵活。
     */
    static class AwaitSignalExample{

        private Lock lock = new ReentrantLock();

        private Condition condition = lock.newCondition();

        public void before() {
            lock.lock();
            try {
                System.out.println("before");
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void after() {
            lock.lock();
            try {
                condition.await();
                System.out.println("after");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Producer p = new Producer(queue, maxSize);
        Consumer c = new Consumer(queue, maxSize);
        Thread pT = new Thread(p);
        Thread pC = new Thread(c);
        pT.start();
        pC.start();

        try {
            Thread.sleep(3000);
            pT.interrupt();
            pC.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        AwaitSignalExample example = new AwaitSignalExample();
        executorService.execute(example::after);
        executorService.execute(example::before);
        executorService.shutdown();
    }
}
