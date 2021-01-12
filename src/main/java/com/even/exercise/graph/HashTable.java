package com.even.exercise.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Project Name: ai
 * Des:
 * 散列表，用于记录图中关注与被关注的关系
 * Created by Even on 2019/4/9 16:58
 */
public class HashTable<T, V> {

    private Node[] nodes;
    private Node[] newNodes;

    private int n = 16;

    private double factor = 0.7;

    private boolean moveData = false;

    private int p = 0;

    private int count = 0;//记录散列表的已存储的数目

    private int k = 0;//顶点数

    public HashTable() {
        nodes = new Node[n];
    }

    /**
     * 插入数据
     *
     * @param key
     * @param value
     */
    public void put(T key, V value) {
        if (((double) (count / n)) >= factor)
            resize();
        if (moveData) {
            if (move()) {//移动一次，如果移动未完成，则应用下面的代码块
                int index = key.hashCode() % (n / 2);
                int newIndex = key.hashCode() % n;
                if (newNodes[newIndex] == null) {
                    if (nodes[index] == null) {
                        count++;
                        Node<T> newNode = new Node<>(key);
                        newNode.focusNodes = new Node<>(value);
                        newNodes[newIndex] = newNode;
                        k++;
                        return;
                    } else {
                        count++;
                        newNodes[newIndex] = nodes[index];
                        nodes[index] = null;
                    }
                }
                //添加数据
                Node targetNode = newNodes[newIndex];
                while (targetNode.next != null) {
                    if (targetNode.nameHash == key.hashCode() && targetNode.name == key) {
                        Node ccNode = targetNode.focusNodes;
                        while (ccNode.next != null) {
                            if (ccNode.nameHash == value.hashCode() && ccNode.name == value) {
                                return;//已存在就不添加
                            }
                            ccNode = ccNode.next;
                        }
                        k++;
                        ccNode.next = new Node<>(value);
                        return;//已经添加
                    }
                    targetNode = targetNode.next;
                }
                if (targetNode.nameHash == key.hashCode() && targetNode.name == key) {
                    Node ccNode = targetNode.focusNodes;
                    while (ccNode.next != null) {
                        if (ccNode.nameHash == value.hashCode() && ccNode.name == value) {
                            return;
                        }
                        ccNode = ccNode.next;
                    }
                    k++;
                    ccNode.next = new Node<>(value);
                    return;//已经添加
                }
                k++;
                targetNode.next = new Node<>(key);
                targetNode.next.focusNodes = new Node<>(value);
                return;
            }
        }
        int index = key.hashCode() % n;
        if (nodes[index] == null) {
            count++;
            nodes[index] = new Node<>(key);
            nodes[index].focusNodes = new Node<>(value);
            k++;
        } else {
            Node targetNode = nodes[index];
            while (targetNode.next != null) {
                if (targetNode.nameHash == key.hashCode() && targetNode.name == key) {
                    Node ccNode = targetNode.focusNodes;
                    while (ccNode.next != null) {
                        if (ccNode.nameHash == value.hashCode() && ccNode.name == value) {
                            return;
                        }
                        ccNode = ccNode.next;
                    }
                    k++;
                    ccNode.next = new Node<>(value);
                    return;//已经添加
                }
                targetNode = targetNode.next;
            }
            if (targetNode.nameHash == key.hashCode() && targetNode.name == key) {
                Node ccNode = targetNode.focusNodes;
                while (ccNode.next != null) {
                    if (ccNode.nameHash == value.hashCode() && ccNode.name == value) {
                        return;
                    }
                    ccNode = ccNode.next;
                }
                k++;
                ccNode.next = new Node<>(value);
                return;//已经添加
            }
            k++;
            targetNode.next = new Node<>(key);
            targetNode.next.focusNodes = new Node<>(value);
        }
    }

    /**
     * 当数组已满时，需要重新申请空间
     */
    private void resize() {
        newNodes = new Node[2 * n];
        n = 2 * n;
        moveData = true;
        count = 0;
    }

    private boolean move() {
        for (int i = p; i < nodes.length; i++) {
            p = i;
            if (nodes[i] != null) {
                int index = nodes[i].name.hashCode() % n;
                if (newNodes[index] == null) {
                    newNodes[index] = nodes[i];
                } else {
                    Node targetNode = newNodes[index];
                    while (targetNode.next != null) {
                        targetNode = targetNode.next;
                    }
                    targetNode.next = nodes[i];
                }
                nodes[i] = null;//置空
                count++;
                break;
            }
        }
        if (p == nodes.length - 1) {
            moveData = false;//证明已把所有旧的数据移到新扩充的数组中了
            nodes = newNodes;
        }
        return moveData;
    }

