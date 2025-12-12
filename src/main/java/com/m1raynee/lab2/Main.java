package com.m1raynee.lab2;

import com.m1raynee.lab2.AVL.AVLNode;
import com.m1raynee.lab2.AVL.AVLTree;
import com.m1raynee.lab2.BST.BSTNode;
import com.m1raynee.lab2.BST.BaseBST;
import com.m1raynee.lab2.RBT.RBTNode;
import com.m1raynee.lab2.RBT.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        testBST();
        System.out.println("===================================");
        testAVL();
        System.out.println("===================================");
        testRBT();
    }

    private static void testBST() {
        Integer[] arrayIntegers = { 20, 10, 110, 100, 50, 55, 70, 40, 15, 5 };
        BaseBST<Integer, BSTNode<Integer>> bst = new BaseBST<>();
        for (Integer n : arrayIntegers) {
            System.out.println("Insert " + n);
            bst.insert(n);
            bst.prettyPrint();
        }
        System.out.println("Height: " + bst.height());
        BSTNode<Integer> s = bst.search(55); 
        System.out.println("Search 55: " + s + " left child: " + s.getLeft() + " right child: " + s.getRight());
        System.out.println("Min: " + bst.findMin());
        System.out.println("Max: " + bst.findMax());
        System.out.println("In-order: " + bst.inOrderTraversal());
        System.out.println("Pre-order: " + bst.preOrderTraversal());
        System.out.println("Post-order: " + bst.postOrderTraversal());
        System.out.println("Level-order: " + bst.levelOrderTraversal());
        bst.delete(20);
        bst.delete(50);
        System.out.println("After deleting 20 and 50:");
        bst.prettyPrint();
    }
    
    private static void testAVL() {
        AVLTree<Integer> avl = new AVLTree<>();
        Integer[] arrayIntegers = { 20, 10, 110, 100, 50, 55, 70, 40, 15, 5 };
        for (Integer n : arrayIntegers) {
            System.out.println("Insert " + n);
            avl.insert(n);
            avl.prettyPrint();
        }
        System.out.println("Height: " + avl.height());
        AVLNode<Integer> s = avl.search(55); 
        System.out.println("Search 55: " + s + " left child: " + s.getLeft() + " right child: " + s.getRight());
        System.out.println("Min: " + avl.findMin());
        System.out.println("Max: " + avl.findMax());
        System.out.println("In-order: " + avl.inOrderTraversal());
        System.out.println("Pre-order: " + avl.preOrderTraversal());
        System.out.println("Post-order: " + avl.postOrderTraversal());
        System.out.println("Level-order: " + avl.levelOrderTraversal());
        avl.delete(20);
        avl.delete(50);
        System.out.println("After deleting 20 and 50:");
        avl.prettyPrint();
    }

    private static void testRBT() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        Integer[] arrayIntegers = { 20, 10, 110, 100, 50, 55, 70, 40, 15, 5 };
        for (Integer n : arrayIntegers) {
            System.out.println("Insert " + n);
            rbt.insert(n);
            rbt.prettyPrint();
        }
        System.out.println("Height: " + rbt.height());
        RBTNode<Integer> s = rbt.search(55); 
        System.out.println("Search 55: " + s + " left child: " + s.getLeft() + " right child: " + s.getRight());
        System.out.println("Min: " + rbt.findMin());
        System.out.println("Max: " + rbt.findMax());
        System.out.println("In-order: " + rbt.inOrderTraversal());
        System.out.println("Pre-order: " + rbt.preOrderTraversal());
        System.out.println("Post-order: " + rbt.postOrderTraversal());
        System.out.println("Level-order: " + rbt.levelOrderTraversal());
        rbt.delete(20);
        rbt.delete(50);
        System.out.println("After deleting 20 and 50:");
        rbt.prettyPrint();
    }

}
