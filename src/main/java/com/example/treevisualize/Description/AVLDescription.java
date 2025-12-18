package com.example.treevisualize.Description;

public class AVLDescription extends TreeDescription {

    @Override
    protected String getTreeName() {
        return "AVL Tree (Adelson-Velsky and Landis)";
    }

    @Override
    protected String getDefinition() {
        return "AVL Tree is a self-balancing Binary Search Tree (BST). The difference between " +
                "heights of left and right subtrees cannot be more than one for all nodes (Balance Factor: -1, 0, 1).\n\n" +
                "Mechanism:\n" +
                "If at any time heights differ by more than one, rebalancing is performed " +
                "to restore this property using Rotations (LL, RR, LR, RL).";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Search: O(log n)
               - Insert: O(log n)
               - Delete: O(log n)
               
               *Note: Because it is strictly balanced, AVL Tree guarantees O(log n) even in worst-case scenarios.*""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n) (Standard node storage)";
    }
}