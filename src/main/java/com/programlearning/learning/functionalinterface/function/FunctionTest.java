package com.programlearning.learning.functionalinterface.function;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Function 接口是一个[功能型]接口，它的一个作用就是转换作用，将输入数据转换成另一种形式的输出数据
 * Function 接口总结:
 * ① Function 接口是一个功能型接口，是一个转换数据的作用。
 * ② Function 接口实现 apply 方法来做转换。
 */
public class FunctionTest {

    public void test(){
        //① 使用map方法，泛型的第一个参数是转换前的类型，第二个是转化后的类型
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                //获取每个字符串的长度，并且返回
                return s.length();
            }
        };

        Stream<String> stream = Stream.of("aaa", "bbbbb", "ccccccv");
        Stream<Integer> stream1 = stream.map(function);
        stream1.forEach(System.out::println);

        System.out.println("********************");
    }
}
