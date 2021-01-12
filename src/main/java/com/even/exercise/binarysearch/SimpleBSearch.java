package com.even.exercise.binarysearch;

/**
 * Project Name: ai
 * Des: 二分查找算法
 * 注意点：
 * 1，循环退出条件
 * 2，mid的取值
 * 3，low和high的更新
 * <p>
 * 局限性
 * 1，依赖顺序表结构，就是数组。
 * 2，只针对有序数据，如果数据不怎么更新频繁，可以只进行一次排序，多次二分查找。如果数据更新频繁的场景，二分查找将不再适用。
 * 3, 数据量太小不适合二分查找，因为跟普通的排序相比，性能差别不大。
 * 4，数据量太大也不适合二分查找，因为依赖数组，数组是连续的内存空间。
 * <p>
 * 因为数组只是存储数据本身，不用存储其他额外的信息，所以如果要求在限定的内存大小下解决问题，可以使用二分查找算法。
 * 更适用于处理静态数据，没有频繁的数据插入、删除操作的数据。
 * Created by Even on 2019/4/4 16:29
 */
public class SimpleBSearch {

    /**
     * 最简单的二分查找算法，数组中不存在重复数据
     *
     * @param a
     * @return
     */
    public int bSearch(int[] a, int value) {
        int n = a.length;
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == value)
                return mid;
            else if (a[mid] > value)
                high = mid + 1;
            else if (a[mid] < value)
                low = mid - 1;
        }
        return -1;
    }


    /**
     * 求给定数的平方根
     *
     * @param a
     * @return
     */
    private double sqart(double a) {
        if (a > 1) {
            return bSearch(a, 1, a);
        } else {
            return bSearch(a, a, 1);
        }
    }

    private double bSearch(double a, double low, double high) {
        double mid = (low + high) / 2;
        double result = mid * mid;
        if (Math.abs(result - a) <= 0.000001)
            return mid;
        else if (result > a)
            return bSearch(a, low, mid - 0.000001);
        else
            return bSearch(a, mid + 0.000001, high);
    }


    /**
     * 找到第一个等于给定值的数的下标
     *
     * @param a
     * @param value
     * @return
     */
    private int bSearch1(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;//low+(high-low)/2等于(low+high)/2
            if (a[mid] > value)
                high = mid - 1;
            else if (a[mid] < value)
                low = mid + 1;
            else {
                if (mid == 0 || a[mid - 1] != value) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个等于给定值的数的下标
     *
     * @param a
     * @param value
     * @return
     */
    private int bSearch2(int[] a, int value) {
        int n = a.length;
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;//low+(high-low)/2等于(low+high)/2
            if (a[mid] > value)
                high = mid - 1;
            else if (a[mid] < value)
                low = mid + 1;
            else {
                if (mid == n - 1 || a[mid + 1] != value) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素，给定值可能不存在于数组中。
     *
     * @param a
     * @param value
     * @return
     */
    private int bSearch3(int[] a, int value) {
        int n = a.length;
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;//low+(high-low)/2等于(low+high)/2
            if (a[mid] >= value) {
                if (mid == 0 || a[mid - 1] < value)
                    return mid;
                else
                    high = mid - 1;
            } else if (a[mid] < value) {
                if (a[mid + 1] >= value)
                    return mid + 1;
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 求解最后一个小于等于给定值的元素
     *
     * @param a
     * @param value
     * @return
     */
    private int bSearch4(int[] a, int value) {
        int n = a.length;
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;//low+(high-low)/2等于(low+high)/2
            if (a[mid] > value) {
                if (a[mid - 1] <= value)
                    return mid - 1;
                high = mid - 1;
            } else if (a[mid] <= value) {
                if (mid == 0 || a[mid + 1] > value)
                    return mid;
                else
                    low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SimpleBSearch simpleBSearch = new SimpleBSearch();
        System.out.println(String.format("%.6f", simpleBSearch.sqart(10)));
    }
}

