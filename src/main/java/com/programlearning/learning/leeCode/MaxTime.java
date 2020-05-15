package com.programlearning.learning.leeCode;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] sl = s.substring(1, s.length()-1).split(",");
            int[] il = new int[sl.length];
            for (int i = 0; i<sl.length; i++){
                il[i] = Integer.parseInt(sl[i]);
            }
            List list = Arrays.stream(il).boxed().collect(Collectors.toList());

            boolean invalidFlag = false;
            int maxValue = 0, temp = 0, maxHours = 0, maxMin = 0, maxSecond = 0;
            //先找小时数的最大值
            for (int i = 0; i<list.size() && !invalidFlag; i++){
                for (int j = 0; j<list.size() && !invalidFlag; j++){
                    if (i != j){
                        temp = il[i] * 10 + il[j];
                        if (temp > maxValue && temp < 24 && temp > 0){
                            maxValue = temp;

                        }else {
                            invalidFlag = true;
                            System.out.println("invalid");
                        }
                    }
                }
            }
            maxHours = maxValue;



        }
    }
}
