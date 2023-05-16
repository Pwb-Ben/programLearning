package com.programlearning.learning.algorithm.sort;

/**
 * 计数排序
 * 通过统计元素数量来实现排序，通常应用于整数数组，适用于数据量n较大但数据范围m较小的情况
 * <a href="https://www.hello-algo.com/chapter_sorting/counting_sort/">...</a>
 */
public class CountingSort {

    public static void countingSort(int[] nums) {
        // 1. 统计数组最大元素 m
        int m = 0;
        for (int num : nums) {
            m = Math.max(m, num);
        }
        // 2. 统计各数字的出现次数
        // counter[num] 代表 num 的出现次数
        int[] counter = new int[m + 1];
        for (int num : nums) {
            counter[num]++;
        }
        // 3. 求 counter 的前缀和，将“出现次数”转换为“尾索引”
        // 即 counter[num]-1 是 num 在 res 中最后一次出现的索引
        for (int i = 0; i < m; i++) {
            counter[i + 1] += counter[i];
        }
        // 4. 倒序遍历 nums ，将各元素填入结果数组 res
        // 初始化数组 res 用于记录结果
        int n = nums.length;
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];
            res[counter[num] - 1] = num; // 将 num 放置到对应索引处
            counter[num]--; // 令前缀和自减 1 ，得到下次放置 num 的索引
        }
        // 使用结果数组 res 覆盖原数组 nums
        System.arraycopy(res, 0, nums, 0, n);
    }


    public static void main(String[] args) {
        int[] a = {1,0,1,2,0,4,0,2,2,4};
        CountingSort.countingSort(a);
        for(int i : a){
            System.out.print(i+" ");
        }
    }
}
