package com.programlearning.learning.algorithm.sort;

public class BubbleSort {

    /**
     * 冒泡排序
     * 稳定排序算法
     */
    static void bubbleSort(int[] a,int low,int high) {
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        for(int i=low, count=0; i < high; i++, count++) {
            int temp = 0;
            for(int j = low; j < high - count; j++) {
                if(a[j] > a[j+1]) {
                    temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
        BubbleSort.bubbleSort(a, 0, a.length-1);
        for(int i : a) {
            System.out.print(i+" ");
        }
    }
}
