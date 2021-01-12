package com.even.exercise.queue;

/**
 * Project Name: ai
 * Des: 数组实现的队列
 *
 *
 * Created by Even on 2019/4/3 10:32
 */
public class ArrayQueue {
    private String[] items;
    private int n;
    private int head;
    private int tail;

    public ArrayQueue() {
        this.items = new String[10];
        this.n = 10;
        this.head = 0;
        this.tail = 0;
    }

    public ArrayQueue(int n) {
        this.n = n;
        this.items = new String[n];
        this.head = 0;
        this.tail = 0;
    }

    private boolean enQueue(String value) {
        if (tail == n) {
            if (head == 0)
                return false;//代码队列已满
            else {
            /*把已填充的数据向数组前方移动*/
                for (int i = 0; i < tail - head; i++) {
                    items[i] = items[head + i];
                }
                tail = tail - head;
                head = 0;
            }
        }
        items[tail++] = value;
        return true;
    }

    private String deQueue() {
        if (head == tail) return null;//没有数据
        return items[head++];
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(5);
        arrayQueue.enQueue("1");
        arrayQueue.enQueue("2");
        arrayQueue.enQueue("3");
        arrayQueue.enQueue("4");
        arrayQueue.enQueue("5");
        System.out.println(arrayQueue.enQueue("3"));
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
    }


}
