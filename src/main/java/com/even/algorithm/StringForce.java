package com.even.algorithm;

/**
 * Project Name: ai
 * Des: 字符串匹配
 * 概念：
 * 字符串A中查找字符串B
 * 主串：字符串A
 * 模式串：字符串B
 * <p>
 * BF,Brute Force暴力匹配法，对于字符串长度小的情况，可以使用。n长度的主串，和m长度的模式串，主串总共有n-m+1个m长度的字符串
 * RK算法，把主串中n-m+1个m长度的字符串转成哈希值，再让模式串比较n-m+1次。数字比较速度较快。
 * BM算法，通过坏字符和好后缀规则，实现字符串一次多位滑动
 * KMP算法，通过好前缀规则，实现一个next数组来实现一次多位滑动
 * Created by Even on 2019/3/29 15:36
 */
public class StringForce {

    /**
     * BF算法
     *
     * @param a
     * @param b
     */
    private void bf(String a, String b) {
        int m = b.length();
        int n = a.length();
        if (n < m) return;
        for (int i = 0; i < n - m + 1; i++) {
            boolean same = true;
            int z = 0;
            for (int j = i; j < i + m; j++) {
                if (a.charAt(j) != b.charAt(z)) {
                    same = false;
                    break;
                }
                z++;
            }
            if (same) {
                System.out.println("same");
                return;
            }
        }
        System.out.println("no same");
    }

    /**
     * RK算法求字符串匹配，注意生成的hash值要在整数范围，可以优化哈希函数，使生成的哈希值在整数范围内，如果出现冲突，就比较直接比较字符串
     *
     * @param a
     * @param b
     */
    private void rk(String a, String b) {
        int m = b.length();
        int n = a.length();
        if (n < m) return;
        int hashNum = hash(b, m);
        for (int i = 0; i < n - m + 1; i++) {
            if (hash(a.substring(i, i + m), m) == hashNum) {
                System.out.println("same");
                return;
            }
        }
        System.out.println("no same");
    }

    private int hash(String c, int m) {

        int result = 0;
        int tmp = 1;
        int beginIndex = (int) ("a".charAt(0));
        for (int i = 1; i < m; i++) {
            tmp *= 26;
            result += ((int) (c.charAt(m - i - 1)) - beginIndex) * tmp;
        }
        result += ((int) (c.charAt(m - 1)) - beginIndex);
        return result;
    }


    /**
     * 下面是BM算法的实现
     *
     * @param args
     */
    private final int SIZE = 255;
    private int[] bc;

    private void bc(char[] b) {
        bc = new int[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            bc[i] = -1;
        }
        for (int i = 0; i < b.length; i++) {
            bc[(int) b[i]] = i;
        }
    }

    /**
     * bm算法，从后往前比较，出现不相等，就往后滑动，坏字符规则
     *
     * @param a
     * @param n
     * @param b
     * @param m
     * @return
     */
    private int bm(char[] a, int n, char[] b, int m) {
        int i = 0;
        bc(b);
        while (i <= n - m) {
            int j = m - 1;
            for (; j >= 0; --j) if (a[i + j] != b[j]) break;
            if (j < 0) return i;
            i = i + (j - bc[(int) a[i + j]]);
        }
        return -1;
    }

    /**
     * BM算法，好后缀规则，求suffix和prefix数组
     */
// b 表示模式串，m 表示长度，suffix，prefix 数组事先申请好了
    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) { // 初始化
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; ++i) { // b[0, i]
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m - 1 - k]) { // 与 b[0, m-1] 求公共后缀子串
                --j;
                ++k;
                suffix[k] = j + 1; //j+1 表示公共后缀子串在 b[0, i] 中的起始下标
            }
            if (j == -1) prefix[k] = true; // 如果公共后缀子串也是模式串的前缀子串
        }
    }

    /**
     * bm性能的最终版本，结合了坏字符和好后缀规则
     *
     * @param a
     * @param n
     * @param b
     * @param m
     * @return
     */
    private int bm1(char[] a, int n, char[] b, int m) {
        bc(b);
        int[] suffix = new int[m];//以好后缀长度为下标，记录了与好后缀匹配的起始下标
        boolean[] prefix = new boolean[m];//以好后缀长度为下标，记录了好后缀是否能匹配模式串的前缀子串
        generateGS(b, m, suffix, prefix);
        int i = 0;
        while (i <= n - m) {
            int move = 0;
            int j = m - 1;
            for (; j >= 0; j--) if (a[i + j] != b[j]) break;
            if (j < 0) return i;//表示已找到匹配的下标
            int x = j - bc[(int) a[i + j]];//坏字符规则需要移动的位数
            int y = 0;
            if (j < m - 1) {//表示有好后缀
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j;//好后缀的长度
        if (suffix[k] != -1) return j - suffix[k] + 1;
        for (int r = j + 2; r <= m - 1; r++) {
            if (prefix[m - r])//从长到短判断，找出最长的与好后缀的后缀子串匹配的前缀子串的下标
                return r;
        }
        return m;
    }


    // a, b 分别是主串和模式串；n, m 分别是主串和模式串的长度。
    public static int kmp(char[] a, int n, char[] b, int m) {
        int[] next = getNexts(b, m);
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while (j > 0 && a[i] != b[j]) { // 一直找到 a[i] 和 b[j]
                j = next[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                ++j;
            }
            if (j == m) { // 找到匹配模式串的了
                return i - m + 1;
            }
        }
        return -1;
    }


    private static int[] getNexts(char[] b, int m) {
        int[] next = new int[m];

        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; i++) {
            while (k != -1 && b[k + 1] != b[i])
                k = next[k];
            if (b[k + 1] == b[i])
                k++;
            next[i] = k;
        }
        return next;
    }

    public static void main(String[] args) {
        StringForce stringForce = new StringForce();
        stringForce.bf("abcdsdcsa", "abcdsdc");
        stringForce.hash("adfas", 5);
        stringForce.rk("abcdsdcsa", "abcdsdc");
        System.out.println(stringForce.bm("abcdsdcsa".toCharArray(), "abcdsdcsa".length(), "bcdsdc".toCharArray(), "bcdsdc".length()));
        System.out.println(stringForce.bm1("abcdsdcsa".toCharArray(), "abcdsdcsa".length(), "bcdsdc".toCharArray(), "bcdsdc".length()));
        System.out.println(stringForce.kmp("abcdsbcdsa".toCharArray(), "abcdsbcdsa".length(), "bcdbcd".toCharArray(), "bcdbcd".length()));

    }
}
