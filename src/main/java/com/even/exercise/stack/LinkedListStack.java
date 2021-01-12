package com.even.exercise.stack;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/3 9:26
 */
public class LinkedListStack {


    public static void main(String[] args) {
        LinkedListStack linkedListStack = new LinkedListStack(5);
        linkedListStack.pull("1");
        linkedListStack.pull("1");
        linkedListStack.pull("1");
        linkedListStack.pull("1");
        linkedListStack.pull("2");
        System.out.println(linkedListStack.pull("2"));
        System.out.println(linkedListStack.poll());
        System.out.println(linkedListStack.poll());
        System.out.println(linkedListStack.poll());
        System.out.println(linkedListStack.poll());
        System.out.println(linkedListStack.poll());
        System.out.println(linkedListStack.poll());
    }

    private Node head;
    private int n;
    private int count;

    public LinkedListStack(int n) {
        this.n = n;
        this.count = 0;
    }

    private boolean pull(String value) {
        if (count == n) return false;
        Node newNode = new Node(value);
        if (head != null)
            newNode.next = head;
        head = newNode;
        count++;
        return true;
    }

    private String poll() {
        if (count == 0)
            return null;
        String result = head.data;
        head = head.next;
        count--;
        return result;
    }

    private static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this.data = data;
        }
    }


}
