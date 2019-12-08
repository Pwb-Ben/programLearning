package com.programlearning.learning.sortingAlgorithm;

public class SimpleSort {

    /*
     * 冒泡排序
     */
    static void bubbleSort(int[] a,int low,int high){
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        // 外层for循环控制循环次数
        for(int i=low;i<high;i++){
            int tem = 0;
            // 内层for循环控制相邻的两个元素进行比较
            for(int j=i+1;j<high;j++){
                if(a[i]>a[j]){
                    tem = a[j];
                    a[j]= a[i];
                    a[i] = tem;
                }
            }
        }
    }

    /*
     * 插入排序
     */
    static void insertionSort(int[] a, int low, int high){
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        for (int i = low; i < high-1; i++) {
            int j, ai = a[i + 1];
            for(j = i; j >= low && ai < a[j]; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = ai;
        }
    }

    /*
     * 选择排序
     */
    static void selectionSort(int[] a, int low, int high){
        if (null == a || low < 0 || a.length == 0 || a.length < high) {
            return;
        }
        int temp = 0, minValueIndex = 0;
        for (int i = low; i < high; i++) {
            minValueIndex = i;
            for (int j = i + 1; j < high; j++) {
                if (a[j] < a[minValueIndex]) {
                    minValueIndex = j;
                }
            }
            if (minValueIndex != i) {
                temp = a[i];
                a[i] = a[minValueIndex];
                a[minValueIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] a={20,40,32,67,33,1,40,20,89,300,400,15,15,2,1,20,89,400};
        SimpleSort.insertionSort(a,0,a.length);
        //SimpleSort.bubbleSort(a,2,5);
        for(int i:a){
            System.out.print(i+" ");
        }
    }
}
