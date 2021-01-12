package com.even.exercise.stack;

/**
 * Project Name: ai
 * Des: 数组实现栈
 * Created by Even on 2019/4/3 9:18
 */
public class ArrayStack {

    private String[] items;
    private int count;
    private int n;

    public ArrayStack(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    private boolean push(String value) {
        if (count == n)
            return false;
        items[count++] = value;
        return true;
    }

    private boolean pushDynamic(String value) {
        if (count == n) {
            n = n * 2;
            String[] tmp = new String[n];
            for (int i = 0; i < items.length; i++) {
                tmp[i] = items[i];
            }
            items = tmp;
        }
        items[count++] = value;
        return true;
    }

    private String poll() {
        if (count == 0)
            return null;
        return items[--count];
    }

    public static void main(String[] args) {
    }

}
