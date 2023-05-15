package com.programlearning.learning.algorithm.sort;

/**
 * 快速排序
 *
 * 快速排序的一次划分算法从两头交替搜索，直到low和high重合，因此其时间复杂度是O(n)；而整个快速排序算法的时间复杂度与划分的趟数有关。
 *
 * 理想的情况是，每次划分所选择的中间数恰好将当前序列几乎等分，经过log2n趟划分，
 * 便可得到长度为1的子表。这样，整个算法的时间复杂度为O(nlogn)。
 *
 * 最坏的情况是，每次所选的中间数是当前序列中的最大或最小元素，这使得每次划分所得的子表中一个为空表，
 * 另一子表的长度为原表的长度-1。这样，长度为n的数据表的快速排序需要经过n趟划分，使得整个排序算法的时间复杂度为O(n^2)。
 *
 * 为改善最坏情况下的时间性能，可采用其他方法选取中间数。通常采用“三者值取中”方法，
 * 即比较H->r[low].key、H->r[high].key与H->r[(10w+high)/2].key，取三者中关键字为中值的元素为中间数。
 *
 * 可以证明，快速排序的平均时间复杂度也是O(nlogn)。因此，该排序方法被认为是目前最好的一种内部排序方法。
 *
 * 从空间性能上看，尽管快速排序只需要一个元素的辅助空间，但快速排序需要一个栈空间来实现递归。
 * 最好的情况下，即快速排序的每一趟排序都将元素序列均匀地分割成长度相近的两个子表，所需栈的最大深度为log(n+1)；
 * 但最坏的情况下，栈的最大深度为n。这样，快速排序的空间复杂度为O(n))。
 *
 * 普通快速排序因为以第一个元素当作枢纽元，可能在排序中放到数组后面，就已经破坏其稳定性
 */
public class QuickSort {

    /**
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
     */
    private static void quickSort(int[] a, int low, int high){
        if (low >= high) {
            return;
        }
        int key = partSort(a, low, high);
        quickSort(a, low, key-1);
        quickSort(a, key+1, high);
    }

    private static int partSort(int[] a, int left, int right) {
        //取最左边的元素做支点
        int key = left;
        //当左右没有相遇
        while (left < right){
            //如果右比key小就退出循环
            while (left < right && a[right] >= a[key]) {
                right--;
            }
            //如果左比key大就退出循环
            while (left < right && a[left] <= a[key]) {
                left++;
            }
            //交换左右
            swap(a, left, right);
        }
        //交换key和相遇位置的元素
        swap(a, key, left);
        //返回key的位置
        return left;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,400,20,89,1};
        QuickSort.quickSort(a, 0, a.length - 1);
        for(int i : a){
            System.out.print(i+" ");
        }
    }
}
