package com.m1raynee.lab2.BST;

import com.m1raynee.lab2.etc.AbstractTreeNode;

public class BSTNode<T extends Comparable<T>> extends AbstractTreeNode<T, BSTNode<T>> {
    public BSTNode(T key) {
        super(key);
    }
}
