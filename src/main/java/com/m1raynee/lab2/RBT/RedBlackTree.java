package com.m1raynee.lab2.RBT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// import java.util.ArrayList;
// import java.util.List;

import com.m1raynee.lab2.BST.BaseBST;

public class RedBlackTree<T extends Comparable<T>> extends BaseBST<T, RBTNode<T>> {
    private final RBTNode<T> NIL = RBTNode.createNILNode();

    public RedBlackTree() {
        this.root = NIL;
    }

    @Override
    protected RBTNode<T> createNode(T key) {
        RBTNode<T> node = new RBTNode<>(key);
        node.setLeft(NIL);
        node.setRight(NIL);
        node.setParent(NIL);
        return node;
    }

    private void rotateLeft(RBTNode<T> parent) {
        RBTNode<T> rightChild = parent.getRight();
        parent.setRight(rightChild.getLeft());
        if (!rightChild.getLeft().isNIL()) {
            rightChild.getLeft().setParent(parent);
        }
        rightChild.setParent(parent.getParent());
        if (parent.getParent() == NIL) {
            this.root = rightChild;
        } else if (parent == parent.getParent().getLeft()) {
            parent.getParent().setLeft(rightChild);
        } else {
            parent.getParent().setRight(rightChild);
        }
        rightChild.setLeft(parent);
        parent.setParent(rightChild);
    }

    private void rotateRight(RBTNode<T> parent) {
        RBTNode<T> leftChild = parent.getLeft();
        parent.setLeft(leftChild.getRight());
        if (!leftChild.getRight().isNIL()) {
            leftChild.getRight().setParent(parent);
        }
        leftChild.setParent(parent.getParent());
        if (parent.getParent() == NIL) {
            this.root = leftChild;
        } else if (parent == parent.getParent().getRight()) {
            parent.getParent().setRight(leftChild);
        } else {
            parent.getParent().setLeft(leftChild);
        }
        leftChild.setRight(parent);
        parent.setParent(leftChild);
    }

    @Override
    public void insert(T key) {
        RBTNode<T> newNode = createNode(key);
        RBTNode<T> parent = NIL;
        RBTNode<T> current = root;

        while (!current.isNIL()) {
            parent = current;
            int cmp = key.compareTo(current.getKey());
            if (cmp < 0) {
                current = current.getLeft();
            } else if (cmp > 0) {
                current = current.getRight();
            } else return;
        }
        newNode.setParent(parent);
        if (parent == NIL) {
            root = newNode;
        } else if (key.compareTo(parent.getKey()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        balanceInsert(newNode);
    }

    private void balanceInsert(RBTNode<T> node) {
        while (node.getParent().getColor() == NodeColor.RED) {
            RBTNode<T> p = node.getParent();
            RBTNode<T> g = p.getParent();

            boolean isParentLeft = (p == g.getLeft());

            RBTNode<T> uncle = (isParentLeft) ? g.getRight() : g.getLeft();
            if (uncle.getColor() == NodeColor.RED) {
                uncle.setColor(NodeColor.BLACK);
                p.setColor(NodeColor.BLACK);
                g.setColor(NodeColor.RED);
                node = g;
                continue;
            }

            if (isParentLeft) {
                if (node == p.getRight()) {
                    node = p;
                    rotateLeft(node);
                    p = node.getParent();
                }
                p.setColor(NodeColor.BLACK);
                g.setColor(NodeColor.RED);
                rotateRight(g);
            } else {
                if (node == p.getLeft()) {
                    node = p;
                    rotateRight(node);
                    p = node.getParent();
                }
                p.setColor(NodeColor.BLACK);
                g.setColor(NodeColor.RED);
                rotateLeft(g);
            }
        }

        root.setColor(NodeColor.BLACK);
    }

    // delete



    private void transplantNode(RBTNode<T> dest, RBTNode<T> node) {
        if (dest.getParent().isNIL()) {
            root = node;
        } else if (dest == dest.getParent().getLeft()) {
            dest.getParent().setLeft(node);
        } dest.getParent().setRight(node);
        node.setParent(dest.getParent());
    }

    // NIL replacement start
    @Override
    protected boolean searchRec(RBTNode<T> node, T key) {
        if (node == NIL) {
            return false;
        }

        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            return searchRec(node.getLeft(), key);
        } else if (cmp > 0) {
            return searchRec(node.getRight(), key);
        } else {
            return true;
        }
    }
    
    @Override
    protected RBTNode<T> findMinRec(RBTNode<T> node) {
        if (node.getLeft().isNIL()) {
            return node;
        }
        return findMinRec(node.getLeft());
    }
    
    @Override
    protected RBTNode<T> findMaxRec(RBTNode<T> node) {
        if (node.getRight().isNIL()) {
            return node;
        }
        return findMaxRec(node.getRight());
    }

    @Override
    protected List<T> inOrderRec(RBTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node.isNIL()) {
            return list;
        }
        list.addAll(inOrderRec(node.getLeft()));
        list.add(node.getKey());
        list.addAll(inOrderRec(node.getRight()));
        return list;
    }

    @Override
    protected List<T> preOrderRec(RBTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node.isNIL()) {
            return list;
        }
        list.add(node.getKey());
        list.addAll(preOrderRec(node.getLeft()));
        list.addAll(preOrderRec(node.getRight()));
        return list;
    }

    @Override
    protected List<T> postOrderRec(RBTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node.isNIL()) {
            return list;
        }
        list.addAll(postOrderRec(node.getLeft()));
        list.addAll(postOrderRec(node.getRight()));
        list.add(node.getKey());
        return list;
    }

    @Override
    protected List<T> levelOrderRec(RBTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node.isNIL()) {
            return list;
        }
        Queue<RBTNode<T>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            RBTNode<T> current = queue.poll();
            list.add(current.getKey());
            if (!current.getLeft().isNIL()) {
                queue.add(current.getLeft());
            }
            if (!current.getRight().isNIL()) {
                queue.add(current.getRight());
            }
        }
        return list;
    }

    // NIL replacement end

    @Override
    protected void prettyPrintRec(RBTNode<T> node, String prefix, boolean isTail) {
        if (node != NIL) {
            if (node.getRight() != NIL) {
                prettyPrintRec(node.getRight(), prefix + (isTail ? "│   " : "    "), false);
            }

            System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.toString());

            if (node.getLeft() != NIL) {
                prettyPrintRec(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}
