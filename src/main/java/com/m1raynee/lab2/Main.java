package com.m1raynee.lab2;

import com.m1raynee.lab2.AVL.AVLTree;

public class Main {
    public static void main(String[] args) {
        // BaseBST<Integer, BSTNode<Integer>> bst = new BaseBST<>();

        // bst.insert(50);
        // bst.insert(30);
        // bst.insert(70);
        // bst.insert(20);
        // bst.insert(40);
        // bst.insert(60);
        // bst.insert(80);
        // bst.insert(75); 
        // bst.insert(85);
        
        // bst.prettyPrint();
        // bst.delete(50);
        // bst.insert(50);
        // bst.prettyPrint();

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

