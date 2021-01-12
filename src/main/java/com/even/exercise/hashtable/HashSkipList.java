package com.even.exercise.hashtable;

import java.util.Random;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/8 15:38
 */
public class HashSkipList {
    private static int MAX_LEVEL = 5;
    private LieTou header;

    private LieTou[] lieTous;
    private Random r = new Random();

    public HashSkipList() {
        lieTous = new LieTou[MAX_LEVEL];
    }

    public void insert(LieTou newLT) {
        if (header == null) {
            header = newLT;
            lieTous[0] = header;
            for (int i = 1; i < MAX_LEVEL; i++) {
                LieTou newNode = new LieTou(newLT.id, newLT.score);
                newNode.down = lieTous[i - 1];
                lieTous[i] = newNode;
            }
            return;
        }
        int level = RandomLevel();
        insertNode(lieTous[level], newLT, level);
    }

    private LieTou insertNode(LieTou p, LieTou q, int level) {
        while (p.next != null) {
            if (p.score == q.score) {//如果索引层积分相等，就不添加索引
                if (level == 0) {
                    p.next.prev = q;
                    q.next = p.next;
                    q.prev = p;
                    p.next = q;
                    return q;
                }
                insertNode(p.down, q, level - 1);
            }
            if (p.score < q.score && p.next.score > q.score) {
                LieTou down = null;
                if (p.down != null)
                    down = insertNode(p.down, q, level - 1);
                if (level == 0) {
                    p.next.prev = q;
                    q.next = p.next;
                    q.down = down;
                    q.prev = p;
                    p.next = q;
                    return q;
                } else {
                    LieTou newNode = new LieTou(q.id, q.score);
                    p.next.prev = newNode;
                    newNode.next = p.next;
                    newNode.down = down;
                    newNode.prev = p;
                    p.next = newNode;
                    return newNode;
                }
            }
            p = p.next;
        }

        LieTou down = null;
        if (p.down != null)
            down = insertNode(p.down, q, level - 1);
        if (p.score == q.score) {
            if (level == 0) {
                q.prev = p;
                p.next = q;
                return q;
            }
        } else {
            if (level == 0) {
                q.next = p.next;
                q.down = down;
                q.prev = p;
                p.next = q;
                return q;
            } else {
                LieTou newNode = new LieTou(q.id, q.score);
                newNode.next = p.next;
                newNode.down = down;
                newNode.prev = p;
                p.next = newNode;
                return newNode;
            }
        }
        return null;
    }

    public void update(LieTou p) {
        LieTou pre = p.prev;
        LieTou next = p.next;
        if (pre != null) {
            if (pre.score > p.score) {
                pre.next = next;
                LieTou section = findBySection(p.score);
                section.next.prev = p;
                p.next = section.next;
                p.prev = section;
                section.next = p;
                return;
            }
        }
        if (next != null) {
            if (next.score < p.score) {
                pre.next = next;
                LieTou section = findBySection(p.score);
                p.next = section.next;
                p.prev = section;
                section.next = p;
                return;
            }
        }
    }

    /**
     * 找到最后一个小于等于score的节点
     *
     * @param score
     * @return
     */
    public LieTou findBySection(int score) {
        return findBySection(lieTous[MAX_LEVEL - 1], score);
    }

    private LieTou findBySection(LieTou p, int score) {
        while (p.next != null) {
            //头部节点如果大于score，则返回该节点
            if ((p.score >= score) || (p.score < score && p.next.score > score)) {
                if (p.down != null)
                    return findBySection(p.down, score);
                else {
                    return p;
                }
            }
            p = p.next;
        }
        //此时的p是当前链表的最后一个元素
        if (p.down != null)//代表是索引层
            return findBySection(p.down, score);
        if (p.score == score)
            return p;
        return null;//代表数据层最后一个元素也比给定的范围小
    }

    private int RandomLevel() {
        int level = 0;
        for (int i = 1; i < MAX_LEVEL; i++) {
            if (r.nextInt() % 2 != 1)
                level++;
        }
        return level;
    }

    public boolean deleteNode(LieTou delNode) {
        if (delNode.next != null)
            delNode.next.prev = delNode.prev;
        delNode.prev.next = delNode.next;
        return deleteNode(lieTous[MAX_LEVEL - 1], delNode, 4);
    }

    private boolean deleteNode(LieTou p, LieTou delNode, int level) {
        if (level == 1 && p.score == delNode.score) {
            if (p.down.next != null && p.down.next.score == delNode.score) {
                p.down = p.down.next;
                return true;//表示不需要删除索引节点
            } else {
                p.prev.next = p.next;
                if (p.next != null)
                    p.next.prev = p.prev;
                p.down = null;
                return false;//表示要删除索引节点
            }
        }
        while (p.next != null) {
            if (p.score == delNode.score && level > 1) {
                if (!deleteNode(p.down, delNode, level - 1)) {
                    p.prev.next = p.next;
                    if (p.next != null)
                        p.next.prev = p.prev;
                    p.down = null;
                    return false;//表示要删除索引节点
                }
            }
            if (p.score < delNode.score && p.next.score > delNode.score) {
                if (p.down != null)
                    return deleteNode(p.down, delNode, level - 1);
            }
            p = p.next;
        }
        if (p.down != null)
            deleteNode(p.down, delNode, level - 1);
        if (p.score == delNode.score) {
            p.prev.next = p.next;
            return false;//代表要删除索引节点
        }
        return true;
    }

    public LieTou findFromIndex(int begin) {
        LieTou p = lieTous[0];
        int i = 0;
        while (i < begin) {
            if (p.next != null) {
                i++;
                p = p.next;
            } else return null;
        }
        return p;
    }
}
