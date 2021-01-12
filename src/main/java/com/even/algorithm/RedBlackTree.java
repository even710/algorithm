package com.even.algorithm;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/3/25 13:46
 */
public class RedBlackTree {
    static class Node {
        private int data;
        private Node left;
        private Node right;
        private String color = "red";
        private Node parent;

        public Node(int data, Node leaf) {
            this.data = data;
            this.left = leaf;
            this.right = leaf;
        }

        public Node() {
            this.color = "black";
        }

        public Node(int data, String color) {
            this.data = data;
            this.color = color;
            Node leaf = new Node();
            this.left = leaf;
            this.right = leaf;
        }
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
        if (p.data != 0) {
            if (p.left != null) {
                midReview(p.left);
            }
            System.out.println(p.data);
            if (p.right != null) {
                midReview(p.right);
            }
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

    /**
     * 红黑树左旋
     *
     * @param p 关注点
     * @return
     */
    private Node leftTransfer(Node p) {
        Node child = p.right;
        if (child == null) return p;
        if (child.left != null)
            p.right = child.left;
        else p.right = null;
        child.left = p;
        return child;
    }

    private Node rightTransfer(Node p) {
        Node child = p.left;
        if (child == null) return p;
        if (child.right != null)
            p.left = child.right;
        else p.left = null;
        child.right = p;
        return child;
    }

    private Node tree;

    private void insert(int data) {
        if (tree == null) {
            tree = new Node(data, "black");
            tree.parent = null;
            return;
        }
        Node p = tree;
        Node pp = tree.parent;
        boolean left = true;
        while (true) {
            if (p.data != 0) {
                if (data > p.data) {
                    if (p.right == null) {
                        if ("black".equals(p.color)) {
                            p.right = new Node(data, leaf);
                            p.right.parent = p;
                        } else {
                        /*进行调整*/
                            adjustTree(pp, p, new Node(data, leaf));
                        }
                        return;
                    }
                    left = false;
                    pp = p;
                    p = p.right;
                } else {
                    if (p.left == null) {
                        if ("black".equals(p.color)) {
                            p.left = new Node(data, leaf);
                            p.left.parent = p;
                        } else {
                         /*进行调整*/
                            adjustTree(pp, p, new Node(data, leaf));
                        }
                        return;
                    }
                    left = true;
                    pp = p;
                    p = p.left;
                }
            } else {
                Node newChild = new Node(data, p);
                if (left)
                    pp.left = newChild;
                else
                    pp.right = newChild;
                newChild.parent = pp;
                if ("red".equals(pp.color))
                    adjustTree(pp.parent, pp, newChild);
                return;
            }
        }
    }

    private static Node leaf = new Node();

    private void adjustTree(Node pp, Node p, Node node) {
        if ("red".equals(p.color)) {
            node.parent = p;
            boolean left = true;
            if (p.data > pp.data)
                left = false;
            Node brotherNode;
            if (left)
                brotherNode = pp.right;
            else
                brotherNode = pp.left;

            if ("red".equals(brotherNode.color)) {
                p.color = "black";
                brotherNode.color = "black";
                pp.color = "red";
            } else {
                case2(pp, p, node, left);
                return;
            }
            if (pp.parent == null) {
                pp.color = "black";
                return;
            } else if (pp.parent.parent == null) {
                return;
            } else {
                adjustTree(pp.parent.parent, pp.parent, pp);
            }
        }
    }

    private void case2(Node pp, Node p, Node child, boolean left) {
        if (left) {
            if (child.data > p.data) {
                p = leftTransfer(p);
                p.parent = pp;
                pp.left = p;
            }
            pp = rightTransfer(pp);
            p.right = pp;
        } else {
            if (child.data < p.data) {
                p = rightTransfer(p);
                p.parent = pp;
                pp.right = p;
            }
            pp = rightTransfer(pp);
            p.left = pp;
        }
        pp.parent = p;
        pp.color = "red";
        p.color = "black";
    }

    private void deleteNode(Node p) {
        Node pp = p.parent;
        boolean left = true;
        if(pp==null){

        }

        if ("black".equals(p.color)) {
            if (p.left.data == 0 && p.right.data != 0) {
                if("red".equals(p.right.color));

            }
        }
    }

    public static void main(String[] args) {
        Node p = new Node(10, leaf);
        p.color = "black";
        p.left = new Node(9, leaf);
        p.left.parent = p;
        p.right = new Node(11, leaf);
        p.right.parent = p;
        p.left.left = new Node(7, leaf);
        p.left.left.parent = p.left;
        p.left.left.color = "block";
        p.left.left.left = new Node(6, leaf);
        p.left.left.left.parent = p.left.left;
        p.left.left.right = new Node(8, leaf);
        p.left.left.right.parent = p.left.left;
        p.right.right = new Node(13, leaf);
        p.right.right.parent = p.right;
        p.right.right.color = "block";
        p.right.right.left = new Node(12, leaf);
        p.right.right.left.parent = p.right.right;
        p.right.right.right = new Node(14, leaf);
        p.right.right.right.parent = p.right.right;
        RedBlackTree redBlackTree = new RedBlackTree();
//        redBlackTree.midReview(p);
//        System.out.println("-----------------");
//        p.left = redBlackTree.rightTransfer(p.left);
//        redBlackTree.midReview(p);
        redBlackTree.tree = p;
        redBlackTree.insert(20);
        redBlackTree.insert(25);
        redBlackTree.insert(19);
        redBlackTree.insert(17);
        redBlackTree.midReview(p);
    }
}
