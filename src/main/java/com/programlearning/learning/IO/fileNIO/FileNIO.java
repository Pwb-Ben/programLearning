package com.programlearning.learning.IO.fileNIO;

import com.programlearning.learning.threadPool.ExecutorsExample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class FileNIO {

    public static void main(String[] args) throws Exception{
        // Write bytes to a file:
        Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt");
        if (path == null) {
            System.out.println("找不到文件");
        }else{
            Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt"), "你好吗".getBytes());
        }

        System.out.println(new String(Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt"))));

        // Write an iterable to a file:
//        List<String> lines = Files.readAllLines(Paths.get("../streams/Cheese.dat"));
//        Files.write(Paths.get("Cheese.txt"), lines);
//        System.out.println("Cheese.txt: " + Files.size(Paths.get("Cheese.txt")));

        Stream<String> stringStream = Files.lines(Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt"));
        stringStream.forEach(System.out::println);

        //https://my.oschina.net/feichexia/blog/212318?nocache=1534897697905
        long s1 = System.currentTimeMillis();
        ExecutorsExample executorsExample = new ExecutorsExample();
        ExecutorService executorService = executorsExample.getExecutorService();
        //文件复制
        FileChannel in = new FileInputStream("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786.mp4").getChannel();
        FileChannel out = new FileOutputStream("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786_1.mp4").getChannel();
        int coreNum = Runtime.getRuntime().availableProcessors() << 1;
        long in_size = in.size();
        long in_size_part = in_size / coreNum;
        CountDownLatch countDownLatch = new CountDownLatch(coreNum);
        for (int i = 0; i < coreNum; i++){
            long ii = i*in_size_part;
            int finalI = i;
            executorService.submit(() -> {
                try {
                    if (finalI ==coreNum-1){
                        in.transferTo(ii, in.size()-ii, out);
                    }else {
                        in.transferTo(ii, in_size_part, out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        long s2 = System.currentTimeMillis();
        System.out.println(s1);
        System.out.println(s2);
    }
}
