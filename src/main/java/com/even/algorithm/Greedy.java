package com.even.algorithm;

/**
 * Project Name: ai
 * Des:贪心算法
 * 经典应用：霍夫曼编码，Prim和Kruskal最小生成树算法，Dijkstra单源最短路径算法
 * 用贪心算法不一定是最优解
 * Created by Even on 2019/4/1 16:04
 */
public class Greedy {
    /**
     * 分糖果，有m个糖果和n个小孩，m<n，每个糖果的大小分别为s1,s2,s3...s_m，每个孩子对糖果的需求不一样，只有糖果大小大于等于
     * 孩子对糖果的需求时，孩子才得到满足，n个孩子的需求分别为g1,g2,g3...g_n。
     * 如何分配糖果，能尽可能满足最多数量的孩子?
     * 从剩下的孩子中找出需求最小的，然后从剩下的糖果中找出满足他的最小的糖果。可能会出现一个糖果太小，即使是最小需求的孩子
     * 一颗也满足不了，需要两颗0。因此需要规定每个小孩最多只能有一颗
     */
    private void greedy1(){

    }

    /**
     * 假设我们有 1 元、2 元、5 元、10 元、20 元、50元、100元这些面额的纸币，它们的张数分别是 c1、c2、c5、c10、c20、c50、c100。
     * 我们现在要用这些钱来支付 K 元，最少要用多少张纸币呢？
     * 先用面值大的，再用面值小的。
     */
    private void greedy2(){

    }

    /**
     * 假设我们有 n 个区间，区间的起始端点和结束端点分别是 [l1, r1]，[l2, r2]，[l3, r3]，……，[ln,rn]。
     * 我们从这 n 个区间中选出一部分区间，这部分区间满足两两不相交（端点相交的情况不算相交），最多能选出多少个区间呢？
     * 解：假设n个区间最左端点是lmin，最右端点是rmax。选择几个不相同的区间，从左到右将[lmin,rmax]覆盖，左端点与前面已
     * 覆盖的区间不重合而右端点又尽量小。
     */
    private void greedy3(){

    }
}