package com.m1raynee.lab2.AVL;

import com.m1raynee.lab2.BST.BaseBST;

public class AVLTree<T extends Comparable<T>> extends BaseBST<T, AVLNode<T>> {
    @Override
    protected AVLNode<T> createNode(T key) {
        return new AVLNode<>(key);
    }

    private int height(AVLNode<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private void updateHeight(AVLNode<T> node) {
        node.setHeight(
            1 + Math.max(
                height(node.getLeft()),
                height(node.getRight())
            )
        );
    }

    private AVLNode<T> rotateRight(AVLNode<T> parent) {
        AVLNode<T> leftChild = parent.getLeft();
        AVLNode<T> rightGreatchild = leftChild.getRight();

        leftChild.setRight(parent);
        parent.setLeft(rightGreatchild);

        updateHeight(parent);
        updateHeight(leftChild);
        return leftChild;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> parent) {
        AVLNode<T> rightChild = parent.getRight();
        AVLNode<T> leftGrandchild = rightChild.getLeft();

        rightChild.setLeft(parent);
        parent.setRight(leftGrandchild);

        updateHeight(parent);
        updateHeight(rightChild);
        return rightChild;
    }

    private AVLNode<T> balance(AVLNode<T> node, T key) {
        updateHeight(node);
        int balanceFactor = (node == null) ? 0 : height(node.getLeft()) - height(node.getRight());

        // Left-Left
        if (balanceFactor > 1 && key.compareTo(node.getLeft().getKey()) < 0) {
            return rotateRight(node);
        }
        
        // Right-Right
        if (balanceFactor < -1 && key.compareTo(node.getRight().getKey()) > 0) {
            return rotateLeft(node);
        }
        
        // Left-Right
        if (balanceFactor > 1 && key.compareTo(node.getLeft().getKey()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // Right-Left
        if (balanceFactor < -1 && key.compareTo(node.getRight().getKey()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }

    @Override
    protected AVLNode<T> insertRec(AVLNode<T> current, T key) {
        if (current == null) {
            return createNode(key);
        }

        int cmp = key.compareTo(current.getKey());

        if (cmp < 0) {
            current.setLeft(insertRec(current.getLeft(), key));
        } else if (cmp > 0) {
            current.setRight(insertRec(current.getRight(), key));
        } else {
            return current;  // Duplicate keys
        }
        return balance(current, key);
    }

    @Override
    protected AVLNode<T> deleteRec(AVLNode<T> current, T key) {
        if (current == null) {
            return null;
        }

        int cmp = key.compareTo(current.getKey());

        if (cmp < 0) {
            current.setLeft(deleteRec(current.getLeft(), key));
        } else if (cmp > 0) {
            current.setRight(deleteRec(current.getRight(), key));
        } else {
            if (current.getLeft() == null) {
                return current.getRight();
            }
            if (current.getRight() == null) {
                return current.getLeft();
            }

            AVLNode<T> successor = findMinRec(current.getRight());
            current.setKey(successor.getKey());
            current.setRight(deleteRec(current.getRight(), successor.getKey()));
        }

        return balance(current, key);
    }
}
