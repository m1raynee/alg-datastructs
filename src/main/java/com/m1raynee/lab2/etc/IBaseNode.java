package com.m1raynee.lab2.etc;

public interface IBaseNode<T extends Comparable<T>, N extends IBaseNode<T, N>> {    
    T getKey();
    void setKey(T key);
    N getLeft();
    void setLeft(N left);
    N getRight();
    void setRight(N right);
}
