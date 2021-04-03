package com.programlearning.learning.multiThread;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

public class Synchronized {

    /**
     * https://pic3.zhimg.com/v2-0b1fc890f9dc3be4e615febde96aa2e2_b.jpeg
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //默认延迟4s才会开启偏向锁，休眠5s确保开启偏向锁
        Thread.sleep(5000);

        List<Synchronized> list = new ArrayList<>();
        new Thread(()->{
            System.out.println("t1线程开始运行");
            for (int i=0;i<30;i++){
                //这里必须要new不同的对象，不能共用同一个对象
                Synchronized object = new Synchronized();
                synchronized (object){
                    list.add(object);
                    // 101三位数说明：
                    // 第一位：0-表示非偏向 1-表示偏向
                    // 后两位：00-表示轻量级锁 01-表示偏向锁 10表示重量级锁
                    System.out.println("t1线程第" + (i+1) + "对象:" + ClassLayout.parseInstance(object).toPrintable());
                }
            }
        },"t1").start();

        try {
            //确保t1创建对象完毕
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------");
        new Thread(()->{
            System.out.println("t2线程开始运行");
            for (int j=0;j<30;j++){
                Synchronized object = list.get(j);
                synchronized (object){
                    System.out.println("t2线程第" + (j+1) + "对象:" + ClassLayout.parseInstance(object).toPrintable());
                }
            }
        },"t2").start();
    }
}
