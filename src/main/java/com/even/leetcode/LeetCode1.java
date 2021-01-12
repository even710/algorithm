package com.even.leetcode;


/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/12 16:05
 */
public class LeetCode1 {
    /**
     * 输入 nums = [2, 7, 11, 15], target = 9,
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if ((tmp + nums[j]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     * Explanation: 342 + 465 = 807.
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int p = (l1.val + l2.val) % 10;
        int q = (l1.val + l2.val) / 10;
        ListNode result = new ListNode(p);
        l1 = l1.next;
        l2 = l2.next;
        ListNode tmp = result;
        while (l1 != null || l2 != null) {
            if (l2 == null) {
                p = (l1.val + q) % 10;
                q = (l1.val + q) / 10;
                tmp.next = new ListNode(p);
                tmp = tmp.next;
                l1 = l1.next;
                continue;
            }
            if (l1 == null) {
                p = (l2.val + q) % 10;
                q = (l2.val + q) / 10;
                tmp.next = new ListNode(p);
                tmp = tmp.next;
                l2 = l2.next;
                continue;
            }
            p = (l1.val + l2.val + q) % 10;
            q = (l1.val + l2.val + q) / 10;
            tmp.next = new ListNode(p);
            tmp = tmp.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        if (q != 0)
            tmp.next = new ListNode(q);
        return result;
    }

    /**
     * 给定一个字符串，找到最长子字符串的长度而不重复字符。
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        char[] root = s.toCharArray();
        int length = 0;
        for (int p = 0, q = 0; q < root.length; q++) {
            for (int i = p; i < q; i++)
                if (root[i] == root[q]) {
                    if (length < q - p) length = q - p;
                    p = i + 1;
                    break;
                }
            if (length < (q - p + 1)) length = q - p + 1;
        }
        return length;
    }


    /**
     * 求两个有序数组的中位数，且时间复杂度为O(log(n+m))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) { // to ensure m<=n
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && nums2[j - 1] > nums1[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && nums1[i - 1] > nums2[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = nums2[j];
                } else if (j == n) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums2[j], nums1[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    /**
     * 获取字符串中的回文字串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        char[] array = s.toCharArray();
        int n = array.length;
        boolean[][] palidrome = new boolean[n][n];
        int i = 1;
        StringBuilder result = new StringBuilder();
        int p = 0;
        int q = 0;
        while (i < n) {
            for (int j = 0; j < n - i; j++) {
                if (array[j] == array[j + i]) {
                    if (i == 1 || i == 2) {
                        palidrome[j][j + i] = true;
                        p = j;
                        q = j + i;
                    } else {
                        if (palidrome[j + 1][j + i - 1]) {
                            palidrome[j][j + i] = true;
                            p = j;
                            q = j + i;
                        }
                    }
                }
            }
            i++;
        }
        for (int z = p; z <= q; z++) {
            result.append(array[z]);
        }
        return result.toString();
    }


    private int lo, maxLen;

    /**
     * 获取字符串中的回文字串，优化版
     *
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        int len = s.length();
        if (len < 2)
            return s;

        for (int i = 0; i < len - 1; i++) {
            extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible，判断奇数的回文
            extendPalindrome(s, i, i + 1); //assume even length.判断偶数的回文
        }
        return s.substring(lo, lo + maxLen);
    }

    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }


    /**
     * Z字形字符串打印
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        int length = s.length();
        if (length <= numRows || numRows == 1) return s;
        char[] chars = s.toCharArray();
        int z;
        int n;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= numRows - 1; i++) {
            z = i;
            n = 0;
            while (z - 2 * i < length) {
                if (i != 0 && i != numRows - 1 && n > 0)
                    result.append(chars[z - 2 * i]);
                if (z < length)
                    result.append(chars[z]);
                n++;
                z = 2 * n * numRows - 2 * n + i;
            }
        }
        System.out.println(result.toString());
        return result.toString();
    }


    /**
     * 反转一个带符号的32位integer类型的数据
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;//防止到达上限，上限最后一个数字为7
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;//防止到达下限，下限最后一个数字为-8
            rev = rev * 10 + pop;
        }
        return rev;
    }

    public static void main(String[] args) {
        LeetCode1 leetCode1 = new LeetCode1();
        System.out.println(leetCode1.lengthOfLongestSubstring("pwiwkejlmw"));
        int[] num1 = new int[]{1, 3, 5, 9, 9, 13, 13, 15, 17, 19};
        int[] num2 = new int[]{2, 4, 6, 16, 16, 16, 17, 18, 20};
//        System.out.println(Math.round(2 * ((double) 9 / 19)));
        System.out.println(leetCode1.findMedianSortedArrays(num1, num2));
        System.out.println(leetCode1.longestPalindrome("bb"));
        System.out.println(leetCode1.convert("AB", 1)); 
        System.out.println(Integer.MIN_VALUE);
        System.out.println(leetCode1.reverse(1534236469));
    }
}
