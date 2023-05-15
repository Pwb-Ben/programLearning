package com.programlearning.learning.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 * <a href="https://www.hello-algo.com/chapter_sorting/bucket_sort/">...</a>
 *
 * 考虑一个长度为n的数组，元素是范围[0,1)的浮点数。桶排序的流程如下：
 * 初始化k个桶，将n个元素分配到k个桶中；
 * 对每个桶分别执行排序（本文采用编程语言的内置排序函数）；
 * 按照桶的从小到大的顺序，合并结果；
 *
 * 桶排序适用于处理体量很大的数据。例如，输入数据包含 100 万个元素，由于空间限制，系统内存无法一次性加载所有数据。
 * 此时，可以将数据分成 1000 个桶，然后分别对每个桶进行排序，最后将结果合并。
 */
public class BucketSort {

    private static void bucketSort(float[] nums) {
        // 初始化 k = n/2 个桶，预期向每个桶分配 2 个元素
        int k = nums.length / 2;
        List<List<Float>> buckets = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            buckets.add(new ArrayList<>());
        }
        // 1. 将数组元素分配到各个桶中
        for (float num : nums) {
            // 输入数据范围 [0, 1)，使用 num * k 映射到索引范围 [0, k-1]
            int i = (int) num * k;
            // 将 num 添加进桶 i
            buckets.get(i).add(num);
        }
        // 2. 对各个桶执行排序
        for (List<Float> bucket : buckets) {
            // 使用内置排序函数，也可以替换成其他排序算法
            Collections.sort(bucket);
        }
        // 3. 遍历桶合并结果
        int i = 0;
        for (List<Float> bucket : buckets) {
            for (float num : bucket) {
                nums[i++] = num;
            }
        }
    }

    public static void main(String[] args) {
        float[] a = {0.49f,0.96f,0.82f,0.09f,0.57f,0.43f,0.91f,0.75f,0.15f,0.37f};
        BucketSort.bucketSort(a);
        for(float i : a){
            System.out.print(i+" ");
        }
    }
}
