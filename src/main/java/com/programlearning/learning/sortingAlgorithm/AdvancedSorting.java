package com.programlearning.learning.sortingAlgorithm;

/**
 * @author Administrator
 */
public class AdvancedSorting {

    /**
     * 快速排序
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
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
     * 但最坏的情况下，栈的最大深度为n。这样，快速排序的空间复杂度为O(logn))。
     *
     * 普通快速排序因为以第一个元素当作枢纽元，可能在排序中放到数组后面，就已经破坏其稳定性
     */
    private static void quickSort(int[] a, int low, int high){
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        int start=low,end=high,pivot;
        if(start < end) {
            //设置枢轴
            pivot=a[start];
            while(start < end) {
                while(start<end && a[end]>=pivot) {
                    --end;
                }
                a[start]=a[end];
                while(start<end && a[start]<pivot) {
                    ++start;
                }
                a[end]=a[start];
            }
            a[start]=pivot;
            quickSort(a,low,start-1);
            quickSort(a,start+1,high);
        }
    }

    /**
     * 希尔排序
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
     *
     * 希尔排序的复杂度和增量序列是相关的
     * {1,2,4,8,...}这种序列并不是很好的增量序列，使用这个增量序列的时间复杂度（最坏情形）是O(n^2)
     * Hibbard提出了另一个增量序列{1,3,7，...,2^k-1}，这种序列的时间复杂度(最坏情形)为O(n^1.5)
     * Sedgewick提出了几种增量序列，其最坏情形运行时间为O（n^1.3）,其中最好的一个序列是{1,5,19,41,109,...}
     *
     * 希尔排序并不稳定，虽然插入排序是稳定的，但是希尔排序在插入的时候是跳跃性插入的，有可能破坏稳定性
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

    /**
     * 归并排序
     * @param a        数组
     * @param low      第一个元素下标
     * @param high     最后一个元素下标
     *
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(N)，归并排序需要一个与原数组相同长度的数组做辅助来排序
     * 稳定性：归并排序是稳定的排序算法，temp[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
     * 这行代码可以保证当左右两部分的值相等的时候，先复制左边的值，这样可以保证值相等的时候两个元素的相对位置不变。
     */
    private static void mergeSort(int[] a, int low, int high){
        if(low == high) {
            return;
        }
        int mid = low + ((high - low) >> 1);
        mergeSort(a, low, mid);
        mergeSort(a, mid + 1, high);
        merge(a, low, mid, high);
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = 0;
        int p1 = low;
        int p2 = mid + 1;
        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while(p1 <= mid && p2 <= high) {
            temp[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while(p1 <= mid) {
            temp[i++] = arr[p1++];
        }
        while(p2 <= high) {
            temp[i++] = arr[p2++];
        }
        // 把最终的排序的结果复制给原数组
        for(i = 0; i < temp.length; i++) {
            arr[low + i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] a={20,40,32,67,33,1,40,20,89,300,400,15,15,2,400,20,89,1};
        AdvancedSorting.shellSort(a,2,6);
        for(int i:a){
            System.out.print(i+" ");
        }
    }
}
