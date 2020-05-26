package com.programlearning.learning.IO.fileNIO;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
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
    }
}
