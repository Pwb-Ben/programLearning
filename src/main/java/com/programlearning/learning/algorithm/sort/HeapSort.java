package com.programlearning.learning.algorithm.sort;

/**
 * 堆排序 小顶堆
 * <a href="https://mp.weixin.qq.com/s/g6OA6353_lonsdE7eOMANQ">...</a>
 */
public class HeapSort {

    private static void heapSort(int[] nums) {
        int len = nums.length;
        int[] a = new int[len + 1];

        // 为了更直观的描述，空出数组的第一位，这样我们就可以通过 i * 2 和 i * 2 + 1 来求得左孩子节点和右孩子节点
        System.arraycopy(nums, 0, a, 1, len);

        // 建堆，通过下沉操作建堆效率更高，具体过程是，找到最后一个非叶子节点，然后从后往前遍历执行下沉操作
        for (int i = len / 2; i >= 1; i--) {
            sink(a, i, len);
        }

        for(int i : a){
            System.out.print(i+" ");
        }
        System.out.println();

        // 将堆顶元素（代表最大元素）与最后一个元素交换，然后新的堆顶元素进行下沉操作，遍历执行上述操作，则可以完成排序
        int k = len;
        while (k > 1) {
            swap(a, 1, k--);
            sink(a, 1, k);
        }

        System.arraycopy(a, 1, nums, 0, len);
    }

    private static void sink(int[] nums, int k, int end) {
        while (2 * k <= end) {
            // 定位左子节点
            int j = 2 * k;
            // 找出子节点中最大的那个
            if (j + 1 <= end && nums[j + 1] > nums[j]) {
                j++;
            }
            // 交换操作，父节点下沉，与最大的孩子节点交换
            if (nums[j] > nums[k]) {
                swap(nums, j, k);
            } else {
                break;
            }
            // 继续下沉
            k = j;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {20,40,32,67,33,1,40,20,89,300,400,15,15,2,400,20,89,1};
        HeapSort.heapSort(a);
        for(int i : a){
            System.out.print(i+" ");
        }
    }
}
