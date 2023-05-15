package com.programlearning.learning.algorithm.sort;

/**
 * 归并排序
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(N)，归并排序需要一个与原数组相同长度的数组做辅助来排序
 * 稳定性：归并排序是稳定的排序算法，temp[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
 * 这行代码可以保证当左右两部分的值相等的时候，先复制左边的值，这样可以保证值相等的时候两个元素的相对位置不变。
 */
public class MergeSort {

    /**
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
     */
    private static void mergeSort(int[] a, int low, int high){
        if(a == null || low >= high) {
            return;
        }
        int mid = low + ((high - low) >> 1);
        mergeSort(a, low, mid);
        mergeSort(a, mid + 1, high);
        merge(a, low, mid, high);
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = 0;
        int p1 = low;
        int p2 = mid + 1;
        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while(p1 <= mid && p2 <= high) {
            temp[i++] = a[p1] <= a[p2] ? a[p1++] : a[p2++];
        }
        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while(p1 <= mid) {
            temp[i++] = a[p1++];
        }
        while(p2 <= high) {
            temp[i++] = a[p2++];
        }
        // 把最终的排序的结果复制给原数组
        for(i = 0; i < temp.length; i++) {
            a[low + i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,400,20,89,1};
        MergeSort.mergeSort(a, 0, a.length - 1);
        for(int i : a){
            System.out.print(i+" ");
        }
    }
}
