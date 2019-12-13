package com.programlearning.learning.base;

public class Base {

    /**
     * 测试java继承顺序,和构造函数执行顺序
     *
     * 从上到下执行父类静态变量;然后遍历所有的非静态变量进行初始化
     * 父类的静态代码块
     * 子类的静态代码块
     * 然后执行主程序
     * 如果在父类或者子类有调用构造函数的new对象先执行
     */
    static class People {
        private String name;
        private static People people = new People();
        private static int n = 10;
        private int age = setAge();
        protected int sex;

        {
            System.out.println("初始化块");
            System.out.println("age = " + age);
        }

        static {
            System.out.println("静态块");
            System.out.println("n = " + n);
        }

        public static int setAge() {
            return n++;
        }

        public People() {
            System.out.println("默认构造函数");
            System.out.println("age:" + age);
        }

        public People(String name, int age) {
            this.name = name;
            this.age = age;
            System.out.println("有参构造函数");
            System.out.println("age:" + age);
        }

        {
            System.out.println("初始化块1");
            System.out.println("age = " + age);
        }
    }

    static class Student extends People {
        private static int num=20;
        private String school;

        {
            System.out.println("student 初始化块");
            school="河北小学";
            System.out.println(school);
        }

        static {
            System.out.println("student 静态块");
        }

        public Student() {
        }

        public Student(String name, int age) {
            super(name, age);
            System.out.println("student 构造函數");
        }
    }

    public static void main(String[] args) {
//        Student p = new Student();
        Student p1 = new Student("zhangsan",40);

        System.out.println(Integer.numberOfLeadingZeros(123));
        String s = "123";
        char[] ch = new char[10];
        s.getChars(0, s.length(), ch, 0);
        for(int i = 0; i<s.length(); i++){
            System.out.println(ch[i]);
        }
    }
}
