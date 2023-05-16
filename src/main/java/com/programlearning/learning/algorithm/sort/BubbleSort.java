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

    /**
     * 冒泡排序（标志优化）
     */
    void bubbleSortWithFlag(int[] nums) {
        // 外循环：待排序元素数量为 n-1, n-2, ..., 1
        for (int i = nums.length - 1; i > 0; i--) {
            // 初始化标志位
            boolean flag = false;
            // 内循环：冒泡操作
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    // 交换 nums[j] 与 nums[j + 1]
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                    // 记录交换元素
                    flag = true;
                }
            }
            if (!flag) {
                // 此轮冒泡未交换任何元素，直接跳出
                break;
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
