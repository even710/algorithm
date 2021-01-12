package com.even.exercise.graph;

import java.util.List;

/**
 * Project Name: ai
 * Des:图的表示
 * 概念：
 * 顶点，图中的元素
 * 边，顶点与顶点之间的连线
 * 度，与顶点相连的边数
 * 出度，有向图中，表示以顶点为起点的边数
 * 入度，有向图中，表示指向该顶点的边数
 * 权重，带权图中，每一条边都有一个权重，用于表示两个顶点间的关系强度
 * 下面是有向图的实现
 * <p>
 * 用散列表+两个单链表
 * 一个单链表用于解决散列冲突，另一个单链表用来存储关注的人（可用跳表代替）
 * Created by Even on 2019/4/9 16:14
 */
public class Graph {

    private HashTable<String, String> focusHash = new HashTable<>();
    private HashTable<String, String> fansHash = new HashTable<>();

    /**
     * a关注b
     *
     * @param a
     * @param b
     */
    private void insert(String a, String b) {
        focusHash.put(a, b);
        fansHash.put(b, a);
    }

    /**
     * 判断a是否关注了b
     *
     * @param a
     * @param b
     * @return
     */
    private boolean isFocus(String a, String b) {
        return focusHash.exist(a, b);
    }

    /**
     * 判断a是否是b的粉丝
     *
     * @param a
     * @param b
     * @return
     */
    private boolean isFans(String a, String b) {
        return fansHash.exist(b, a);
    }

    private List listKFocus(String a, int i) {
        return focusHash.listKFocus(a, i);
    }

    /**
     * 用户a取消关注b
     *
     * @param a
     * @param b
     */
    private void deleteFocus(String a, String b) {
        focusHash.delete(a, b);
        fansHash.delete(b, a);
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.insert("a", "b");
        graph.insert("tom", "Com");
        graph.insert("bom", "bom");
        graph.insert("com", "com");
        graph.insert("com", "tom");
        graph.insert("Com", "hello");
        graph.insert("eom", "eom");
        graph.insert("dom", "dom");
        graph.insert("fom", "fom");
        System.out.println(graph.listKFocus("com", 3));

        String[] a = new String[10];
    }
}
