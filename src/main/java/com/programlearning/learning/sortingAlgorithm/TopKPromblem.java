package com.programlearning.learning.sortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * JDK中有快速排序优化算法 DualPivotQuicksort 类
 */
public class TopKPromblem {

    /**
     *
     * @param input
     * @param k
     * @return ArrayList<Integer>
     *
     * 适合海量数据的最小堆 O（nlogk）
     * 该方法不改变原数组，但时间复杂度比O（n）略微复杂了些。
     * 构造一个最小堆，最小堆的性质就是堆顶是所有堆中数字的最小值，
     * 那么放入k个数字，随后将数字中k个数字之后的数字依次和堆中的最小数字比较（也就是和堆顶数字比较），
     * 如果大于他，就把堆顶数字弹出，放入小的数字，这样遍历一边数组后，
     * 得到一个k个数字的最小堆，这个最小堆里存的是最大的k个数。
     */
    static ArrayList<Integer> QueueSolution(int[] input, int k){
        // 由于本题需要返回ArrayList<Integer>，所以新建之
        ArrayList<Integer> res = new ArrayList<>();
        // 几种特殊情况
        if (k > input.length|| k == 0) {
            return res;
        }

        Integer p;
        // 构造优先队列，排序方法是自然数顺序，所以是最小堆
//        Queue<Integer> queue = new PriorityQueue<>(k);
        // 最大堆
        Queue<Integer> queue = new PriorityQueue<>(k, Collections.reverseOrder());

        for(int i:input) {
            // 最小堆内数字个数少于k，一直添加到k个
            if (queue.size() < k) {
                queue.add(i);
            }
            else {
                // 若堆内最小的数字小于数组中的数字，则将数字出堆，并放入这个大的数
                p = queue.peek();
                if (p != null && i < queue.peek()) {
                    queue.remove();
                    queue.add(i);
                }
            }
        }

        // 结束上面循环后，堆内就是最大的k个数
        while (!queue.isEmpty()) {
            res.add(queue.remove());
        }
        return res;
    }

    /**
     *
     * @param input
     * @param k
     * @return ArrayList<Integer>
     *
     * 基于快速排序的变种 O（n）
     * 该方法需要改变原数组。
     * 还记得上一题：数组中超过一半的数字么？这一题的思路和上题类似，仅仅是换成了k个前。
     * 这种算法是受快速排序算法的启发。
     * 在随机快速排序算法中，我们先数组中随机选择一个数字，然后调整数组中数字的顺序，
     * 使得比选中的数字小的数字都排在它的左边，比选中的数字大的数字都排在它的右边。
     * 如果这个选中的数字的下标刚好是k，我们就得到了k个小的数字，这些数字在k的左边，并且没有经过排序，但是都比k小。
     * 如果它的下标大于k，我们可以接着在它的左边部分的数组中查找。
     * 如果它的下标小于k，那么中位数应该位于它的右边，我们可以接着在它的右边部分的数组中查找。
     * 这是一个典型的递归过程
     */
    static ArrayList<Integer> QuickSortSolution(int[] input, int k){
        // 由于本题需要返回ArrayList<Integer>，所以新建之
        ArrayList<Integer> list = new ArrayList<>();
        // 若输入数组长度小于k。直接返回数空的ArrayList
        if(input.length < k){
            return list;
        }
        findKMin(input,0,input.length-1,k);
        for(int i = 0; i < k; i++){
            list.add(input[i]);
        }
        return list;
    }

    static void findKMin(int[] a, int start, int end, int k){
        if(start < end){
            int pos = partition(a, start, end);
            if(pos == k-1){
                return;
            }else if(pos < k-1){
                findKMin(a,pos+1,end,k);
            }else{
                findKMin(a,start,pos-1,k);
            }
        }
    }

    // 快排中的每次排序实现（挖坑填数法），返回的是交换后start位置（快排一次后的中轴点，中轴点左边全是小于它的，右边都是大于它的）
    static int partition(int[] a, int start, int end){
        int pivot = a[start];
        while(start < end){
            while(start < end && a[end] >= pivot){
                end--;
            }
            a[start]=a[end];
            while(start < end && a[start] <= pivot){
                start++;
            }
            a[end]=a[start];
        }
        a[start] = pivot;
        return start;
    }

    public static void main(String[] args) {
//        int[] a={15,1,2};
        int[] a={1,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
//        ArrayList<Integer> list = TopKPromblem.QueueSolution(a,5);
//        System.out.print("queue solution:");
//        for(Integer i:list){
//            System.out.print(i+" ");
//        }
//        System.out.println(" ");
        ArrayList<Integer> list = TopKPromblem.QuickSortSolution(a,5);
        System.out.print("quick sort solution:");
        for(Integer i:list){
            System.out.print(i+" ");
        }
        System.out.println(" ");

        for(Integer i:a){
            System.out.print(i+" ");
        }
        System.out.println(" ");
    }
}
