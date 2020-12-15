package com.programlearning.learning.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GCDandLCM {

    /**
     * 辗转相除法的递归调用求两个数的最大公约数
     * @param x 其中一个数
     * @param y 其中另一个数
     * @return 递归调用，最终返回最大公约数
     */
    public static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y , x % y);
    }
    /**
     * 求n个数的最大公约数
     * @param target<Integer>list n个数的集合
     * @param z 数据个数
     * @return 递归调用，最终返回最大公约数
     */
    public static int ngcd(List<Integer> target , int z) {
        if(z == 1) {
            return target.get(0);//真正返回的最大公约数
        }
        //递归调用，两个数两个数的求
        return gcd(target.get(z - 1) , ngcd(target , z - 1));
    }
    /**
     * 辗转相除法的递归调用求两个数的最小公倍数
     * @param x 其中一个数
     * @param y 其中另一个数
     * @return 递归调用，最终返回最小公倍数
     */
    public static int lcm (int x , int y) {
        return (x * y) / gcd(x , y);
    }
    /**
     * 求n个数的最小公倍数
     * @param target<Integer>list n个数的集合
     * @param z 数据个数
     * @return 递归调用，最终返回最小公倍数
     */
    public static int nlcm (List<Integer> target , int z) {
        if (z == 1) {
            return target.get(z - 1);//真正返回的最小公倍数
        }
        //递归调用，两个数两个数的求
        return lcm(target.get(z - 1) , nlcm(target , z-1));
    }
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;//数据个数
        List<Integer> target = new ArrayList<Integer>();//数据集合
        System.out.println("------------------------------------------------------------");
        System.out.println("请输入需要计算最大公约数和最小公倍数的个数:");
        try {
            num = scanner.nextInt();
            System.out.println("请输入需要计算最大公约数和最小公倍数的数组:");
            for (int i = 0; i < num; i++) {
                target.add(scanner.nextInt());
            }
        } catch (Exception e) {
            System.out.println("输入错误!!!");
            return ;
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("最大公约数:" + ngcd(target , num));
        System.out.println("最小公倍数:" + nlcm(target , num));
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }
}
