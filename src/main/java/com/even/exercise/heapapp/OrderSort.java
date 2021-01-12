package com.even.exercise.heapapp;

import java.util.Random;

/**
 * Project Name: ai
 * Des:
 * 场景：电商交易系统中，订单数据分库分表存储，如果分了10个库并存在在不同的机器上，开放一个小功能，能快速查询金额最大的前K个订单。
 * <p>
 * 方法findK就是搜索订单金额前K条的记录
 * Created by Even on 2019/4/9 13:59
 */
public class OrderSort {

    private Heap heap;

    private void findK(int k) {
        heap = new Heap(k);
        int num = k / 10;
        boolean[] needFind = new boolean[10];
        for (int i = 0; i < 10; i++) {
            needFind[i] = true;
        }

        boolean over = false;

        for (int i = 0; i < 10; i++) {
            if (needFind[i]) {
                Order[] tmp = getData(i, 0, num);
                for (int j = 0; j < tmp.length; j++) {
                    heap.insert(tmp[j]);
                }
            }
        }
        needFind[heap.getMin().getDb()] = false;
        int count = 1;
        while (!over) {
            over = true;
            for (int i = 0; i < 10; i++) {
                if (needFind[i]) {
                    if (count == 10 && i == 2)
                        System.out.println();
                    over = false;
                    /*1，获取num个数据*/
                    Order[] tmp = getData(i, count, num);

                    /*2，与小顶堆顶部元素比较，大于则存入*/
                    if (tmp != null) {
                        for (Order aTmp : tmp) {
                            if (heap.hasSpace())
                                heap.insert(aTmp);
                            else if (aTmp.getPrice() > heap.getMin().getPrice()) {
                                heap.insert(aTmp);
                            } else {
                                needFind[i] = false;
                                break;
                            }
                        }
                    }
                }
            }
            count++;
        }
        System.out.println("count:" + --count);
    }

    private int MAX_SIZE = 10000;
    private Order[] a = new Order[MAX_SIZE];
    private Order[] b = new Order[MAX_SIZE];
    private Order[] c = new Order[MAX_SIZE];
    private Order[] d = new Order[MAX_SIZE];
    private Order[] e = new Order[MAX_SIZE];
    private Order[] f = new Order[MAX_SIZE];
    private Order[] g = new Order[MAX_SIZE];
    private Order[] h = new Order[MAX_SIZE];
    private Order[] i = new Order[MAX_SIZE];
    private Order[] j = new Order[MAX_SIZE];


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        OrderSort orderSort = new OrderSort();
        orderSort.buildData();
        orderSort.findK(10000);
        Order out;
        int k = 1;
        while ((out = orderSort.heap.poll()) != null) {
            if (k == 4355)
                System.out.println();
            k++;
            System.out.println("id:" + out.getId() + ",price:" + out.getPrice() + ",db:" + out.getDb());
        }
        System.out.println("k=" + k);
        long end = System.currentTimeMillis();
        System.out.println("cost time:" + (end - start));
    }

    private static Random r = new Random(3);

    private Order[] getData(int db, int count, int num) {
        switch (db) {
            case 0:
                return copyArray(a, count, num);
            case 1:
                return copyArray(b, count, num);
            case 2:
                return copyArray(c, count, num);
            case 3:
                return copyArray(d, count, num);
            case 4:
                return copyArray(e, count, num);
            case 5:
                return copyArray(f, count, num);
            case 6:
                return copyArray(g, count, num);
            case 7:
                return copyArray(h, count, num);
            case 8:
                return copyArray(i, count, num);
            case 9:
                return copyArray(j, count, num);
            default:
                return null;
        }
    }

    private Order[] copyArray(Order[] array, int count, int num) {
        Order[] tmp = null;
        if (count * num + num <= MAX_SIZE) {
            tmp = new Order[num];
            int i = 0;
            for (int n = count * num; n < count * num + num; n++) {
                tmp[i++] = array[n];
            }
        }
        return tmp;
    }


    private void buildData() {
        int min = Math.abs(r.nextInt());
        int minPrice = 1200;
        for (int i = 0; i < a.length; i++) {
            a[i] = new Order(min + i, minPrice - i, 0);
        }
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        min = Math.abs(r.nextInt());
        minPrice = 1300;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            b[i] = new Order(min + i, minPrice - i, 1);
        }
        min = Math.abs(r.nextInt());
        minPrice = 1400;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            c[i] = new Order(min + i, minPrice - i, 2);
        }
        min = Math.abs(r.nextInt());
        minPrice = 1100;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            d[i] = new Order(min + i, minPrice - i, 3);
        }
        min = Math.abs(r.nextInt());
        minPrice = 900;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            e[i] = new Order(min + i, minPrice - i, 4);
        }
        min = Math.abs(r.nextInt());
        minPrice = 999;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            f[i] = new Order(min + i, minPrice - i, 5);
        }
        min = Math.abs(r.nextInt());
        minPrice = 956;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            g[i] = new Order(min + i, minPrice - i, 6);
        }
        min = Math.abs(r.nextInt());
        minPrice = 1324;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            h[i] = new Order(min + i, minPrice - i, 7);
        }
        min = Math.abs(r.nextInt());
        minPrice = 1623;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int n = 0; n < a.length; n++) {
            i[n] = new Order(min + n, minPrice - n, 8);
        }
        min = Math.abs(r.nextInt());
        minPrice = 1563;
        System.out.println("database min id:" + min + ", minPrice:" + minPrice);
        for (int i = 0; i < a.length; i++) {
            j[i] = new Order(min + i, minPrice - i, 9);
        }
    }

}
