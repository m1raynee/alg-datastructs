package com.m1raynee;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BaseBST<T extends Comparable<T>, N extends IBaseNode<T, N>> {
    protected N root;

    public BaseBST() {
        this.root = null;
    }

    @SuppressWarnings("unchecked")
    protected N createNode(T key) {
        return (N) new BSTNode<T>(key);
    }

    public void insert(T key) {
        root = insertRec(root, key);
    }

    protected N insertRec(N current, T key) {
        if (current == null) {
            return createNode(key);
        }

        int cmp = key.compareTo(current.getKey());

        if (cmp < 0) {
            current.setLeft(insertRec(current.getLeft(), key));
        } else if (cmp > 0) {
            current.setRight(insertRec(current.getRight(), key));
        }
        // Если cmp == 0, возвращаем текущий узел без изменений

        return current;
    }

    public boolean search(T key) {
        return searchRec(root, key);
    }

    private boolean searchRec(N node, T key) {
        if (node == null) {
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

    public T findMin() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMinRec(root).getKey();
    }

    protected N findMinRec(N node) {
        if (node.getLeft() == null) {
            return node;
        }
        return findMinRec(node.getLeft());
    }

    public T findMax() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMaxRec(root).getKey();
    }

    protected N findMaxRec(N node) {
        if (node.getRight() == null) {
            return node;
        }
        return findMaxRec(node.getRight());
    }

    public void delete(T key) {
        root = deleteRec(root, key);
    }

    protected N deleteRec(N node, T key) {
        if (node == null) {
            throw new IllegalStateException("Tree is empty");
        }

        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(deleteRec(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(deleteRec(node.getRight(), key));
        } else {
            // Node with only one child or no child
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            
            // Node with two children
            N minRight = findMinRec(node.getRight());
            node.setKey(minRight.getKey());
            node.setRight(deleteRec(node.getRight(), minRight.getKey()));
        }
        return node;
    }

    public List<T> inOrderTraversal() {
        return inOrderRec(root);
    }

    private List<T> inOrderRec(N node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.addAll(inOrderRec(node.getLeft()));
        list.add(node.getKey());
        list.addAll(inOrderRec(node.getRight()));
        return list;
    }

    public List<T> preOrderTraversal() {
        return preOrderRec(root);
    }

    private List<T> preOrderRec(N node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.add(node.getKey());
        list.addAll(preOrderRec(node.getLeft()));
        list.addAll(preOrderRec(node.getRight()));
        return list;
    }

    public List<T> postOrderTraversal() {
        return postOrderRec(root);
    }

    private List<T> postOrderRec(N node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.addAll(postOrderRec(node.getLeft()));
        list.addAll(postOrderRec(node.getRight()));
        list.add(node.getKey());
        return list;
    }

    public List<T> levelOrderTraversal() {
        return levelOrderRec(root);
    }

    private List<T> levelOrderRec(N node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        Queue<N> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            N current = queue.poll();
            list.add(current.getKey());
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
        return list;
    }

    public void prettyPrint() {
        prettyPrintRec(root, "", true);
        System.out.println();
    }

    private void prettyPrintRec(N node, String prefix, boolean isTail) {
        if (node != null) {
            if (node.getRight() != null) {
                prettyPrintRec(node.getRight(), prefix + (isTail ? "│   " : "    "), false);
            }

            System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.toString());

            if (node.getLeft() != null) {
                prettyPrintRec(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}
