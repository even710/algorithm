package com.even.algorithm;

import java.text.DecimalFormat;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/3/20 9:15
 */
public class SortAlgorithm {

    /**
     * 冒泡排序，前后两两比较，每一轮会有一个数字到达正确位置，执行n次后，排序完成。
     *
     * @param a 要排序的数组
     */
    private void bubbleSort(int[] a) {
        if (a.length < 1) return;

        /*执行a.length次，完成排序*/
        for (int j = 0; j < a.length; ++j) {
            boolean status = true;
            /*遍历一次，会有一个数字到达正确位置*/
            for (int i = 0; i < a.length - j - 1; ++i) {
                if (a[i] > a[i + 1]) {
                    int temporary = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temporary;
                    status = false;
                }
            }
            /*当没有触发交换时，代表已经排序完成*/
            if (status)
                break;
        }
    }

    /**
     * 插入排序，把数组分成两部分，左边为已排序，右边为未排序。从右边拿首个数字与左边已排序部分比较，从尾位开始对比，依次类推，执行n次。
     *
     * @param a 要排序的数组
     */
    private void insertionSorting(int[] a) {
        if (a.length < 1) return;
        for (int i = 0; i < a.length; ++i) {
            /*要比较的值*/
            int temporary = a[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                /*i之前的是已排序的，比较已排序的尾位值和要比较的值，如果大于temporary，则交换*/
                if (temporary < a[j]) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            /*排序完后，把temporary存入已排序中*/
            a[j + 1] = temporary;
        }
    }

    /**
     * 选择排序，每一轮都选择数组中最小的值，然后放到最左边的尾位。循环n次。
     *
     * @param a 要排序的数组
     */
    private void selectionSort(int[] a) {
        if (a.length < 1) return;

        for (int i = 0; i < a.length - 1; ++i) {
            int minValue = a[i];
            int index = i;
            int j = i + 1;
            for (; j < a.length; ++j) {
                if (a[j] < minValue) {
                    minValue = a[j];
                    index = j;
                }
            }
            a[index] = a[i];
            a[i] = minValue;
        }
    }

    /**
     * 归并排序
     *
     * @param a
     * @param start
     * @param end
     */
    private void merge(int[] a, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            merge(a, start, mid);
            merge(a, mid + 1, end);
            mergeArray(a, start, mid, end);
        }
    }

    /**
     * 归并排序的合并数组部分，从左右两个数组的起始开始比较，从小到大存入到临时数组，再把临时数组替换掉原始数组的相应部分。
     *
     * @param a
     * @param start
     * @param mid
     * @param end
     */
    private void mergeArray(int[] a, int start, int mid, int end) {
        /*左边数组的下标*/
        int p = start;
        /*右边数组的下标*/
        int q = mid + 1;
        /*临时数组的下标*/
        int i = 0;
        int[] tmp = new int[end - start + 1];

        while (p <= mid && q <= end) {
            /*保证排序的稳定性，即相等时以左边数组为准*/
            if (a[p] <= a[q])
                tmp[i++] = a[p++];
            else
                tmp[i++] = a[q++];
        }

        while (p <= mid) {
            tmp[i++] = a[p++];
        }

        while (q <= end) {
            tmp[i++] = a[q++];
        }

        System.arraycopy(tmp, 0, a, start, tmp.length);
    }

