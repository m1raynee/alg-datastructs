package com.m1raynee;

public interface IBaseNode<T extends Comparable<T>, N extends IBaseNode<T, N>> {    
    T getKey();
    void setKey(T key);
    N getLeft();
    void setLeft(N left);
    N getRight();
    void setRight(N right);
}
