package com.even.exercise.sorts2;

import java.util.Arrays;

/**
 * Project Name: ai
 * Des: 快排
 * 从一组数据中，选出一个数，遍历数组，小于该数的值放在左边，大于该数的值放在右边
 * 非稳定的排序，时间复杂度O(nlogn)，空间复杂度O(1)
 * 先排序再分组
 * Created by Even on 2019/4/4 13:28
 */
public class QuickSort {
    private void sort(int[] a, int s, int e) {
        if (a.length == 0) return;
        int mid = quick(a, s, e);
        sort(a, s, mid - 1);
        sort(a, mid + 1, e);
    }

    private int quick(int[] a, int s, int e) {
        int index = e;
        if (s >= e) return index;
        int p = s, q = s;
        int pos = a[s];
        while (q <= e) {
            if (a[q] <= pos) {
                int tmp = a[p];
                a[p] = a[q];
                a[q] = tmp;
                p++;
            }
            q++;
        }
        index = --p;
        return index;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 4, 6, 7, 5, 10, 1, 2, 6, 4, 5, 8, 3};
        QuickSort quickSort = new QuickSort();
        quickSort.sort(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));
    }
}
