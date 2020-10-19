package com.programlearning.learning.generics;

public class Generics {

    public static void main(String[] args) {
        String s = "hello world";
        print(s);
        Integer i = 5;
        print(i);
        String[] slist = new String[1];
        System.out.println(slist.getClass());
        System.out.println(s.getClass());
    }

    public static <T> void print(T t){
        System.out.println(t);
    }
}
