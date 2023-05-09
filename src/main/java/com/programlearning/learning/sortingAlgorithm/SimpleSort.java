package com.programlearning.learning.sortingAlgorithm;

public class SimpleSort {

    /**
     * 冒泡排序
     * 稳定排序算法
     */
    static void bubbleSort(int[] a,int low,int high) {
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        for(int i=low,count=0; i<high; i++,count++) {
            int temp = 0;
            for(int j=low; j<high-count; j++) {
                if(a[j]>a[j+1]){
                    temp = a[j];
                    a[j]= a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     * 稳定排序算法
     * @param a
     * @param low
     * @param high
     */
    static void insertionSort(int[] a, int low, int high){
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        for (int i = low; i <= high; i++) {
            //选择第i+1个数插入到前i个数中
            int j, ai = a[i];
            //如果比第i+1个数大则后退一位
            for(j = i; j > low && a[j-1] > ai; j--) {
                a[j] = a[j-1];
            }
            a[j] = ai;
        }
    }

    static void insertionSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int j, ai = a[i];
            for (j = i; j > 0 && a[j-1] > ai; j--) {
                a[j] = a[j-1];
            }
            a[j] = ai;
        }
    }

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
        SimpleSort.bubbleSort(a, 0, a.length-1);
        for(int i : a) {
            System.out.print(i+" ");
        }
        System.out.println();
        int[] b = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
        SimpleSort.insertionSort(b);
        for(int i : b) {
            System.out.print(i+" ");
        }
    }
}
