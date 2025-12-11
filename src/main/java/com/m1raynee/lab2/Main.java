package com.m1raynee.lab2;

import com.m1raynee.lab2.AVL.AVLTree;
import com.m1raynee.lab2.RBT.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        testRBT();
    }
    
    private static void testAVL() {
        AVLTree<Integer> avl = new AVLTree<>();
        // avl.insert(1);
        // avl.insert(2);
        // avl.insert(3);
        // avl.insert(4);
        // avl.insert(5);
        // avl.insert(6);

        // 30 70 20 40 80 10 45
        avl.insert(30);
        avl.insert(70);
        avl.insert(20);
        avl.insert(40);
        avl.insert(80);
        avl.insert(10);
        avl.insert(45);
        avl.insert(65);
        avl.insert(50);
        avl.insert(60);


        avl.prettyPrint();
    }

    private static void testRBT() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        // 30 70 20 40 80 10 45
        // Integer[] arrayIntegers = {30, 70, 20, 40, 80, 10, 45, 65, 50, 60};

        // 20 10 110 100 50 55 70 40 15 5
        Integer[] arrayIntegers = {20, 10, 110, 100, 50, 55, 70, 40, 15, 5};
        for (Integer n : arrayIntegers) {
            System.out.println("Insert " + n);
            rbt.insert(n);
            rbt.prettyPrint();
        }

        System.out.println(rbt.search(20));
        System.out.println(rbt.findMin());
        System.out.println(rbt.findMax());
        System.out.println(rbt.inOrderTraversal());
        System.out.println(rbt.preOrderTraversal());
        System.out.println(rbt.postOrderTraversal());
        System.out.println(rbt.levelOrderTraversal());

        rbt.delete(55);
        rbt.prettyPrint();
    }
}

