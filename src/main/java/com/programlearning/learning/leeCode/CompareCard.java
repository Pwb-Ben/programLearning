package com.programlearning.learning.leeCode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目描述
 * 扑克牌游戏大家应该都比较熟悉了，一副牌由54张组成，含3~A、2各4张，小王1张，大王1张。牌面从小到大用如下字符和字符串表示（其中，小写joker表示小王，大写JOKER表示大王）：
 * 3 4 5 6 7 8 9 10 J Q K A 2 joker JOKER
 * 输入两手牌，两手牌之间用"-"连接，每手牌的每张牌以空格分隔，"-"两边没有空格，如：4 4 4 4-joker JOKER。
 * 请比较两手牌大小，输出较大的牌，如果不存在比较关系则输出ERROR。
 * 基本规则：
 * （1）输入每手牌可能是个子、对子、顺子（连续5张）、三个、炸弹（四个）和对王中的一种，不存在其他情况，由输入保证两手牌都是合法的，顺子已经从小到大排列；
 * （2）除了炸弹和对王可以和所有牌比较之外，其他类型的牌只能跟相同类型的存在比较关系（如，对子跟对子比较，三个跟三个比较），不考虑拆牌情况（如：将对子拆分成个子）；
 * （3）大小规则跟大家平时了解的常见规则相同，个子、对子、三个比较牌面大小；顺子比较最小牌大小；炸弹大于前面所有的牌，炸弹之间比较牌面大小；对王是最大的牌；
 * （4）输入的两手牌不会出现相等的情况。
 *
 * 输入描述:
 * 输入两手牌，两手牌之间用"-"连接，每手牌的每张牌以空格分隔，"-"两边没有空格，如 4 4 4 4-joker JOKER。
 *
 * 输出描述:
 * 输出两手牌中较大的那手，不含连接符，扑克牌顺序不变，仍以空格隔开；如果不存在比较关系则输出ERROR。
 */
public class CompareCard {

    static String[] strings = {"3","4","5","6","7","8","9","10","J","Q","K","A","2"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s = sc.nextLine();
            String[] strings1 = s.split("-");
            String[] card1, card2;
            card1 = strings1[0].split("\\s+");
            card2 = strings1[1].split("\\s+");
            //牌面类型相同
            if (card1.length == card2.length) {
                card1 = findIndex(card1[0]) > findIndex(card2[0])?card1:card2;
                if (card1.length == 1){
                    System.out.println(card1[0]);
                }
                else{
                    for (int i = 0; i < card1.length - 1; i++) {
                        System.out.print(card1[i]+" ");
                    }
                    System.out.print(card1[card1.length - 1]);
                }
            }
            //牌面类型不同，只有炸弹能和其他类型比较
            else {
                //王炸最大
                if("joker JOKER".equals(strings1[0]) || "JOKER joker".equals(strings1[1]) || "joker JOKER".equals(strings1[1]) || "JOKER joker".equals(strings1[0])){
                    System.out.println("joker JOKER");
                }
                //有一手牌是炸弹
                else if (card1.length == 4 || card2.length == 4){
                    card1 = card1.length == 4?card1:card2;
                    for (int i = 0; i < card1.length - 1; i++) {
                        System.out.print(card1[i]+" ");
                    }
                    System.out.print(card1[card1.length - 1]);
                }
                //其他情况是无效比较
                else {
                    System.out.println("ERROR");
                }
            }
        }
    }

    static int findIndex(String s){
        for(int i = 0; i < strings.length; i++){
            if (strings[i].equals(s)){
                return i;
            }
        }
        return -1;
    }
}