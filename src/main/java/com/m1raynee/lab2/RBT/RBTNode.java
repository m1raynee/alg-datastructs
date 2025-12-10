package com.m1raynee.lab2.RBT;

import com.m1raynee.lab2.etc.AbstractTreeNode;

public class RBTNode<T extends Comparable<T>> extends AbstractTreeNode<T, RBTNode<T>> {
    private NodeColor color;
    private RBTNode<T> parent;

    private boolean isNIL = false;
    
    public RBTNode(T key) {
        super(key);
        this.color = NodeColor.RED;
    }

    private RBTNode(boolean isNIL) {
        super(null);
        this.isNIL = isNIL;
        this.color = NodeColor.BLACK;
        this.left = this;
        this.right = this;
        this.parent = this;
    }

    public static <T extends Comparable<T>> RBTNode<T> createNILNode() {
        return new RBTNode<>(true);
    }

    public NodeColor getColor() { return color; }
    public void setColor(NodeColor color) { if (!isNIL) this.color = color; }
    public RBTNode<T> getParent() { return parent; }
    public void setParent(RBTNode<T> parent) { this.parent = parent; }
    public boolean isNIL() { return isNIL; }

    @Override
    public String toString() {
        return isNIL ? "nil" : key.toString() + ((color == NodeColor.BLACK) ? " (b)" : " (r)");
    }
}
