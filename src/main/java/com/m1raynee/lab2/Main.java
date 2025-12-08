package com.m1raynee.lab2;

import com.m1raynee.lab2.AVL.AVLTree;

public class Main {
    public static void main(String[] args) {
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
}

