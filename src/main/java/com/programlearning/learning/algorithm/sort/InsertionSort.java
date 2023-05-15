package com.programlearning.learning.algorithm.sort;

public class InsertionSort {

    /**
     * 插入排序
     * 稳定排序算法
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

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
        InsertionSort.insertionSort(a, 0, a.length - 1);
        for(int i : a) {
            System.out.print(i+" ");
        }
    }
}
