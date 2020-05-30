package com.programlearning.learning.IO.fileNIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileNIO {

    private static final int FINAL_SIZE = 100 * 1024 * 1024;

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

        // https://my.oschina.net/feichexia/blog/212318?nocache=1534897697905
        // https://blog.csdn.net/bird_tp/article/details/102504833
        // 复制文件
        long s1 = System.currentTimeMillis();

        FileChannel in = new FileInputStream("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786.mp4").getChannel();
        FileChannel out = new FileOutputStream("D:\\迅雷\\迅雷下载\\HD-SSNI-786\\HD-SSNI-786_1.mp4").getChannel();
        long inSize = in.size();
        long start = 0;
        while (start != inSize){
            long len = in.transferTo(start, inSize-start, out);
            start += len;
        }

        long s2 = System.currentTimeMillis();
        System.out.println(s2-s1);
    }
}
