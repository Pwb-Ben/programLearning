package com.programlearning.learning.algorithm.sort;

public class SelectionSort {

    /**
     * 选择排序
     * 不是稳定排序算法
     */
    static void selectionSort(int[] a, int low, int high) {
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        int temp = 0, minValueIndex = 0;
        for (int i = low; i < high; i++) {
            minValueIndex = i;
            //选择出i到high中最小数
            for (int j = i + 1; j < high; j++) {
                if (a[j] < a[minValueIndex]) {
                    minValueIndex = j;
                }
            }
            //把最小数放在第i位
            if (minValueIndex != i) {
                temp = a[i];
                a[i] = a[minValueIndex];
                a[minValueIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
        SelectionSort.selectionSort(a, 0, a.length - 1);
        for(int i : a) {
            System.out.print(i+" ");
        }
    }
}
