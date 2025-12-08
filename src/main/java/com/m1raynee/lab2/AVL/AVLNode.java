package com.m1raynee.lab2.AVL;

import com.m1raynee.lab2.etc.AbstractTreeNode;

public class AVLNode<T extends Comparable<T>> extends AbstractTreeNode<T, AVLNode<T>> {
    private int height;

    public AVLNode(T key) {
        super(key);
        this.height = 1;
    }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}