    /**
     * 判断key内是否含有value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean exist(T key, V value) {
        int nameHash = key.hashCode();
        int index = nameHash % n;
        Node p;
        if (moveData) {
            p = newNodes[index];
            if (p != null) {
                while (p != null) {
                    if (p.name == key && p.nameHash == nameHash) {
                        Node ccNode = p.focusNodes;
                        while (ccNode != null) {
                            if (ccNode.name == value && ccNode.nameHash == value.hashCode()) {
                                return true;
                            }
                            ccNode = ccNode.next;
                        }
                        return false;
                    }
                    p = p.next;
                }
                return false;
            } else {
                index = nameHash % (n / 2);
            }
        }
        p = nodes[index];
        while (p != null) {
            if (p.name == key && p.nameHash == nameHash) {
                Node ccNode = p.focusNodes;
                while (ccNode != null) {
                    if (ccNode.name == value && ccNode.nameHash == value.hashCode()) {
                        return true;
                    }
                    ccNode = ccNode.next;
                }
                return false;
            }
            p = p.next;
        }
        return false;
    }


    /**
     * 删除key内的value
     *
     * @param key
     * @param value
     */
    public void delete(T key, V value) {
        int nameHash = key.hashCode();
        int index = nameHash % n;
        Node p;
        if (moveData) {
            p = newNodes[index];
            if (p != null) {
                while (p != null) {
                    if (p.name == key && p.nameHash == nameHash) {
                        Node ccNode = p.focusNodes;
                        if (ccNode.name == value && ccNode.nameHash == value.hashCode()) {
                            p.focusNodes = p.focusNodes.next;
                            k--;
                            return;
                        }

                        while (ccNode.next != null) {
                            if (ccNode.next.name == value && ccNode.next.nameHash == value.hashCode()) {
                                ccNode.next = ccNode.next.next;
                                k--;
                                return;
                            }
                            ccNode = ccNode.next;
                        }
                        return;
                    }
                    p = p.next;
                }
                return;
            } else {
                index = nameHash % (n / 2);
            }
        }
        p = nodes[index];
        while (p != null) {
            if (p.name == key && p.nameHash == nameHash) {
                Node ccNode = p.focusNodes;
                if (ccNode.name == value && ccNode.nameHash == value.hashCode()) {
                    p.focusNodes = p.focusNodes.next;
                    k--;
                    return;
                }
                while (ccNode != null) {
                    if (ccNode.next.name == value && ccNode.next.nameHash == value.hashCode()) {
                        ccNode.next = ccNode.next.next;
                        k--;
                        return;
                    }
                    ccNode = ccNode.next;
                }
                return;
            }
            p = p.next;
        }
    }

    /**
     * 获取i度关注者，一度就是好友，二度就是好友的好友，以此类推。使用广度优先搜索（BFS）
     *
     * @param i
     * @return
     */
    public List<String> listKFocus(String key, int i) {
        boolean[] visited = new boolean[k];
        String[] prev = new String[k];
        Queue<String> queue = new LinkedList<>();
        Queue<String> queue1 = new LinkedList<>();
        queue.add(key);
        visited[key.hashCode() % k] = true;
        List<String> peopleList = new ArrayList<>();
        while (i > 1) {
            while (!queue.isEmpty()) {
                String w = queue.poll();
                Node node = get(w);
                while (node != null) {
                    String q = (String) node.name;
                    int tmpIndex = q.hashCode() % k;
                    if (!visited[tmpIndex]) {
                        prev[tmpIndex] = w;
                        visited[tmpIndex] = true;
                        queue1.add(q);
                    }
                    node = node.next;
                }
            }
            while (!queue1.isEmpty()) {
                queue.add(queue1.poll());
            }
            i--;
        }
        while (!queue.isEmpty()) {
            String w = queue.poll();
            Node node = get(w);
            while (node != null) {
                String q = (String) node.name;
                int tmpIndex = q.hashCode() % k;
                if (!visited[tmpIndex]) {
                    peopleList.add(q);
                }
                node = node.next;
            }
        }
        return peopleList;
    }

    /**
     * 获取key所关注的所有顶点
     *
     * @param key
     * @return
     */
    public Node get(String key) {
        int index = key.hashCode() % n;
        Node target;
        if (moveData) {
            target = newNodes[index];
            if (target != null) {
                while (target != null) {
                    if (target.nameHash == key.hashCode() && target.name == key) {
                        return target.focusNodes;
                    }
                    target = target.next;
                }
                return null;
            } else {
                index = key.hashCode() % (n / 2);
            }
        }
        target = nodes[index];
        while (target != null) {
            if (target.nameHash == key.hashCode() && target.name == key) {
                return target.focusNodes;
            }
            target = target.next;
        }
        return null;
    }

    private static class Node<T> {
        private T name;
        private Node next;
        private int nameHash;
        private Node focusNodes;//此处可以改成用跳表，以便分页查询

        public Node(T name) {
            this.name = name;
            this.nameHash = name.hashCode();
        }
    }

}
