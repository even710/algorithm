package com.even.algorithm;

/**
 * Project Name: ai
 * Des: 字典树的构造和查找
 * Created by Even on 2019/4/1 14:24
 */
public class Trie {

    private class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndNode = false;

        public TrieNode(char data) {
            this.data = data;
        }

        private TrieNode root = new TrieNode('/');

        private void insert(char[] text) {
            TrieNode p = root;
            for (int i = 0; i < text.length; i++) {
                int index = text[i] - 'a';
                if (p.children[index] == null) {
                    p.children[index] = new TrieNode(text[i]);
                }
                p = p.children[index];
            }
            p.isEndNode = true;
        }

        private boolean find(char[] pattern) {
            TrieNode p = root;
            for (char aPattern : pattern) {
                int index = aPattern - 'a';
                if (p.children[index] == null)
                    return false;//没有匹配的字符串
            }
            if (p.isEndNode)
                return true;
            else {
                System.out.println("不能完全匹配");
                return false;//不能完全匹配
            }
        }
    }
}
