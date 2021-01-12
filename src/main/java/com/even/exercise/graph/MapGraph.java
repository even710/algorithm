package com.even.exercise.graph;


import java.util.LinkedList;

/**
 * Project Name: ai
 * Des: 地图求最短路径，有向有权图求解两个顶点间最短距离——Dijkstra算法实现
 * Created by Even on 2019/4/11 15:21
 */
public class MapGraph {

    private int n;//顶点数
    private LinkedList<Edge>[] items;

    public MapGraph(int n) {
        this.items = new LinkedList[n];
        this.n = n;
        for (int i = 0; i < n; i++) {
            this.items[i] = new LinkedList<>();
        }
    }

    public void addEdge(int sid, int tid, int w) {
        this.items[sid].add(new Edge(sid, tid, w));
    }

    private class Edge {
        public int sid;
        public int tid;
        public int w;

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    private class Vertex {
        private int id;//顶点号
        private int dist;//从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        char a = 16;
        int i = 8 << 1;
        System.out.println(a);
        System.out.println(1 << 15);
        System.out.println(a | (1 << 15));
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        addTwoNumbers(l1, l2);
    }

    /**
     * l1:2->4->3
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int p = (l1.val + l2.val) % 10;
        int q = (l1.val + l2.val) / 10;
        ListNode result = new ListNode(p);
        l1 = l1.next;
        l2 = l2.next;
        ListNode tmp = result;
        while (l1 != null || l2 != null) {
            if(l2==null){
                p = (l1.val + q) % 10;
                q = (l1.val + q) / 10;
                tmp.next = new ListNode(p);
                tmp = tmp.next;
                l1 = l1.next;
                continue;
            }
            if(l1==null){
                p = (l2.val + q) % 10;
                q = (l2.val + q) / 10;
                tmp.next = new ListNode(p);
                tmp = tmp.next;
                l2 = l2.next;
                continue;
            }
            p = (l1.val + l2.val + q) % 10;
            q = (l1.val + l2.val + q) / 10;
            tmp.next = new ListNode(p);
            tmp = tmp.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        if (q != 0)
            tmp.next = new ListNode(q);
        return result;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
