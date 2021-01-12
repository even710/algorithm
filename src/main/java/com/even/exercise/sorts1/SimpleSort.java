package com.even.exercise.sorts1;

import java.util.Arrays;

/**
 * Project Name: ai
 * Des: 概念
 * 1，空间复杂度为O(1)的排序是原地排序
 * 2，排序时，相同数据的顺序不改变，叫做稳定的排序
 *
 * 冒泡排序和插入排序时间复杂度一样，但是插入排序赋值语句要比冒泡排序要少，所以性能相对来说会更优秀一点。
 * 选择排序是不稳定的排序
 *
 * 要想分析、评价一个排序算法，需要从执行效率，内存消耗和稳定性三个方面来计算。
 * Created by Even on 2019/4/3 17:28
 */
public class SimpleSort {

    /**
     * 冒泡排序的实现
     * 时间复杂度是O(n^2)，空间复杂度为O(1)
     * 稳定的排序
     */
    private void bubbleSort(int[] a) {
        if (a.length == 0) return;
        for (int i = 0; i < a.length; i++) {
            boolean flag = true;
            for (int j = 0; j < a.length; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag)
                break;
        }

    }

    /**
     * 插入排序
     * 时间复杂度是O(n^2)，最好复杂度是O(n)，最坏是O(n^2)，空间复杂度为O(1)。
     * 因为是tmp<a[j]比较，所以是稳定的排序；如果是<=，就是不稳定
     *
     * @param a
     */
    private void insertSort(int[] a) {
        if (a.length == 0) return;
        for (int i = 1; i < a.length; i++) {
            int tmp = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (tmp < a[j]) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = tmp;
        }
    }

    /**
     * 选择排序
     * 平均和最坏复杂度为O(n^2)，原地排序，不稳定的排序
     *
     * @param a
     */
    private void seletcSort(int[] a) {
        if (a.length == 0) return;
        for (int i = 0; i < a.length; i++) {
            int min = a[i];
            int j = i + 1;
            int tmp = i;
            for (; j < a.length; j++) {
                if (a[j] < min) {
                    min = a[j];
                    tmp = j;
                }
            }
            a[tmp] = a[i];
            a[i] = min;
        }
    }

    public static void main(String[] args) {
        int[] a = {4, 5, 6, 1, 3, 2};
        SimpleSort simpleSort = new SimpleSort();
        simpleSort.seletcSort(a);
        System.out.println(Arrays.toString(a));
    }
}
