package com.even.exercise.hashtable;

import java.util.*;

/**
 * Project Name: ai
 * Des:
 * 开放寻址法和链表法来解决散列冲突
 * 大数据量，大对象用链表法
 * <p>
 * 企业级散列表要求可以设计一个可以应对各种异常情况来避免在散列冲突的情况下，散列表性能的急剧下降
 * <p>
 * 散列函数不能太复杂，且生成的数要尽可能随机且均匀分布
 * <p>
 * 装载因子：表示已存储的个数/散列列总长度
 * <p>
 * 应用1：散列表+双向链表实现LRU缓存
 * <p>
 * 应用2：猎聘网有10万名猎头，每个猎头都可以通过做任务来积累积分，然后通过积分下载简历。问题是如果在内存中存储这10万个猎头ID和积分信息
 * <p>
 * 支持的操作：
 * 1，根据猎头的ID快速查找、删除、更新猎头的积分信息
 * 2，查找积分在某个区间的猎头ID列表
 * 3，查找按照积分从小到大排名的第x位到第y位之间的猎头ID列表
 * <p>
 * 实现：使用散列表+跳表的方式，维护两个链表，一个是拉链表，用于解决散列冲突，另一个是跳表，按积分大小排序，用于查找积分在某个区间或排序
 * Created by Even on 2019/4/8 13:30
 */
public class HashTable {
    private int MAX_SIZE = 16;
    private LieTou[] lieTous;//散列表
    private HashSkipList skipList;//跳表

    public HashTable() {
        lieTous = new LieTou[MAX_SIZE];
        skipList = new HashSkipList();
    }

    private void put(Integer key, int value) {
        int hash = key.hashCode();
        int index = hash % MAX_SIZE;
        if (lieTous[index] == null) {
            LieTou newNode = new LieTou(key, value);
            skipList.insert(newNode);
            lieTous[index] = newNode;
        } else {
            LieTou p = lieTous[index];
            boolean exist = false;
            while (p.hhnext != null) {
                if (p.id == key) {
                    p.score = value;
                    skipList.update(p);//更新跳表
                    return;
                }
                p = p.hhnext;
            }
            if (p.id == key) {
                p.score = value;
                skipList.update(p);//更新跳表
                exist = true;
            }

            if (!exist) {//不存在，则添加
                LieTou newNode = new LieTou(key, value);
                p.hhnext = newNode;
                skipList.insert(newNode);
            }
        }
    }

    private int get(Integer key) {
        int hash = key.hashCode();
        LieTou result = lieTous[hash % MAX_SIZE];
        if (result == null) return -1;
        else {
            while (result.id != key) {
                if (result.hhnext != null)
                    result = result.hhnext;
                else
                    return -1;//表示最后一个也不等于key
            }
            return result.score;
        }
    }

    private void delete(Integer key) {
        int index = key.hashCode() % MAX_SIZE;
        LieTou delTou = lieTous[index];
        if (delTou == null)
            return;
        if (delTou.id == key) {
            skipList.deleteNode(delTou);//更新跳表
            lieTous[index] = delTou.hhnext;//删除头部指针
        }

        while (delTou.hhnext != null) {
            if (delTou.hhnext.id == key) {
                skipList.deleteNode(delTou.hhnext);//更新跳表
                delTou.hhnext = delTou.hhnext.hhnext;//更新散列表的拉链
                return;
            }
            delTou = delTou.hhnext;
        }
    }

    /**
     * 查看积分区间为begin到end的猎头id
     *
     * @param beginScore
     * @param endScore
     * @return
     */
    private List listLieTou(int beginScore, int endScore) {
        if (beginScore > endScore) return null;

        LieTou beginNode = skipList.findBySection(beginScore);
        if (beginNode == null) {//等于null表示没有score最小值小于等于begin的元素
            return null;
        }
        List idList = new ArrayList();
        while (beginNode != null) {
            if (beginNode.score < beginScore) {
                beginNode = beginNode.next;
                continue;
            }
            if (beginNode.score <= endScore) {
                idList.add(beginNode.id);
            } else {
                break;
            }
            beginNode = beginNode.next;
        }
        return idList;
    }


    private List listLieTouByIndex(int x, int y) {
        LieTou beginNode = skipList.findFromIndex(x);
        List idList = new ArrayList();
        int i = 0;
        while (i <= y - x) {
            if (beginNode != null)
                idList.add(beginNode.id);
            else break;
            i++;
            beginNode = beginNode.next;
        }
        return idList;
    }

    public static void main(String[] args) {
        //accessOrder表示排序的方式
        HashTable hashTable = new HashTable();
        hashTable.put(1, 123);
        hashTable.put(2, 234);
        hashTable.put(3, 456);
        hashTable.put(4, 567);//添加
        hashTable.put(5, 567);//添加
        hashTable.delete(4);//删除
        hashTable.put(20, 678);
        hashTable.put(20, 235);//更新
        hashTable.put(19, 8910);
        List list = hashTable.listLieTou(123, 9999);//积分区间
        List list1 = hashTable.listLieTouByIndex(1, 3);//从小到大第x到y
        System.out.println(list);
        System.out.println(list1);
    }
}
