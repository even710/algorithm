package com.even.exercise.queue;

/**
 * Project Name: ai
 * Des: 循环队列，数组实现
 * Created by Even on 2019/4/3 15:45
 */
public class CircleQueue {

    private int n;//队列的大小

    private String[] items;

    private int head;
    private int tail;

    public CircleQueue(int n) {
        this.n = n;
        this.items = new String[n];
        this.head = 0;
        this.tail = 0;
    }

    private boolean enQueue(String value) {
        if ((tail + 1) % n == head) return false;//队列已满
        items[tail] = value;
        tail = (tail + 1) % n;
        return true;
    }

    private String deQueue() {
        if (tail == head) return null;
        String result = items[head];
        head = (head + 1) % n;
        return result;
    }
}
