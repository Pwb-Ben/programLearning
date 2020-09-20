package com.programlearning.learning.ghs.controller;


import java.text.NumberFormat;

public class DownloadController {

    public static void main(String[] args) {
        double start = 100000;
        System.out.println(formatDouble(start*Math.pow(1.2, 20)));
        System.out.println(formatDouble(start*Math.pow(1.3, 20)));
    }

    private static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(20);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }
}
