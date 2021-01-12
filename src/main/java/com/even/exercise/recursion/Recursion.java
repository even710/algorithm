package com.even.exercise.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * Project Name: ai
 * Des: 递归需要满足三个条件
 * 1，一个问题的解可以分解为几个子问题的解
 * 2，这个问题与分解之后的子问题，除了数据规模不同，求解思路完全一样
 * 3，存在递归终止条件
 * <p>
 * 注意事项，
 * 1，因为函数调用时会使用栈来保存临时变量，每调用一个函数都会将临时变量封闭为栈帧压入内存栈，如果递归求解的数据规模过大，
 * 调用层次很深，就会有堆栈溢出的风险
 * 2，重复数据计算
 * 3，死循环
 *
 * 技巧：写递推公式，求终止条件
 * Created by Even on 2019/4/3 16:02
 */
public class Recursion {
    private int depth = 0;//防止堆栈溢出，当最大深度比较小时，就可以使用

    Map map = new HashMap();//防止重复计算，如果已经计算，则记录下来

    private int sum(int a) throws Exception {
        depth++;
        if (depth > 1000) throw new Exception();
        if (a == 1) return 1;
        if (map.containsKey(a))
            return (int) map.get(a);
        int result = sum(a - 1) + 1;
        map.put(a, result);
        return result;
    }
}
