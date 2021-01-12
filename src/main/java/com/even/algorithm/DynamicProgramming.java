package com.even.algorithm;

/**
 * Project Name: ai
 * Des: 动态规划
 * “一个模型三个特征”
 * 一个模型：“多阶段决策最优解模型”。解决问题的过程是，需要经历多个决策阶段，每个决策阶段都对应一组状态。
 * <p>
 * 最优子结构：通过子问题的最优解求解得问题的最优解。在动态规划中就是，通过前面阶段的状态推导后面阶段的状态。
 * <p>
 * 无后效性：1，在推导后面阶段的状态的时候，我们只关心前面阶段的状态值，而不状态这个状态是怎么一步一步推导出来的。
 * 2，某阶段的状态一旦确定，就不受之后阶段的决策影响。
 * <p>
 * 重复子问题：不同的决策序列，到达相同的阶段时，会出现重复的状态。
 * <p>
 * 动态规划的解题思路：
 * 状态转移表法：回溯算法实现 - 定义状态 - 画递归树 - 找重复子问题 - 画状态转移表 - 根据递推关系填表 - 将填表过程翻译成代码
 * 状态转移方程法：找最优子结构 - 写状态转移方程 - 将状态转移方程翻译成代代码
 * Created by Even on 2019/4/2 9:41
 */
public class DynamicProgramming {
    // 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
    private int maxW = Integer.MIN_VALUE; // 结果放到 maxW 中
    private int[] weight = {2, 2, 4, 6, 3};  // 物品重量
    private int n = 5; // 物品个数
    private int w = 9; // 背包承受的最大重量

    public void f(int i, int cw) { // 调用 f(0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了，i==n 表示物品都考察完了
            if (cw > maxW) maxW = cw;
            return;
        }
        f(i + 1, cw); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            f(i + 1, cw + weight[i]); // 选择装第 i 个物品
        }
    }


    private boolean[][] mem = new boolean[5][10]; // 备忘录，默认值 false

    /**
     * 回溯求解背包问题优化1
     *
     * @param i
     * @param cw
     */
    public void ff(int i, int cw) { // 调用 f(0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了，i==n 表示物品都考察完了
            if (cw > maxW) maxW = cw;
            return;
        }
        if (mem[i][cw]) return; // 重复状态
        mem[i][cw] = true; // 记录 (i, cw) 这个状态
        ff(i + 1, cw); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            ff(i + 1, cw + weight[i]); // 选择装第 i 个物品
        }
    }


    //weight: 物品重量，n: 物品个数，w: 背包可承载重量

    /**
     * 动态规则
     *
     * @param weight
     * @param n
     * @param w
     * @return
     */
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w + 1]; // 默认值 false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][weight[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) {// 把第 i 个物品放入背包
                if (states[i - 1][j] == true) states[i][j + weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n - 1][i] == true) return i;
        }
        return 0;
    }

    /**
     * 动态规则优化，减少数组申请的数量，只需要w+1个数组就行
     *
     * @param items
     * @param n
     * @param w
     * @return
     */
    public static int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w + 1]; // 默认值 false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[items[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = w - items[i]; j >= 0; --j) {// 把第 i 个物品放入背包
                if (states[j] == true) states[j + items[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i] == true) return i;
        }
        return 0;
    }

    private int maxV = Integer.MIN_VALUE; // 结果放到 maxV 中
    private int[] items = {2, 2, 4, 6, 3};  // 物品的重量
    private int[] value = {3, 4, 8, 9, 6}; // 物品的价值

    /**
     * 对于一组不同重量，不同价值，不可分割的物品，怎么存放可以在不超过背包最大裁量的前提下，使价值最大
     *
     * @param i
     * @param cw
     * @param cv
     */
    public void fff(int i, int cw, int cv) { // 调用 f(0, 0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了，i==n 表示物品都考察完了
            if (cv > maxV) maxV = cv;
            return;
        }
        fff(i + 1, cw, cv); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            fff(i + 1, cw + weight[i], cv + value[i]); // 选择装第 i 个物品
        }
    }

    /**
     * 动态规则求解背包总价值最大问题
     *
     * @param weight
     * @param value
     * @param n
     * @param w
     * @return
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化 states
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        states[0][weight[0]] = value[0];
        for (int i = 1; i < n; ++i) { // 动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第 i 个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }
        return maxvalue;
    }


    /**
     * 薅羊毛代码
     *
     * @param items
     * @param n
     * @param w
     */
    // items 商品价格，n 商品个数, w 表示满减条件，比如 200
    public static void double11advance(int[] items, int n, int w) {
        boolean[][] states = new boolean[n][3 * w + 1];// 超过 3 倍就没有薅羊毛的价值了
        states[0][0] = true;  // 第一行的数据要特殊处理
        states[0][items[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = 0; j <= 3 * w; ++j) {// 不购买第 i 个商品
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= 3 * w - items[i]; ++j) {// 购买第 i 个商品
                if (states[i - 1][j] == true) states[i][j + items[i]] = true;
            }
        }

        int j;
        for (j = w; j < 3 * w + 1; ++j) {
            if (states[n - 1][j] == true) break; // 输出结果大于等于 w 的最小值
        }
        if (j == 3 * w + 1) return; // 没有可行解
        for (int i = n - 1; i >= 1; --i) { // i 表示二维数组中的行，j 表示列
            if (j - items[i] >= 0 && states[i - 1][j - items[i]] == true) {
                System.out.print(items[i] + " "); // 购买这个商品
                j = j - items[i];
            } // else 没有购买这个商品，j 不变。
        }
        if (j != 0) System.out.print(items[0]);
    }


}
