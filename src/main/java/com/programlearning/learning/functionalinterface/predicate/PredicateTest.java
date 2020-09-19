package com.programlearning.learning.functionalinterface.predicate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Predicate 接口是一个[谓词型]接口，其实，这个就是一个类似于 bool 类型的判断的接口
 * Predicate 接口总结：
 * ① Predicate 是一个谓词型接口，其实只是起到一个判断作用。
 * ② Predicate 通过实现一个 test 方法做判断。
 */
public class PredicateTest {

    public void test(){
        //① 使用Predicate接口实现方法,只有一个test方法，传入一个参数，返回一个bool值
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 5;
            }
        };

        System.out.println(predicate.test(6));

        System.out.println("********************");

        //② 使用lambda表达式，
        predicate = (t) -> t > 5;
        System.out.println(predicate.test(1));

        System.out.println("********************");

        //③ 将Predicate作为filter接口，Predicate起到一个判断的作用
        Predicate<Integer> predicate1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 5;
            }
        };

        Stream<Integer> stream = Stream.of(1, 23, 3, 4, 5, 56, 6, 6);
        List<Integer> list = stream.filter(predicate1).collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("********************");
    }
}
