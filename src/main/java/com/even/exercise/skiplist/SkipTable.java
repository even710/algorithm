package com.even.exercise.skiplist;

import java.util.Arrays;
import java.util.Random;

/**
 * Project Name: ai
 * Des: 跳表的实现，数据不重复
 * Created by Even on 2019/4/8 9:39
 */
public class SkipTable {
    public static Node root;

    public int MAX_LEVEL = 5;
    public int count = 0;//记录链表元素总数
    public Node[] skipList;

    public static void main(String[] args) {
        SkipTable skipTable = new SkipTable();
        skipTable.insert(2);
        skipTable.insert(1);
        skipTable.insert(4);
        skipTable.insert(5);
        skipTable.insert(3);
        skipTable.insert(6);
        skipTable.insert(4);
        Node node = skipTable.find(skipTable.skipList[skipTable.MAX_LEVEL - 1], 3);
        skipTable.delete(skipTable.skipList[skipTable.MAX_LEVEL - 1], 3);
        System.out.println(Arrays.toString(skipTable.skipList));

    }

    public SkipTable() {
        skipList = new Node[MAX_LEVEL];//定义一个数组在存储各索引链表
    }

    public SkipTable(int data) {
        skipList = new Node[MAX_LEVEL];//定义一个数组在存储各索引链表
        root = new Node(data);
        skipList[0] = root;
        for (int i = 1; i < MAX_LEVEL; i++) {
            Node p = new Node(data);
            p.down = skipList[i - 1];
            skipList[i] = p;
        }
    }

    public int nodeNum(Node p, Node q) {
        int n = 0;
        while (p != q) {
            p.next = p;
            n++;
        }
        return ++n;
    }

    /**
     * 只能删除链表出现的第一个
     *
     * @param p
     * @param data
     * @return
     */
    public boolean delete(Node p, int data) {
        while (p.next != null) {
            if ((p.next.data == data)) {
                if (p.down != null) {
                    delete(p.down, data);
                }
                p.next = p.next.next;
                return true;
            }
            if (p.data < data && p.next.data > data) {
                if (p.down != null)
                    return delete(p.down, data);
            }
            p = p.next;
        }
        //如果相等，p是头结点且链表只有一个元素，清空数组
        if (p.data == data) {
            for (int i = 0; i < skipList.length; i++) {
                skipList[i] = null;
            }
            return true;
        }
        if (p.down != null)
            return delete(p.down, data);
        else
            return false;


    }

    public boolean insert(int data) {
        count++;
        //跳表初始化
        if (root == null) {
            root = new Node(data);
            skipList[0] = root;
            for (int i = 1; i < MAX_LEVEL; i++) {
                Node p = new Node(data);
                p.down = skipList[i - 1];
                skipList[i] = p;
            }
            return true;
        }
        //当插入的数据是链表头部时，则需要把所有的索引也一起更新
        if (root.data > data) {
            Node newNode = new Node(data);
            newNode.next = root;
            skipList[0] = newNode;
            for (int i = 1; i < MAX_LEVEL; i++) {
                Node p = new Node(data);
                p.down = skipList[i - 1];
                p.next = skipList[i];
                skipList[i] = p;
            }
            return true;
        }
        int level = randomLevel();
        /*针对每一层都要进行插入操作*/
        skipInsert(skipList[level], data);
        return true;
    }

    public Node find(Node p, int data) {
        while (p.next != null) {
            if ((p.data == data) || (p.data < data && p.next.data > data)) {
                if (p.down != null)
                    return find(p.down, data);
                else if (p.data == data)
                    return p;
                else return null;
            }
            p = p.next;
        }
        //代码到此，表示p.next==null,可能是尾结点，也可能是头结点
        if (p.down != null)
            return find(p.down, data);
        if (p.data == data)
            return p;
        return null;//表示不存在此数据
    }

    public Node skipInsert(Node p, int data) {
//        Node p = skipList[index];
        while (p.next != null) {
            if (p.data <= data && p.next.data > data) {

                Node down = null;
                if (p.down != null)
                    down = skipInsert(p.down, data);
                Node next = new Node(data);
                next.next = p.next;
                next.down = down;//设置down指针，如果是第一层，则指向空
                p.next = next;
                return next;
            }
            p = p.next;
        }
        //代码能到这里，代表p.next==null
        Node next = new Node(data);
        next.next = p.next;
        Node down = null;
        if (p.down != null)
            down = skipInsert(p.down, data);
        next.down = down;//设置down指针，如果是第一层，则指向空
        p.next = next;
        return next;
    }


    public Random r = new Random();

    /**
     * 随机函数，用于维护平衡性，插入数据时，把数据插入到第一层到第k层的链表
     * 为了保证随机性，遍历MAX_LEVEL次，1/2的机率来决定是否添加数据
     *
     * @return
     */
    public int randomLevel() {
        int k = 0;
        for (int i = 1; i < MAX_LEVEL; i++) {
            if (r.nextInt() % 2 == 1)
                k++;
        }
        return k;
    }

    /**
     * 纯粹的单链表有序插入方法
     *
     * @param data
     * @return
     */
    public boolean simpleInsert(int data) {
        if (root == null) {
            root = new Node(data);
            return true;
        }
        Node p = root;
        if (p.data > data) {
            Node newNode = new Node(data);
            newNode.next = root;
            root = newNode;
        }
        while (p.next != null) {
            if (p.data < data && p.next.data > data) {
                Node newNode = new Node(data);
                newNode.next = p.next;
                p.next = newNode;
                break;
            }
            p = p.next;
        }
        if (p.next == null) {
            p.next = new Node(data);
        }
        return true;
    }

    public boolean deleteByValue(int value) {
        return delete(skipList[MAX_LEVEL - 1], value);
    }

    /*定义节点类*/
    public static class Node {
        public int data;
        public Node next;
        public Node down;

        public Node(int data) {
            this.data = data;
        }
    }
}
