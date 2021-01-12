package com.even.exercise.sorts3;

import java.util.Arrays;

/**
 * Project Name: ai
 * Des:线性排序
 * 桶排序、计数排序、基数排序
 * Created by Even on 2019/4/4 14:51
 */
public class LinearSort {

    /**
     * 桶排序，把要排序的数据分到几个有序的桶里，桶里的数据再进行单独排序，当排序完成后，把桶整合一起，就是有序的数据了
     *
     * @param a
     * @param m 桶的数量
     */
    private void bucketSort(int[] a, int n, int m) {
        int min = 0;
        int max = 0;

        for (int value : a) {
            if (min > value)
                min = value;
            if (max < value)
                max = value;
        }

        int count = (max - min) / m;


    }

    /**
     * 计数排序，用于数据范围不大的场景中，如果数据范围k比要排序的数据n大很多，就不适合用计数排序了。只能给非负整数排序
     */
    private void countingSort(int[] a) {
        if (a.length == 0) return;
        int n = a.length;
        int max = a[0];
        for (int i = 1; i < n; i++)
            if (max < a[i])
                max = a[i];
        int[] c = new int[max + 1];
        for (int i = 0; i < n; i++) {
            c[a[i]] = c[a[i]] + 1;
        }

        for (int i = 1; i <= max; i++) {
            c[i] = c[i - 1] + c[i];
        }

        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            r[c[a[i]] - 1] = a[i];
            c[a[i]] = c[a[i]] - 1;
        }
        /**
         * 从后面开始排序，这样可以确保稳定性
         */
        for (int i = n - 1; i >= 0; i--) {
            a[i] = r[i];
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 把数组中的数拆分成一位位，从低位开始进行稳定排序。每一位可以使用计数排序或桶排序
     *
     * @param a
     */
    private void radixSort(String[] a) {
        for (int i = 1; i <= a[0].length(); i++) {
            countingSort(a, i);
        }
        System.out.println(Arrays.toString(a));
    }


    private void countingSort(String[] a, int pos) {
        int n = a.length;
        int numLength = a[0].length();
        int[] tmp = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            tmp[i] = Integer.parseInt(a[i].substring(numLength - pos, numLength - pos + 1));
            if (max < tmp[i])
                max = tmp[i];
        }

        int[] c = new int[max + 1];
        for (int i = 0; i < n; i++) {
            c[tmp[i]] = c[tmp[i]] + 1;
        }
        for (int i = 1; i <= max; i++) {
            c[i] = c[i] + c[i - 1];
        }

        String[] r = new String[n];
        for (int i = n - 1; i >= 0; i--) {
            int index = c[tmp[i]] - 1;
            r[index] = a[i];
            c[tmp[i]]--;
        }
        for (int i = 0; i < n; i++) {
            a[i] = r[i];
        }
    }

    public static void main(String[] args) {
        LinearSort linearSort = new LinearSort();
        String[] a = {"13534474639", "13378647200", "13277959528", "15273075778", "13690233017", "13125196132"};
        linearSort.radixSort(a);
    }
}
