package com.even.algorithm;

import java.util.Arrays;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/3/25 17:26
 */
public class HeapSort {
    private static int[] a;

    private static int count;//已存储的数目

    private static int n;//可存储的最大数目

    private static void out(int[] a, int count) {
        if (a.length > 0) {
            for (int i = 1; i <= count; i++) {
                System.out.print(a[i] + ",");
            }
        }
    }

    private HeapSort(int n) {
        a = new int[n + 1];
        count = 0;
        HeapSort.n = n;
    }

    /**
     * 插入数据
     *
     * @param data
     */
    private void insert(int data) {
        if (count >= n) return;
        count++;
        a[count] = data;
        int index = count;
        while (true) {
            int p = a[index / 2];//父结点
            if (p > data || index == 1)
                break;
            a[index / 2] = data;
            a[index] = p;
            index = index / 2;
        }
    }

    /**
     * 向下堆化
     *
     * @param a
     * @param count
     * @param i
     */
    private void heapify(int[] a, int count, int i) {
        while (true) {
            int tmp = a[i];
            int maxPos = i;
            if (i * 2 <= count && a[i] < a[i * 2]) maxPos = 2 * i;
            if ((i * 2 + 1) <= count && a[maxPos] < a[i * 2 + 1]) maxPos = 2 * i + 1;
            if (maxPos == i) break;
            a[i] = a[maxPos];
            a[maxPos] = tmp;
            i = maxPos;
        }
    }

    /**
     * 删除堆顶元素
     */
    private void deleteMax() {
        if (count == 0) return;//如果等于0，代表没有数据
        a[1] = a[count];
        a[count] = 0;
        --count;
        int i = 1;
        heapify(a, count, 1);
    }

    /**
     * 堆排序，把堆顶元素和最后一个元素调换位置，再对除了最后一个元素的其他元素进行向下堆化
     * @param a
     * @param n
     */
    private void sort(int[] a, int n) {
        for (int i = n; i > 1; --i) {
            int tmp = a[i];
            a[i] = a[1];
            a[1] = tmp;
            heapify(a, i - 1, 1);
        }
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort(15);
//        heapSort.insert(2);
//        heapSort.insert(10);
//        heapSort.insert(30);
//        heapSort.insert(15);
//        heapSort.insert(61);
//        heapSort.insert(41);
//        heapSort.insert(34);
//        heapSort.insert(21);
//        heapSort.insert(16);
//        heapSort.insert(100);
//        heapSort.insert(101);
//        heapSort.insert(102);
//        heapSort.insert(103);
//        heapSort.insert(104);
//        heapSort.insert(105);
//        out(a, count);
        System.out.println();
        a = new int[]{0, 2, 10, 30, 15, 61, 41, 34, 21, 16, 100, 101, 102, 103, 104, 105};
        for (int i = n / 2; i >= 1; --i) {
            heapSort.heapify(a, n, i);
        }
        System.out.println(Arrays.toString(a));
        heapSort.sort(a, n);
        System.out.println(Arrays.toString(a));
//        heapSort.deleteMax();
//        System.out.println("---------------------");
//        out(a, count);

    }
}
