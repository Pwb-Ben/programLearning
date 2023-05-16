package com.programlearning.learning.algorithm.search;

/**
 * 二分查找
 * 适用于大数据量的情况，效率表现稳定，最差时间复杂度为O(logn)
 * 数据量不能过大，因为存储数组需要连续的内存空间
 * 不适用于高频增删数据的场景，因为维护有序数组的开销较大
 */
public class BinarySearch {

    /**
     * 二分查找（双闭区间）
     */
    private static int binarySearch(int[] nums, int target) {
        // 初始化双闭区间 [0, n-1] ，即 i, j 分别指向数组首元素、尾元素
        int i = 0, j = nums.length - 1;
        // 循环，当搜索区间为空时跳出（当 i > j 时为空）
        while (i <= j) {
            // 计算中点索引 m
            int m = (i + j) / 2;
            if (nums[m] < target) {
                // 此情况说明 target 在区间 [m+1, j] 中
                i = m + 1;
            } else if (nums[m] > target) {
                // 此情况说明 target 在区间 [i, m-1] 中
                j = m - 1;
            } else {
                // 找到目标元素，返回其索引
                return m;
            }
        }
        // 未找到目标元素，返回 -1
        return -1;
    }

    /**
     * 二分查找（左闭右开）
     */
    private static int binarySearch1(int[] nums, int target) {
        // 初始化左闭右开 [0, n) ，即 i, j 分别指向数组首元素、尾元素+1
        int i = 0, j = nums.length;
        // 循环，当搜索区间为空时跳出（当 i = j 时为空）
        while (i < j) {
            // 计算中点索引 m
            int m = (i + j) / 2;
            if (nums[m] < target) {
                // 此情况说明 target 在区间 [m+1, j) 中
                i = m + 1;
            } else if (nums[m] > target) {
                // 此情况说明 target 在区间 [i, m) 中
                j = m;
            } else {
                // 找到目标元素，返回其索引
                return m;
            }
        }
        // 未找到目标元素，返回 -1
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(BinarySearch.binarySearch(a, 7));
    }
}
