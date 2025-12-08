package com.m1raynee;

public class BSTNode<T extends Comparable<T>> extends AbstractTreeNode<T, BSTNode<T>> {
    public BSTNode(T key) {
        super(key);
    }
}
