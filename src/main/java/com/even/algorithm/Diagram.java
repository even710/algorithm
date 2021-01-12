package com.even.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Project Name: ai
 * Des: 非线性结构——图
 * 概念：
 * 顶点，图中的元素
 * 边，顶点与顶点之间的连线
 * 度，与顶点相连的边数
 * 出度，有向图中，表示以顶点为起点的边数
 * 入度，有向图中，表示指向该顶点的边数
 * 权重，带权图中，每一条边都有一个权重，用于表示两个顶点间的关系强度
 * <p>
 * 稀疏图：顶点很多，但是边很少。
 * 存储方法：
 * 邻接矩阵，A[i][j]=1表示i节点与j节点连接。缺点，浪费空间；优点，简单直接，易于将图的运算转换成矩阵的运算。
 * 领接表，类似散列表，每个元素对应的链表记录了它指向的元素
 * 逆领接表，每个元素对应的链表记录了指向它的元素
 * Created by Even on 2019/3/28 8:57
 */
public class Diagram {

    private int v;//顶点数
    private LinkedList<Integer> adj[];

    /**
     * 建一个无向图
     *
     * @param v
     */
    private Diagram(int v) {
        this.v = v;

        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 给顶点s和t添加一条边
     *
     * @param s
     * @param t
     */
    private void addEdge(int s, int t) {
        if (s >= adj.length || t >= adj.length) return;
        if (adj[s].size() > t && adj[s].get(t) != null)
            System.out.println("已添加");
        else {
            adj[s].add(t);
            adj[t].add(s);
        }
    }


    /**
     * 广度优先搜索，查找s到t的路径，先查找离起始点近的，再次近的，以次类推
     *
     * 需要三个辅助变量：visited、queue、prev。
     * visited是用来记录已经被访问的顶点，用来避免顶点被重复访问。如果顶点q被访问，那么相应的visited[q]会被设置为true
     * queue是一个队列，用于存储已经被访问、但相连的顶点还没有被访问的顶点。因为广度优先搜索是逐层访问的，也就是说，我们只有把第k层的顶点都访问完成之后，才
     * 可以访问第k+1层的顶点，当我们访问到第k层的顶点的时候，我们需要把第k层的顶点记录下来，稍后才能通过第k层的顶点来找第
     * k+1层的顶点。
     * prev用来记录搜索路径，当我们从顶点s开始，广度优先搜索到顶点t后，prev元素存储的是顶点 是从哪个前驱顶点遍历过来的。
     */
    private void bfs(int s, int t) {
        if (s == t) return;
        Queue<Integer> queue = new LinkedList<>();//记录需要访问的顶点
        queue.add(s);//入队

        boolean[] visited = new boolean[v];//记录访问情况
        int[] prev = new int[v];//记录指向该顶点的顶点
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        visited[s] = true;
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        System.out.println("");
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + "->");
    }


    boolean found = false;//表示是否找到

    /**
     * 深度优先搜索，每到岔路口，选择一条路径，走不通时，再返回上一个岔路口选择。
     *
     * @param s
     * @param t
     */
    private void dfs(int s, int t) {
        int[] prev = new int[v];//记录指向该顶点的顶点
        boolean[] visited = new boolean[v];//记录已访问过顶点
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);//打开路径
    }

    private void recurDfs(int s, int t, boolean[] visited, int[] prev) {
        if (found) return;
        visited[s] = true;
        if (s == t) {
            found = true;
            return;
        }

        for (int i = 0; i < adj[s].size(); i++) {
            if (found)
                return;
            int q = adj[s].get(i);
            if (!visited[q]) {
                prev[q] = s;
                recurDfs(q, t, visited, prev);
            }
        }
    }

    public static void main(String[] args) {
        Diagram diagram = new Diagram(15);
        diagram.addEdge(0, 1);
        diagram.addEdge(0, 3);
        diagram.addEdge(1, 4);
        diagram.addEdge(3, 4);
        diagram.addEdge(1, 2);
        diagram.addEdge(4, 5);
        diagram.addEdge(2, 5);
        diagram.addEdge(4, 6);
        diagram.addEdge(5, 7);
        diagram.addEdge(6, 7);
        diagram.bfs(0, 6);
        diagram.dfs(0, 6);
    }
}
