package com.programlearning.learning.algorithm.sort;

/**
 * 希尔排序
 *
 * 希尔排序的复杂度和增量序列是相关的
 * {1,2,4,8,...}这种序列并不是很好的增量序列，使用这个增量序列的时间复杂度（最坏情形）是O(n^2)
 * Hibbard提出了另一个增量序列{1,3,7，...,2^k-1}，这种序列的时间复杂度(最坏情形)为O(n^1.5)
 * Sedgewick提出了几种增量序列，其最坏情形运行时间为O（n^1.3）,其中最好的一个序列是{1,5,19,41,109,...}
 *
 * 希尔排序并不稳定，虽然插入排序是稳定的，但是希尔排序在插入的时候是跳跃性插入的，有可能破坏稳定性
 */
public class ShellSort {

    /**
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
     */
    public static void shellSort(int[] a,int low,int high){
        // 进行分组，最开始的增量是组数长度的一半
        for(int gap = (high - low + 1) >> 1; gap > 0; gap = gap >> 1){
            //对各分组进行插入排序
            for(int i = low + gap - 1; i <= high; i++){
                int j ,inserted = a[i];
                // 插入的时候按组进行插入（组内元素两两相隔gap）
                for(j = i - gap; j >= low && inserted < a[j]; j-=gap){
                    a[j+gap] = a[j];
                }
                a[j+gap] = inserted;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,400,20,89,1};
        ShellSort.shellSort(a, 0, a.length - 1);
        for(int i : a){
            System.out.print(i+" ");
        }
    }
}
