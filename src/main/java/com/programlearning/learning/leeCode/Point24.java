package com.programlearning.learning.leeCode;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 题目描述
 * 计算24点是一种扑克牌益智游戏，随机抽出4张扑克牌，通过加(+)，减(-)，乘(*), 除(/)四种运算法则计算得到整数24，本问题中，扑克牌通过如下字符或者字符串表示，其中，小写joker表示小王，大写JOKER表示大王：
 *
 *                    3 4 5 6 7 8 9 10 J Q K A 2 joker JOKER
 *
 * 本程序要求实现：输入4张牌，输出一个算式，算式的结果为24点。
 *
 * 详细说明：
 *
 * 1.运算只考虑加减乘除运算，没有阶乘等特殊运算符号，友情提醒，整数除法要当心；
 * 2.牌面2~10对应的权值为2~10, J、Q、K、A权值分别为为11、12、13、1；
 * 3.输入4张牌为字符串形式，以一个空格隔开，首尾无空格；如果输入的4张牌中包含大小王，则输出字符串“ERROR”，表示无法运算；
 * 4.输出的算式格式为4张牌通过加(+)，减(-)，乘(*), 除(/)四个运算符相连，中间无空格，4张牌出现顺序任意，只要结果正确；
 * 5.输出算式的运算顺序从左至右，不包含括号，如1+2+3*4的结果为24
 * 6.如果存在多种算式都能计算得出24，只需输出一种即可，如果无法得出24，则输出“NONE”表示无解。
 */
public class Point24 {

    static String[] opList = {"+","-","*","/"};
    static String[] cardList = {"0","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    static ArrayList<double[]> list = new ArrayList<double[]>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            String[] strings = str.split("\\s+");
            list.clear();
            double[] doubles = new double[4];
            boolean flag = true;
            for (int i = 0; i < 4; i++) {
                if ("joker".equals(strings[i]) || "JOKER".equals(strings[i])) {
                    flag = false;
                    break;
                }else if ("J".equals(strings[i])){
                    doubles[i] = 11;
                }else if ("Q".equals(strings[i])){
                    doubles[i] = 12;
                }else if ("K".equals(strings[i])){
                    doubles[i] = 13;
                }else if ("A".equals(strings[i])){
                    doubles[i] = 1;
                }else {
                    doubles[i] = Double.valueOf(strings[i]);
                }
            }
            if (!flag){
                System.out.println("ERROR");
            }else if (!func(doubles)){
                System.out.println("NONE");
            }
            System.out.println();
        }
    }

    /**
     * 运算函数
     * @param a
     * @param b
     * @param op
     * @return
     */
    static double op(double a, double b, int op){
        if (op == 0){
            return a+b;
        }else if (op == 1){
            return a-b;
        }else if (op == 2){
            return a*b;
        }else if (op == 3){
            return a/b;
        }
        return 0;
    }

    /**
     * 计算24点
     * @param input     输入4个数
     * @param op        运算符3个
     * @return
     */
    static boolean cal24(double input[], int op[]){
        double[] doubles = new double[4];
        System.arraycopy(input, 0, doubles, 0, 4);
        for (int i = 0; i < input.length - 1; i++) {
            doubles[i+1] = op(doubles[i], doubles[i+1], op[i]);
        }
        return Math.abs(doubles[3] - 24) < 0.01;
    }

    /**
     * 遍历函数
     * @param input
     * @return
     */
    static boolean func(double input[]){
        boolean flag = false;
        int[] op = new int[4];
        solution(input, 0);
        for (double[] d: list) {
            for(int i = 0; i < 4 && !flag; i++) {
                op[0] = i;
                for(int j = 0; j < 4 && !flag; j++) {
                    op[1] = j;
                    for(int k = 0; k < 4 && !flag; k++) {
                        op[2] = k;
                        if (cal24(d, op)) {
                            for(int m = 0; m < 3; m++){
                                int cn = (int)d[m];
                                System.out.print(cardList[cn] + opList[op[m]]);
                            }
                            System.out.print(cardList[(int)d[3]]);
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 递归调用函数交换数组中的数
     */
    static void solution(double[] arr, int k) {
        int n = arr.length;
        if (k == n) {
            double[] doubles = new double[4];
            System.arraycopy(arr, 0, doubles, 0, 4);
            list.add(doubles);
        }

        for (int i = k; i < n; i++) {
            swap(arr, k, i);
            solution(arr, k + 1);
            swap(arr, k, i);
        }
    }

    /**
     * 交换数组中两个数
     */
    static void swap(double[] arr, int x, int y) {
        if (x != y) {
            double temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }
}
