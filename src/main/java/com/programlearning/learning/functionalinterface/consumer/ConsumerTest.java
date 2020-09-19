package com.programlearning.learning.functionalinterface.consumer;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * consumer接口就是一个[消费型] 的接口，通过传入参数，然后输出值
 * Consumer 总结:
 * ① Consumer是一个接口，并且只要实现一个 accept 方法，就可以作为一个“消费者”输出信息。
 * ② 其实，lambda 表达式、方法引用的返回值都是 Consumer 类型，所以，他们能够作为 forEach 方法的参数，并且输出一个值。
 */
public class ConsumerTest {

    public void test(){
        //① 使用consumer接口实现方法
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Stream<String> stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        stream.forEach(consumer);

        System.out.println("********************");

        //② 使用lambda表达式，forEach方法需要的就是一个Consumer接口
        stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        //lambda表达式返回的就是一个Consumer接口
        Consumer<String> consumer1 = (s) -> System.out.println(s);
        stream.forEach(consumer1);
        //更直接的方式
        //stream.forEach((s) -> System.out.println(s));
        System.out.println("********************");

        //③ 使用方法引用，方法引用也是一个consumer
        stream = Stream.of("aaa", "bbb", "ddd", "ccc", "fff");
        Consumer consumer2 = System.out::println;
        stream.forEach(consumer);
        //更直接的方式
        //stream.forEach(System.out::println);
    }
}
