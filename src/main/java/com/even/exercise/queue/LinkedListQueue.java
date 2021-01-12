package com.even.exercise.queue;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/3 10:57
 */
public class LinkedListQueue {

    private Node item;
    private int count;
    private int n;

    private boolean enQueue(String value) {
        if (count == n) return false;//已满
        Node newNode = new Node(value);
        if (item == null) {
            item = newNode;
            count++;
            return true;
        }
        Node p = item;
        if (p.next != null) {
            p = p.next;
        }
        p.next = newNode;
        count++;
        return true;
    }

    private String deQueue(){
        if (count == 0) return null;
        String result = item.data;
        item = item.next;
        count--;
        return result;
    }


    public LinkedListQueue(int n) {
        this.n = n;
        this.count = 0;
    }

    private static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this.data = data;
        }
    }
}
