package com.programlearning.learning.leeCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * 题目描述
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 输入: "()"
 * 输出: true
 *
 * 输入: "()[]{}"
 * 输出: true
 *
 * 输入: "([)]"
 * 输出: false
 */
public class ValidBrackets {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            System.out.println(isValid(s));
        }
    }

    private static boolean isValid(String s) {
        // 括号的长度
        int slen = s.length();
        // 括号不是成对出现直接返回 false
        if (slen % 2 == 1) {
            return false;
        }
        // 把所有对比的括号存入 map，对比时用
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        // 定义栈，用于存取括号（辅助比较）
        Stack<Character> stack = new Stack<>();
        // 循环所有字符
        for (int i = 0; i < slen; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                // 为右边的括号，如 ')'、'}' 等
                if (stack.isEmpty()
                        // 是一对括号，执行出栈（消除左右括号）
                        || !map.get(c).equals(stack.pop())) {
                    // 栈为空或括号不匹配
                    return false;
                }
            } else {
                // 左边括号，直接入栈
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
