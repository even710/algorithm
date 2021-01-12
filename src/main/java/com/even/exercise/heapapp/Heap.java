package com.even.exercise.heapapp;


import java.util.Random;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/9 10:55
 */
public class Heap {
    private int k;
    public Order[] orderHeap;
    private int endIndex;

    public Heap(int k) {
        this.k = k;
        orderHeap = new Order[k];
    }

    public boolean insert(Order newOrder) {
        Order root = orderHeap[1];
        if (root == null) {
            orderHeap[1] = newOrder;
            endIndex = 2;
            return true;
        } else {
            if (endIndex == 4356)
                System.out.println();

            int i = endIndex;
            if (i < k) {
                orderHeap[i] = newOrder;
                while (i > 1) {
                    if (newOrder.getPrice() < orderHeap[i / 2].getPrice()) {
                        Order tmp = orderHeap[i / 2];
                        orderHeap[i / 2] = newOrder;
                        orderHeap[i] = tmp;
                        i = i / 2;
                    } else
                        break;
                }
                endIndex++;
                return true;
            } else {
                Order poll = poll();//把堆顶的移出
                i = k - 1;
                orderHeap[i] = newOrder;
                while (i > 1) {
                    if (newOrder.getPrice() < orderHeap[i / 2].getPrice()) {
                        Order tmp = orderHeap[i / 2];
                        orderHeap[i / 2] = newOrder;
                        orderHeap[i] = tmp;
                        i = i / 2;
                    } else
                        break;
                }
                endIndex++;
                return true;
            }
        }
    }

    public Order poll() {
        endIndex--;
        Order result = orderHeap[1];
        orderHeap[1] = orderHeap[endIndex];
        orderHeap[endIndex] = null;
        int i = 1;
        while (true) {
            Order tmp = orderHeap[i];
            int maxPos = i;
            if (2 * i < k && orderHeap[i * 2] != null && orderHeap[i].getPrice() > orderHeap[i * 2].getPrice())
                maxPos = 2 * i;
            if (2 * i + 1 < k && orderHeap[2 * i + 1] != null && orderHeap[maxPos].getPrice() > orderHeap[2 * i + 1].getPrice())
                maxPos = 2 * i + 1;
            if (maxPos == i) break;
            orderHeap[i] = orderHeap[maxPos];
            orderHeap[maxPos] = tmp;
            i = maxPos;
        }
        return result;
    }

    public Order getMin() {
        return orderHeap[1];
    }

    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(Math.abs(r.nextInt()));
        System.out.println(Math.abs(r.nextInt()));
    }

    public boolean hasSpace() {
        return endIndex < k;
    }
}
