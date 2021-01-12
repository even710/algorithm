package com.even.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/1 14:34
 */
public class AC {
    private class ACTrieNode {
        public char data;
        public ACTrieNode[] children = new ACTrieNode[26];
        public boolean isEndChar = false;
        public int length = -1;//当isEndChar等于true时，记录模式串的长度
        public ACTrieNode fail;

        public ACTrieNode(char data) {
            this.data = data;
        }
    }

    ACTrieNode root = new ACTrieNode('/');

    private void insert(char[] text) {
        ACTrieNode p = root;
        for (int i = 0; i < text.length; i++) {
            int index = text[i] - 'a';
            if (p.children[index] == null)
                p.children[index] = new ACTrieNode(text[i]);
            p = p.children[index];
        }
        p.isEndChar = true;
        p.length = text.length;
        root = p;
    }

    /**
     * 一个节点的失败指针可以通过递归求得之前节点的失败指针来得到
     */
    private void buildFailePointer() {
        Queue<ACTrieNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            ACTrieNode p = queue.remove();//要设置失败指针节点的上一个节点
            for (int i = 0; i < 26; i++) {
                ACTrieNode pc = p.children[i];//需要设置失败指针的节点
                if (pc == null) continue;
                if (p == root) {
                    pc.fail = root;
                } else {
                    ACTrieNode q = p.fail;//上一个节点的失败指针
                    while (q != null) {
                        ACTrieNode qc = q.children[pc.data - 'a'];//上一个节点的失败指针的下一个下标为pc.data-'a'的子节点
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null)
                        pc.fail = root;
                }
                queue.add(pc);
            }
        }
    }

    /**
     * 通过画图可以理解下面代码
     * @param text
     */
    public void match(char[] text) { // text 是主串
        int n = text.length;
        ACTrieNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) p = root; // 如果没有匹配的，从 root 开始重新匹配
            ACTrieNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println(" 匹配起始下标 " + pos + "; 长度 " + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }


    public static void main(String[] args) {
        AC ac = new AC();
        ac.insert("gasdfga".toCharArray());
        ac.insert("qwettqwe".toCharArray());
        ac.insert("asdfc".toCharArray());
    }
}
