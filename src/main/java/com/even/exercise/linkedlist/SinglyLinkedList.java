package com.even.exercise.linkedlist;

/**
 * Project Name: ai
 * Des:
 * 1）单链表的插入、删除、查找操作；
 * 2）链表中存储的是int类型的数据；
 * <p>
 * 技巧：
 * 1，理解指针或引用的含义
 * 2，警惕指针丢失和内存泄漏
 * 3，利用哨兵简化实现难度
 * 4，重点留意边界条件处理：（1）如果链表为空时，代码是否能正常工作；（2）如果链表只包含一个节点时，代码是否能工作；
 * （3）如果链表只包含两个节点时，代码是否能工作；（4）代码逻辑在处理头节点和尾节点时能否正常工作
 * 5，举例画图
 * <p>
 * 经典链表操作
 * 1，单链表反转
 * 2，链表中环的检测
 * 3，两个有序的链表合并
 * 4，删除表倒数第n个节点
 * 5，求链表的中间节点
 * Created by Even on 2019/4/2 14:14
 */
public class SinglyLinkedList {

    /**
     * 单链表反转
     *
     * @param p
     */
    private void inverseLinkList(Node p) {
        Node tmpHead = new Node(1000);

        Node next = p.next;
        p.next = null;
        tmpHead.next = p;
        Node pre = null;
        while (next != null) {
            pre = next.next;
            next.next = tmpHead.next;
            tmpHead.next = next;
            next = pre;
        }
        tmpHead = tmpHead.next;
        printAll(tmpHead);
    }

    /**
     * 链表中环的检测
     *
     * @param head
     */
    private boolean loopLinkedList(Node head) {
        if (head == null) return false;
        Node p;
        if (head.next == null) return false;
        else p = head.next;
        Node q;
        if (head.next.next == null) return false;
        else q = head.next.next;

        while (p != q) {
            p = p.next;
            if (p == null)
                return false;
            if (q == null || q.next == null)
                return false;
            q = q.next.next;
        }
        return true;
    }

    /**
     * 两个有序的链表合并,时间复杂度是a的大小或b的大小，取决于a和b第一个值谁大
     *
     * @param a
     * @param b
     */
    private void sorlLinked(Node a, Node b) {
        Node p = a;
        Node q = b;
        while (p != null && q != null) {
            if (p.data <= q.data) {
                if (p.next != null) {
                    if (p.next.data > q.data) {
                        Node tmp = q.next;
                        q.next = p.next;
                        p.next = q;
                        q = tmp;
                        p = p.next;
                    } else {
                        p = p.next;
                    }
                } else {
                    p.next = q;
                    break;
                }
            } else {
                sorlLinked(b, a);
                return;
            }
        }
        printAll(a);//打印a，即合并后的链表
    }


    /**
     * 删除链表倒数第n个节点。注意，链表是否有环，有环需要求环的开始点。
     * 解决方案一：前提是单链表无环，先反转链表，删除第n个节点，再反转链表。时间复杂度为O(m)，空间复杂度O(1)
     * 解决方案二：前提是单链表无环，先遍历一次，得到总长度m，处理m-n下标的节点。时间复杂度为O(m)，空间复杂度O(1)
     *
     * @param n
     */
    private void deleteNode(Node a, int n) {
        int m = 1;
        Node p = a;
        while (p != null) {
            p = p.next;
            m++;
        }
        if (n >= m || n <= 0)
            return;
        p = a;
        for (int i = 0; i < m; i++) {
            if (i == m - n - 1) {
                p.next = p.next.next;
                break;
            }
            p = p.next;
        }
    }

