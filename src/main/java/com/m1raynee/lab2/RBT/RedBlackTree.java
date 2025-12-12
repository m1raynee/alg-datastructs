package com.m1raynee.lab2.RBT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        if (parent.getParent().isNIL()) {
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
        if (parent.getParent().isNIL()) {
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
        if (parent.isNIL()) {
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

    @Override
    public void delete(T key) {
        RBTNode<T> node = search(key);
        if (node.isNIL()) throw new IllegalStateException("Key not found in the tree");

        deleteNode(node);
    }

    private void deleteNode(RBTNode<T> node) {
        if (node.isNIL()) {
            return;
        }

        if (!node.getLeft().isNIL() && !node.getRight().isNIL()) {
            RBTNode<T> successor = findMinRec(node.getRight());
            node.setKey(successor.getKey());
            deleteNode(successor);
            return;
        }
        
        RBTNode<T> child = node.getLeft().isNIL() ? node.getRight() : node.getLeft();
        
        if (!node.getParent().isNIL()) {
            if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(child);
            } else {
                node.getParent().setRight(child);
            }
            child.setParent(node.getParent());
        } else {
            // node is root
            root = child;
            child.setParent(NIL);
        }
        
        if (node.getColor() == NodeColor.BLACK) {
            balanceDelete(child);
        }
    }

    private void balanceDelete(RBTNode<T> node) {
        while (node != root && node.getColor() == NodeColor.BLACK) {
            boolean isLeftChild = (node == node.getParent().getLeft());
            RBTNode<T> sibling = isLeftChild ? node.getParent().getRight() : node.getParent().getLeft();

            // Красный брат
            if (sibling.getColor() == NodeColor.RED) {
                sibling.setColor(NodeColor.BLACK);
                node.getParent().setColor(NodeColor.RED);

                if (isLeftChild) rotateLeft(node.getParent());
                else rotateRight(node.getParent());
                sibling = isLeftChild ? node.getParent().getRight() : node.getParent().getLeft();
            }

            if (node == node.getParent().getLeft()) {
                
                // У брата 2 чёрных потомка: красим брата, продолжаем от родителя
                if (sibling.getLeft().getColor() == NodeColor.BLACK && 
                    sibling.getRight().getColor() == NodeColor.BLACK) {
                    sibling.setColor(NodeColor.RED);
                    node = node.getParent();
                }
                else {
                    // Правый потомок брата черный: исправляем поворотом
                    if (sibling.getRight().getColor() == NodeColor.BLACK) {
                        sibling.getLeft().setColor(NodeColor.BLACK);
                        sibling.setColor(NodeColor.RED);
                        rotateRight(sibling);
                        sibling = node.getParent().getRight();
                    }
                    // Правый потомок красный: конец балансировки
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(NodeColor.BLACK);
                    sibling.getRight().setColor(NodeColor.BLACK);
                    rotateLeft(node.getParent());
                    node = root;
                }
            } else {
                // У брата 2 чёрных потомка: красим брата, балансируем от родителя
                if (sibling.getRight().getColor() == NodeColor.BLACK && 
                    sibling.getLeft().getColor() == NodeColor.BLACK) {
                    sibling.setColor(NodeColor.RED);
                    node = node.getParent();
                }
                else {
                    // левый потомок брата чёрный: исправляем поворотом
                    if (sibling.getLeft().getColor() == NodeColor.BLACK) {
                        sibling.getRight().setColor(NodeColor.BLACK);
                        sibling.setColor(NodeColor.RED);
                        rotateLeft(sibling);
                        sibling = node.getParent().getLeft();
                    }
                    // Правый потомок брата красный: конец балансировки
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(NodeColor.BLACK);
                    sibling.getLeft().setColor(NodeColor.BLACK);
                    rotateRight(node.getParent());
                    node = root;
                }
            }
        }
        node.setColor(NodeColor.BLACK);
    }

    // NIL replacement start

    @Override
    protected RBTNode<T> searchRec(RBTNode<T> node, T key) {
        if (node.isNIL()) return node;
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) return searchRec(node.getLeft(), key);
        else if (cmp > 0) return searchRec(node.getRight(), key);
        return node;
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

    @Override
    protected int heightRec(RBTNode<T> node) {
        if (node.isNIL()) {
            return 0;
        }
        int leftHeight = heightRec(node.getLeft());
        int rightHeight = heightRec(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // NIL replacement end
}
