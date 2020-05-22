package com.programlearning.learning.leeCode;

import java.util.Scanner;

/**
 * 题目描述
 * 连续输入字符串，请按长度为8拆分每个字符串后输出到新的字符串数组；
 * 长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
 * 输入描述:
 * 连续输入字符串(输入2次,每个字符串长度小于100)
 *
 * 输出描述:
 * 输出到长度为8的新字符串数组
 *
 * 输入
 * abc
 * 123456789
 *
 * 输出
 * abc00000
 * 12345678
 * 90000000
 *
 */
public class Split8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            String str1 = scanner.nextLine();
            String str2 = scanner.nextLine();

            stringBuilder1.append(str1);
            int size = stringBuilder1.length();
            int addZero = 8 - size % 8;
            if(addZero > 0 && addZero < 8){
                while(addZero > 0){
                    stringBuilder1.append('0');
                    addZero--;
                }
            }
            str1 = stringBuilder1.toString();
            while(str1.length()>0){
                System.out.println(str1.substring(0, 8));
                str1 = str1.substring(8);
            }


            stringBuilder2.append(str2);
            size = stringBuilder2.length();
            addZero = 8 - size % 8;
            if(addZero > 0 && addZero < 8){
                while(addZero > 0){
                    stringBuilder2.append('0');
                    addZero--;
                }
            }
            str2 = stringBuilder2.toString();
            while(str2.length()>0){
                System.out.println(str2.substring(0, 8));
                str2 = str2.substring(8);
            }
        }
    }
}
