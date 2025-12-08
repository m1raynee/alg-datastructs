package com.m1raynee;

public abstract class AbstractTreeNode<T extends Comparable<T>, N extends IBaseNode<T, N>> implements IBaseNode<T, N> {
    protected T key;
    protected N left;
    protected N right;

    public AbstractTreeNode(T key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }

    @Override
    public T getKey() { return key; }

    @Override
    public void setKey(T key) { this.key = key; }

    @Override
    public N getLeft() { return left; }

    @Override
    public void setLeft(N node) { this.left = node; }

    @Override
    public N getRight() { return right; }

    @Override
    public void setRight(N node) { this.right = node; }

    @Override
    public String toString() { return String.valueOf(key); }
}
