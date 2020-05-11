package com.programlearning.learning.leeCode;

import java.util.Scanner;

/**
 * 题目描述
 * 分子为1的分数称为埃及分数。现输入一个真分数(分子比分母小的分数，叫做真分数)，请将该分数分解为埃及分数。如：8/11 = 1/2+1/5+1/55+1/110。
 *
 * 贪心算法】
 * 设a、b为互质正整数，a<b 分数a/b 可用以下的步骤分解成若干个单位分数之和：
 * 步骤一： 用b除以a，得商数q1及余数r1,即b=a*q1+r1
 * 步骤二： a/b=1/(q1+1）+(a-r)/b(q1+1）
 * 步骤三： 重复步骤2，直到分解完毕
 * 3/7=1/3+2/21=1/3+1/11+1/231
 * 13/23=1/2+3/46=1/2+1/16+1/368
 *
 * 以上其实是数学家斐波那契提出的一种求解埃及分数的贪心算法，准确的算法表述应该是这样的：
 * 设某个真分数的分子为a，分母为b;
 * 把c=(b/a+1)作为分解式中第一个埃及分数的分母；
 * 将a-b%a作为新的a；
 * 将b*c作为新的b；
 * 如果a等于1，则最后一个埃及分数为1/b,算法结束；
 * 如果a大于1但是a能整除b，则最后一个埃及分数为1/(b/a),算法结束；
 * 否则重复上面的步骤。
 *
 */
public class CaleEgpyt {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] ss = s.split("/");
            int a = Integer.parseInt(ss[0]), b = Integer.parseInt(ss[1]);
            while (a != 1){
                if (b % (a -1) == 0){
                    System.out.println(1 + "/" + b/(a -1) + "+");
                }else {
                    int c = b/a + 1;
                    a = a - b%a;
                    b = b*c;
                    System.out.println(1 + "/" + c + "+");
                    if (b%a == 0){
                        b = b/a;
                        a = 1;
                    }
                }
            }
            System.out.println(1 + "/" + b);
        }
    }
}
