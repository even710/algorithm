package com.even.exercise.sorts2;

import java.util.Arrays;

/**
 * Project Name: ai
 * Des: 归并排序
 * 把问题先分解再合并
 *
 * 稳定的排序，时间复杂度O(nlogn)，空间复杂度为O(n)
 * Created by Even on 2019/4/4 11:22
 */
public class MergeSort {

    private void sort(int[] a, int s, int t) {
        int mid = (s + t) / 2;
        if (s == t)
            return;
        sort(a, s, mid);
        sort(a, mid + 1, t);
        merge(a, s, mid, t);
    }

    private void merge(int[] a, int s, int mid, int t) {
        int[] tmp = new int[t - s + 1];
        int q = mid + 1;
        int p = s;
        int i = 0;
        while (p <= mid && q <= t) {
            if (a[p] <= a[q])
                tmp[i++] = a[p++];
            else
                tmp[i++] = a[q++];

        }
        if (p > mid) {
            for (; i < tmp.length; ) {
                tmp[i++] = a[q++];
            }
        }
        if (q > t) {
            for (; i < tmp.length; ) {
                tmp[i++] = a[p++];
            }
        }
        for (int j = 0; j < tmp.length; j++) {
            a[s + j] = tmp[j];
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 4, 6, 7, 5, 10, 1, 2, 6, 4, 5, 8, 3};
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));
    }

}
