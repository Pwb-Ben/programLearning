package com.programlearning.learning.IO.fileNIO;

import com.programlearning.learning.threadPool.ExecutorsExample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 反面教材！
 */
public class FileIO {

    private static final int FINAL_SIZE = 100 * 1024 * 1024;

    private static final Object object = new Object();

    public static void main(String[] args) {
        RandomAccessFile in = null, out = null;

        long s1 = System.currentTimeMillis();

        try {
            in = new RandomAccessFile("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786.mp4", "r");

            ExecutorsExample executorsExample = new ExecutorsExample();
            ExecutorService executorService = executorsExample.getExecutorServiceWithNewFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

            // 以固定大小分块
            long inFileSize = in.length();
            System.out.println("文件大小：" + inFileSize);

            long blockingNum = inFileSize/FINAL_SIZE;
            System.out.println("分块数：" + blockingNum);

            // 完成计数器
            CountDownLatch countDownLatch = new CountDownLatch((int)blockingNum);

            for (int i = 0; i < blockingNum; i++){
                long finalI = i;
                executorService.submit(() -> {synchronized (object){
                    System.out.println("第" + finalI + "个任务启动");
                    try {
                        RandomAccessFile finalIn = new RandomAccessFile("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786.mp4", "r");
                        RandomAccessFile finalOut = new RandomAccessFile("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786_1.mp4", "rwd");

                        byte[] b = new byte[FINAL_SIZE];
                        long off = finalI * FINAL_SIZE;
                        System.out.println("第" + finalI + "个任务in的文件读指针位置：" + finalIn.getFilePointer() + ",应该偏移的位置：" + off + "，FINAL_SIZE大小：" + FINAL_SIZE);
                        finalIn.seek(off);

                        int res = finalIn.read(b, 0, FINAL_SIZE);

                        System.out.println("第" + finalI + "个任务读出来的数据长度：" + res);

                        if (res > 0){
                            System.out.println("第" + finalI + "个任务out的文件写指针位置：" + finalOut.getFilePointer() + ",应该偏移的位置：" + off + "，FINAL_SIZE大小：" + FINAL_SIZE);
                            finalOut.seek(off);
                            finalOut.write(b, 0, res);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                        System.out.println("第" + finalI + "个任务完成");
                    }
                }});
            }

            countDownLatch.await();
            executorService.shutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

        }

        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }
}