    /**
     * 求链表的中间节点
     * 解决方案，通过快慢两节点来实现。注意奇数和偶数两种情况的处理
     *
     * @param a
     */
    private void findMiddleNode(Node a) {
        Node p = a;
        Node q = a;
        while (q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        printAll(p);
    }

    private Node head = null;

    /**
     * 单链表查找操作
     *
     * @param value
     * @return
     */
    private Node findByValue(int value) {
        Node p = head;
        if (p != null && p.data != value) {
            p = p.next;
        }
        return p;
    }

    /**
     * 查看第index+1个元素
     *
     * @param index
     * @return
     */
    private Node findByIndex(int index) {
        int pos = 0;
        Node p = head;
        while (p != null && pos != index) {
            p = p.next;
            ++pos;
        }
        return p;
    }

    /**
     * 向头部插入节点
     *
     * @param value
     */
    public void insertToHead(int value) {
        Node newNode = new Node(value);
        if (head == null)
            head = newNode;
        else {
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * 向尾部插入节点
     *
     * @param value
     */
    public void insertTail(int value) {
        Node newNode = new Node(value);
        if (head == null)
            head = newNode;
        else {
            Node p = head;
            while (p.next != null) {
                p = p.next;
            }
            newNode.next = null;
            p.next = newNode;
        }
    }

    /**
     * 在某个节点后面插入
     *
     * @param p
     * @param value
     */
    public void insertAfter(Node p, int value) {
        Node newNode = new Node(value);
        if (p == null) return;
        newNode.next = p.next;
        p.next = newNode;
    }


    /**
     * 在某个节点之前插入新值
     *
     * @param p
     * @param value
     */
    public void insertBefore(Node p, int value) {
        if (p == null) return;
        if (head == p) {
            insertToHead(value);
            return;
        }
        Node q = head;
        while (q != null && q.next != p) {
            q = q.next;
        }
        if (q == null)
            return;
        Node newNode = new Node(value);
        newNode.next = p;
        q.next = newNode;
    }

    /**
     * 删除某个节点
     *
     * @param p
     */
    public void deleteBy(Node p) {
        if (p == null || head == null) return;

        if (p == head) {
            head = head.next;
            return;
        }

        Node q = head;
        while (q != null && q.next != p) {
            q = q.next;
        }
        if (q == null)
            return;
        q.next = q.next.next;
    }

    /**
     * 删除指定数据的节点，此方法只会删除第一个与value相等的值的节点
     *
     * @param value
     */
    public void deleteByValue(int value) {
        if (head == null) return;
        Node p = head;
        Node q = null;
        while (p != null && p.data != value) {
            q = p;
            p = p.next;
        }
        if (p == null) return;
        if (q == null) head = head.next;
        else q.next = q.next.next;
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public void printAll(Node node) {
        Node p = node;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * 判断两个节点是否相等
     *
     * @param left
     * @param right
     * @return
     */
    public boolean TFResult(Node left, Node right) {
        Node l = left;
        Node r = right;
        while (l != null && r != null) {
            if (l.data == r.data) {
                l = l.next;
                r = r.next;
            } else
                break;
        }
        if (l == null && r == null)
            return true;
        else return false;
    }

    private Node root;
    private int length = 5;


    public void insert(char data) {
        Node result = new Node(data);
        result.next = root;
        root = result;
    }

    /**
     * 只有在满的时候会删除
     */
    public void delete() {
        if (root == null) return;
        Node p = root;
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
    public Node find(char data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        Node p = root;
        int i = 1;
        while (p.next != null) {
            i++;
            if (p.next.data == data) {//如果找到，就把该节点移到链表头部
                Node result = p.next;
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
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.insertToHead(1);
        singlyLinkedList.insertToHead(2);
        singlyLinkedList.insertToHead(3);
        singlyLinkedList.insertToHead(4);
        singlyLinkedList.insertToHead(5);
        singlyLinkedList.inverseLinkList(singlyLinkedList.head);
//        singlyLinkedList.insertToHead(4);
//        singlyLinkedList.insertToHead(3);
//        singlyLinkedList.insertToHead(2);
//        singlyLinkedList.insertToHead(1);

        Node a = new Node(1);
        a.next = new Node(3);
        a.next.next = new Node(5);
        a.next.next.next = new Node(10);
        a.next.next.next.next = new Node(12);
        a.next.next.next.next.next = new Node(15);
        Node b = new Node(2);
        b.next = new Node(2);
        b.next.next = new Node(4);
        b.next.next.next = new Node(5);
        b.next.next.next.next = new Node(10);
        b.next.next.next.next.next = new Node(12);
        singlyLinkedList.sorlLinked(b, a);

        Node loop = new Node(1);
        loop.next = new Node(2);
        loop.next.next = new Node(3);
        loop.next.next.next = new Node(4);
        loop.next.next.next.next = loop;
        System.out.println(singlyLinkedList.loopLinkedList(loop));
    }

    private static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