    /**
     * 快速排序的分区方法
     *
     * @param a
     * @param p
     * @param r
     * @return
     */
    private int partition(int[] a, int p, int r) {
        int value = a[r];
        int i = p; //最终会指向分区点的下标
        int j = p;
        while (j <= r) {
            /*当a[j]<value时，将a[j]和a[i]的值互换*/
            if (a[j] <= value) {
                if (j > i) {
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
                /*当j为最后一个值时，调换后就跳出*/
                if (j == r) break;
                /*只要a[j]<选择的值，都需要自增*/
                ++i;
            }
            ++j;
        }
        return i;
    }

    /**
     * 快速排序，从数组中选择一个数，把小于这个数的值放到左边，把大于这个数的值放到右边，得到三个区间，左区间，选择值，右区间，然后再循环执行处理左区间和右区间。
     *
     * @param a
     * @param start
     * @param end
     */
    private void fastSort(int[] a, int start, int end) {
        if (start >= end) return;
        int partition = partition(a, start, end);
        fastSort(a, start, partition - 1);
        fastSort(a, partition + 1, end);
    }

    /**
     * 求第k大的数
     *
     * @param a
     * @param start
     * @param end
     * @param k
     * @return
     */
    private int getDataWithFastSort(int[] a, int start, int end, int k) {
        if (start >= end) return -1;
        int i = k;
        int partition = partition(a, start, end);
        fastSort(a, start, partition - 1);
            /*代表第k大的数在右区间*/
        if ((a.length - partition) >= k)
            getDataWithFastSort(a, partition + 1, end, i);
        else {
            i = k - (a.length - partition) - 1;
            getDataWithFastSort(a, start, partition - 1, i);
        }
        return a[a.length - k];
    }

    /**
     * 桶排序，适合处理外部存储的数据，遍历数据，把数据分成n个区间存入n个桶中，各个桶自带序号，然后针对每个桶，进行快速排序，最后把所有桶整合在一起
     *
     * @param a
     */
    private void bucketSort(int[] a, int start, int end, int z) {
        int[] b, c, d, e, f, h, i, j, k = new int[10];
        int bb, cc, dd, ee, ff, hh, ii, jj, kk = 0;
        int minValue = a[0];
        int maxValue = a[0];
        for (int x = 0; x < a.length; x++) {
            if (a[x] > maxValue)
                maxValue = a[x];
            if (a[x] < minValue)
                minValue = a[x];
        }
        System.out.println("最大值是：" + maxValue + " ,最小值是：" + minValue);


    }

    /**
     * 计数排序，订单金额从0到9999，建立数组C，每个元素表示某一金额出现的个数。然后再对数组C顺序求和，即把前一个元素的值加上当前元素的值。
     * 此时每一个元素的意思是，小于等于该金额的个数。再创建一个数组R，数组C中的元素值-1相当于R的下标，R的元素值相当于数组C的下标。
     *
     * @param a
     * @param start
     * @param end
     */
    private void countingSort(int[] a, int start, int end) {
        if (a.length <= 1) return;
        int maxValue = a[0];
        for (int x = 0; x < a.length; x++) {
            if (a[x] > maxValue)
                maxValue = a[x];
        }

        int[] c = new int[maxValue + 1];
        int[] r = new int[a.length];
        for (int anA : a) {
            c[anA]++;
        }
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i] + c[i - 1];
        }
        for (int anA : a) {
            r[c[anA] - 1] = anA;
            c[anA]--;
        }
        System.arraycopy(r, 0, a, 0, a.length);
    }

    /**
     * 基数排序，对于需要排序的数据，需要可以分割成独立的“位”来比较，而且位之间有递进关系，即高位的数据比较大时，会忽略低位。
     */
    private void redixSoft(int[] a) {

    }

    /**
     * 最简单的二分法查找，从一组不带重复值的有序数组中查找数据value的位置。时间复杂度O(log n)
     *
     * @param a
     * @param value
     * @return
     */
    private int bsearch(int[] a, int value) {
        int length = a.length;
        if (length <= 1) return -1;
        int low = 0;
        int high = length - 1;
        while (low <= high) {
//            int mid = (low + high) / 2;
//            int mid = low + (high - low) / 2;//为了防止low+high过大造成溢出，可以改写成(low + (high-low)/2)
            int mid = low + ((high - low) >> 1);//再度优化，位数向右移一位，相当于/2
            if (a[mid] == value)
                return mid;
            else if (a[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 二分法查找，递归版本
     *
     * @param a
     * @param low
     * @param high
     * @param value
     * @return
     */
    private int bsearch(int[] a, int low, int high, int value) {
        if (low > high) return -1;
        int mid = low + (high - low) / 2;

        if (a[mid] == value)
            return mid;
        else if (a[mid] > value)
            return bsearch(a, low, mid - 1, value);
        else
            return bsearch(a, mid + 1, high, value);
    }

    /**
     * 运用二分查找法，求一个数的平方根，对于这个数有两种情况，一种是大于1，一种是小于1。小于1时，数的平方根比数大。大于1时，数的平方根小于等于该数。
     *
     * @param value
     */
    private void squart(double value) {
        double low;
        double high;
        if (value >= 1) {
            low = 0;
            high = value;
        } else {
            low = value;
            high = 1;
        }
        System.out.println(squart(value, low, high));
    }

    private double squart(double value, double low, double high) {
//        if (low > high) high++;
        double mid = low + ((high - low) / 2);
        double tmp = mid * mid;
        if (Math.abs(tmp - value) < 0.000001)
            return Double.parseDouble(new DecimalFormat("0.000000").format(mid));
        if (tmp > value)
            return squart(value, low, mid);
        if (tmp < value)
            return squart(value, mid, high);
        return Double.parseDouble(new DecimalFormat("0.000000").format(mid));
    }

    /**
     * 查找第一个等于给定值的元素的位置
     *
     * @param a
     * @param value
     * @return
     */
    private int bsearchProblem1(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value)
                high = mid - 1;
            else if (a[mid] < value)
                low = mid + 1;
            else {
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个等于给定值的元素的位置
     *
     * @param a
     * @param value
     * @return
     */
    private int bsearchProblem2(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] > value)
                high = mid - 1;
            else if (a[mid] < value)
                low = mid + 1;
            else {
                if ((mid == a.length - 1) || (a[mid + 1] != value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 求第一个大于等于给定值的元素的位置
     *
     * @param a
     * @param value
     * @return
     */
    private int bsearchProblem3(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if (mid == 0 || (a[mid - 1] < value)) return mid;

                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 求最后一个小于等于给定值的元素的位置
     *
     * @param a
     * @param value
     * @return
     */
    private int bsearchProblem4(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] <= value) {
                if (mid == a.length - 1 || (a[mid + 1] > value)) return mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }


    /**
     * 链表加多级索引的结构，就是跳表。查找的时间复杂度是O(log n)，空间复杂度是O(n)
     */
    private void jumpTable() {

    }

    /**
     * 哈希表，哈希冲突解决方案——开放寻址法。ThreadLocalMap解决冲突的方案就是开放寻址法。
     * 数据量较小，装载因子小的时候，便于序列化，适合使用。因为是纯数组，所以对cpu缓存友好，但因为装载因子不能太大，所以会浪费很多内存。
     * <p>
     * 线性探测：插入操作时，如果出现哈希冲突，就线性往后寻空址，然后保存下来；查找操作时同理，如果出现哈希冲突，也会线性往后寻找，如果找到就返回，
     * 如果出现空址，就代表没有在哈希表中。
     * 二次探测：相对于线性探测，每次探测的步长是二次方。
     * 双重探测：经过第一个哈希函数后如果出现哈希冲突，那么就用第二个哈希函数，直至没有出现哈希冲突。
     * <p>
     * 另一种方案，链表法。哈希表每个槽位都对应一个链表，数据插入时，通过哈希函数计算到槽位，再插入相应的链表中，时间复杂度O(1)；查找删除的
     * 复杂度和对应链表的长度k有关——O(k)，如果均匀分布，n代表数据个数，m代表槽数，k=n/m。LinkedHashMap解决冲突的方案就是链表法，
     * 类似LRU 缓存淘汰策略的缓存系统，即链是一个双向链表，按照访问时间排序。
     * HashMap在1.8后，引入红黑树，当链表长度超过默认8时，会转换成红黑树。
     * <p>
     * LRU缓存淘汰策略，链表法解决冲突，同时再维护一个双向链表，解决插入元素顺序的问题。
     * <p>
     * <p>
     * 适合存储大对象，大数据量，比起开放寻址法，更灵活，可以用其他数据结构代替链表。
     * <p>
     * 要比较两个大容量数组相等的字符串，可以每哈希存储一个数组，再遍历查找另一个数组，看在哈希表是否存在。
     */
    private void hashTable() {

    }

    /**
     * 满二叉树表示所有除叶节点外的节点都有两个子节点。
     * 完全二叉树是指，叶子节点都在最底下两层，最后一层的叶子节点都靠左排列，并且除...
     *
     * @param n
     */
    private void binaryTree(int n) {

    }

    /**
     * 二叉查找树，要求左子节点要小于当前节点，右子节点要大于当前节点。
     * 下面是二叉查找树查找数据的代码。另外，针对重置数据的插入删除和查找，插入时，当存在相当的键值，则把这个键对应的对象存到节点的右子节点。
     * 查找和删除时，需要一直遍历到最底层，对给定键值一样的对象进行操作。
     * <p>
     * 时间复杂度，极端情况时间复杂度分别为O(n)和O(log n)
     * <p>
     * 二叉查找树与散列表的差别
     * 1，散列表的数据是无序存储的，要输出有序数据，需要先进行排序。而二叉查找树只需要使用中序遍历
     * 2，散列表扩容耗时很多，而且遇到散列冲突时，性能不稳定。正常的二叉查找树也不稳定，但是平衡二叉查找树性能稳定
     * 3，散列表的查找等操作时间复杂度是常量级的，但因为哈希冲突，可能这个常量会很大，不一定比O(log n)快
     * 4，散列表的构造比二叉查找树要复杂，需要考虑的东西很多。散列函数设计，冲突解决，扩容和缩容等。平衡二叉查找树只考虑平均问题
     * 5，散列表装载因子不能过大，不然会浪费一定的存储空间
     */
    private Node binarySearchTreeFind(int data) {
        Node p = tree;
        while (p != null) {
            if (data < p.data) p = p.left;
            else if (data > p.data) p = p.right;
            else return p;
        }
        return null;
    }

    private void binarySearchTreeInsert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }
        Node p = tree;

        while (p != null) {
            if (data > p.data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else {
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    /**
     * 二叉查找树删除操作，images文件夹有相关的图。删除操作的另一个方法就是把要删除的节点设置为deleted状态
     *
     * @param data
     */
    private void binarySearchTreeDelete(int data) {
        Node p = tree;//初始化指向根节点
        Node pp = null;//p的父结点
        while (p != null && p.data != data) {
            pp = p;
            if (p.data > data) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        if (p == null) return;
        if (p.left != null && p.right != null) {
            Node minNode = p.left;
            Node minPNode = p;
            while (minNode.left != null) {
                minPNode = minNode;
                minNode = minNode.left;
            }
            p.data = minNode.data;

            /*后面还需要把叶结点删除，因此，把p设置为minNode，把pp设置为minPNode，执行下面的代码就会删除叶节点*/
            p = minNode;
            pp = minPNode;
        }

        /*删除叶节点或只有一个子节点*/
        Node child;
        if (p.left != null) {
            child = p.left;
        } else if (p.right != null) {
            child = p.right;
        } else {
            child = null;
        }

        if (pp == null) tree = child;
        else if (pp.left == p) pp.left = child;
        else pp.right = child;
    }

    /**
     * 前序遍历，先打印自身，再打印左子节点，最后打印右子节点
     *
     * @param p
     */
    private void preReview(Node p) {
        System.out.println(p.data);
        if (p.left != null)
            preReview(p.left);
        if (p.right != null)
            preReview(p.right);
    }

    /**
     * 中序遍历，先打印左子节点，再打印自身，最后打印右子节点，输出有序数据，时间复杂度O(n)
     *
     * @param p
     */
    private void midReview(Node p) {
        if (p.left != null) {
            midReview(p.left);
        }
        System.out.println(p.data);
        if (p.right != null) {
            midReview(p.right);
        }
    }

    /**
     * 后序遍历，先打印左子节点，再打印右子节点，最后打印自身
     *
     * @param p
     */
    private void backReview(Node p) {
        if (p.left != null)
            backReview(p.left);
        if (p.right != null)
            backReview(p.right);
        System.out.println(p.data);
    }


    private Node tree;

    static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }


    /**
     * 平衡二叉查找树：树中任意一个节点的左右子树的高度相差不能大于 1。
     * 平衡二叉查找树中“平衡”的意思，其实就是让整棵树左右看起来比较“对称”，比较“平衡”，不要出现左子树很高、右子树很矮的情况。
     * 这样就能让整棵树的高度相对来说低一些，相应的插入、删除、查找等操作的效率高一些。
     * <p>
     * 红黑树是一种不严格的平衡二叉查找树，即不完全符合“树中任意一个节点的左右子树的高度相差不能大于 1”这个定义。但是符合“平衡”的定义
     * 1，根节点是黑色的
     * 2，每个叶节点都是黑色的空节点（null），即叶节点不存储数据
     * 3，任何相邻的节点（上下层的节点）都不能同时为红色，即红色节点被黑色节点隔开
     * 4，每个节点，从该节点到达其可达叶节点的所有路径，都包含相同的数目的黑色节点
     */
    private void redBlackTree() {

    }

    public static void main(String[] args) {
        int[] array = new int[10000];
        for (int i = 0; i < 10000; i++) {
            array[i] = (int) (Math.random() * 10000);
        }

//        [1, 1, 1, 1, 2, 2, 3, 3, 3, 5, 5, 5, 6, 6, 7, 32, 46, 123, 126, 134, 74326]
        int[] a = {1, 2, 3, 3, 4, 4, 5, 5, 5, 6, 8, 10, 11, 12, 12, 15, 15, 16, 16, 17, 18, 19, 20, 24};
        SortAlgorithm sortAlgorithm = new SortAlgorithm();
//        sortAlgorithm.bubbleSort(a);
//        sortAlgorithm.insertionSorting(a);
//        sortAlgorithm.selectionSort(a);
//        sortAlgorithm.merge(a, 0, a.length - 1);
//        sortAlgorithm.fastSort(a, 0, a.length - 1);
//        sortAlgorithm.countingSort(array, 0, 9999);
        int bsearch = sortAlgorithm.bsearchProblem4(a, 5);
//        sortAlgorithm.squart((double) 1 / 4);
        Node p = new Node(10);
        p.left = new Node(9);
        p.right = new Node(11);
        p.left.left = new Node(7);
        p.left.left.left = new Node(6);
        p.left.left.right = new Node(8);
        p.right.right = new Node(13);
        p.right.right.left = new Node(12);
        p.right.right.right = new Node(14);
//        sortAlgorithm.preReview(p);
        sortAlgorithm.midReview(p);
//        sortAlgorithm.backReview(p);


//        System.out.println(bsearch);
//        System.out.println(sortAlgorithm.getDataWithFastSort(array, 0, array.length - 1, 1));
//        System.out.println(Arrays.toString(a));
    }
}
