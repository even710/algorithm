package com.even.exercise.sorts1;

/**
 * Project Name: ai
 * Des: 基于链表的冒泡，插入和选择排序
 * Created by Even on 2019/4/4 9:38
 */
public class SortBaseOnLinked {


    /**
     * 基于单链表的冒泡排序，此排序是交换节点，如果可以直接修改值，会简单一点不需要比较p.next和p.next.next，只需要比较p和p.next
     */
    private void bubbleSort() {
        if (head == null) return;
        if (head.next == null) return;
        int i = 0;


        while (true) {
            if (head.data > head.next.data) {
                Node tmp = head.next;
                head.next = tmp.next;
                tmp.next = head;
                head = tmp;
            }
            Node p = head;
            i++;
            boolean flag = true;
            while (p.next != null) {
                if (p.next.next != null) {
                    if (p.next.data > p.next.next.data) {
                        Node tmp = p.next.next;
                        p.next.next = p.next.next.next;
                        tmp.next = p.next;
                        p.next = tmp;
                        flag = false;
                    }
                }
                p = p.next;
            }
            if (flag) {
                System.out.println(i);
                break;
            }
        }
    }

    private void insertSort() {
        if (head == null) return;
        if (head.next == null) return;
        int i = 0;
        if (head.data > head.next.data) {
            Node tmp = head.next;
            head.next = head.next.next;
            tmp.next = head;
            head = tmp;
            i++;
        }
        Node p = head;
        Node q = head.next;
        while (true) {
            i++;
            boolean flag = true;
            while (q.next != null) {
                if (q.data > q.next.data) {
                    flag = false;
                    if (q.next.data < head.data) {
                        Node tmp = q.next;
                        q.next = q.next.next;
                        tmp.next = head;
                        head = tmp;
                        p = head;
                    } else if (q.next.data < p.next.data) {
                        Node tmp = q.next;
                        q.next = q.next.next;
                        tmp.next = p.next;
                        p.next = tmp;
                    } else {
                        p = p.next;
                    }
                } else {
                    p = head;
                    q = q.next;
                }
            }
            q = head.next;
            if (flag) {
                System.out.println(i);
                break;
            }
        }
    }


    private static Node head;

    public static void main(String[] args) {
        head = new Node(5);
        head.next = new Node(4);
        head.next.next = new Node(6);
        head.next.next.next = new Node(1);
        head.next.next.next.next = new Node(3);
        head.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next = new Node(1);
        SortBaseOnLinked sbo = new SortBaseOnLinked();
        sbo.bubbleSort();
        sbo.printAll(head);
    }

    private void printAll(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }

}
