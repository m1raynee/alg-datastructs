package com.m1raynee.lab2;

import com.m1raynee.lab2.BST.BSTNode;
import com.m1raynee.lab2.BST.BaseBST;
import com.m1raynee.lab2.AVL.AVLTree;
import com.m1raynee.lab2.RBT.RedBlackTree;

import java.util.Set;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.PrintWriter;

public class Experiments {

    // Получить экспериментальную зависимость высоты BST
    // от количества ключей, при условии, что значение ключа
    // - случайная величина, распределенная равномерно.
    public static void experiment4_old() {
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};
        int[] trials = {5000, 2000, 1000, 750, 600, 500, 300, 200, 100};

        Path out = Path.of("src/main/resources/experiment4.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(out))) {
            w.println("size,average_height");
            for (int i = 0; i < sizes.length; i++) {
                int size = sizes[i];
                int trial = trials[i];
                int heightSum = 0;
                
                for (int t = 0; t < trial; t++) {
                    Set<Integer> keys = new java.util.HashSet<>();
                    while (keys.size() < size) {
                        int key = (int) (Math.random() * Integer.MAX_VALUE);
                        keys.add(key);
                    }
                    BaseBST<Integer, BSTNode<Integer>> bst = new BaseBST<>();
                    for (int key : keys) {
                        bst.insert(key);
                    }
                    heightSum += bst.height();
                    System.out.println("Completed trial " + (t + 1) + " for size " + size);
                }
                double averageHeight = (double) heightSum / trial;
                w.println(size + "," + averageHeight);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Integer> generateUniqueRandomKeys(int size) {
        Set<Integer> keys = new java.util.HashSet<>();
        while (keys.size() < size) {
            int key = (int) (Math.random() * Integer.MAX_VALUE);
            keys.add(key);
        }
        return keys;
    }

    public static void experiment4(int size) {
        Set<Integer> keys = generateUniqueRandomKeys(size);
        BaseBST<Integer, BSTNode<Integer>> bst = new BaseBST<>();

        Path p = Path.of("src/main/resources/experiment4_single.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(p))) {
            w.println("n,height");

            int count = 0;
            for (int key : keys) {
                bst.insert(key);
                int height = bst.height();
                count++;
                w.println(count + "," + height);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void experiment5_AVL() {
        Set<Integer> keys = generateUniqueRandomKeys(50_000);
        AVLTree<Integer> avl = new AVLTree<>();

        Path p = Path.of("src/main/resources/experiment5_avl.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(p))) {
            w.println("n,height");

            int count = 0;
            for (int key : keys) {
                avl.insert(key);
                int height = avl.height();
                count++;
                w.println(count + "," + height);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void experiment5_RBT() {
        Set<Integer> keys = generateUniqueRandomKeys(50_000);
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        Path p = Path.of("src/main/resources/experiment5_rbt.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(p))) {
            w.println("n,height");

            int count = 0;
            for (int key : keys) {
                rbt.insert(key);
                int height = rbt.height();
                count++;
                w.println(count + "," + height);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void experiment6_AVL() {
        AVLTree<Integer> avl = new AVLTree<>();

        Path p = Path.of("src/main/resources/experiment6_avl.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(p))) {
            w.println("n,height");

            for (int key = 1; key <= 50_000; key++) {
                avl.insert(key);
                int height = avl.height();
                w.println(key + "," + height);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void experiment6_RBT() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        Path p = Path.of("src/main/resources/experiment6_rbt.csv");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(p))) {
            w.println("n,height");

            for (int key = 1; key <= 50_000; key++) {
                rbt.insert(key);
                int height = rbt.height();
                w.println(key + "," + height);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // experiment4(50_000);

        // experiment5_AVL();
        // experiment5_RBT();
        
        experiment6_AVL();
        experiment6_RBT();
    }
}
