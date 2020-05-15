package com.programlearning.learning.leeCode;

import java.util.Scanner;

/**
 * 题目描述：给定一个数组，里面有6个整数，求这个数组能够表示的最大24进制的时间是多少，
 *           输出这个时间，无法表示输出invalid
 * 输入描述：输入为一个整数数组，数组内有6个整数
 *           输入整数数组长度为6，不需要考虑其它长度，元素值为0或者正整数，
 *           6个数字每个数字只能使用一次。
 * 输出描述：输出为一个24进账格式的时间，或者字符串“invalid”
 *
 * 示例输入：[0,2,3,0,5,6]
 * 示例输出: 23:56:00
 */
public class MaxTime {

    private static int[] maxList = new int[6];
    private static int maxTime = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] sl = s.substring(1, s.length()-1).split(",");
            int[] il = new int[sl.length];
            for (int i = 0; i<sl.length; i++){
                il[i] = Integer.parseInt(sl[i]);
            }
            solution(il, 0);
            if (maxTime == 0){
                System.out.println("invalid");
            }else {
                int hour, min, second;
                hour = maxList[0]*10 + maxList[1];
                min = maxList[2]*10 + maxList[3];
                second = maxList[4]*10 + maxList[5];
                System.out.printf("%02d:%02d:%02d",hour,min,second);
                System.out.println();
            }
            maxTime = 0;
        }
    }

    /**
     * 递归调用函数交换数组中的数,得到全排列
     */
    private static void solution(int[] arr, int k) {
        int n = arr.length;
        if (k == n) {
            int hour, min, second;
            hour = arr[0]*10 + arr[1];
            min = arr[2]*10 + arr[3];
            second = arr[4]*10 + arr[5];

            if (hour >= 24 || min >= 60 || second >= 60){

            }else {
                int totalSecond = hour * 3600 + min * 60  + second;
                if (totalSecond > maxTime){
                    maxTime = totalSecond;
                    System.arraycopy(arr, 0, maxList, 0, 6);
                }
            }
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
    private static void swap(int[] arr, int x, int y) {
        if (x != y) {
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }
}
