package com.even.exercise.linkedlist;

/**
 * Project Name: ai
 * Des: 链表实现LRU缓存
 * Created by Even on 2019/4/2 10:56
 */
public class LinkedList {
    class Linked {
        public char data;
        public Linked next;

        public Linked(char data) {
            this.data = data;
        }
    }

    public LinkedList() {
    }

    public LinkedList(int length) {
        this.length = length;
    }

    private Linked root;
    private int length = 5;

    public void insert(char data) {
        Linked result = new Linked(data);
        result.next = root;
        root = result;
    }

    public void delete() {
        if (root == null) return;
        Linked p = root;
        for (int i = 0; i < length - 1; i++) {
            if (p == null)
                break;
            if (i == length - 2)
                p.next = null;
            else
                p = p.next;
        }
    }

    /**
     * 查看指定数据data的节点
     * LRU缓存的实现
     *
     * @param data
     * @return
     */
    public Linked find(char data) {
        if (root == null) {
            root = new Linked(data);
            return root;
        }
        Linked p = root;
        int i = 1;
        while (p.next != null) {
            i++;
            if (p.next.data == data) {//如果找到，就把该节点移到链表头部
                Linked result = p.next;
                p.next = result.next;
                result.next = root;
                root = result;
                return result;//返回要查看数据的上一个节点
            }
            p = p.next;
        }
        if (i >= length) {//如果大于等于length，表示缓存已满，要删除最后一个节点
            delete();
        }
        insert(data);//添加新的节点到链表头部
        return root;
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.build("111111111222111111111".toCharArray());
        System.out.println(linkedList.circleLinked("111111111121111111111".length()));
//        linkedList.find('1');
//        linkedList.find('2');
//        linkedList.find('3');
//        linkedList.find('4');
//        linkedList.find('5');
//        linkedList.find('6');
//        linkedList.find('3');
//        linkedList.find('1');
    }

    private Linked p;

    private void build(char[] text) {
        if (text.length == 0) return;
        if (p == null)
            p = new Linked(text[text.length - 1]);
        for (int i = text.length - 2; i >= 0; i--) {
            Linked head = new Linked(text[i]);
            head.next = p;
            p = head;
        }
    }

    private boolean circleLinked(int n) {
        if (n == 1) return true;
        Linked head = new Linked(p.data);
        p = p.next;
        for (int i = 2; i < n; ) {
            Linked result = new Linked(p.data);
            result.next = head;
            head = result;
            p = p.next;
            i += 2;
        }
        if (n % 2 != 0)
            head = head.next;
        while (p != null) {
            if (p.data != head.data)
                return false;
            p = p.next;
            head = head.next;
        }
        return true;
    }

}
