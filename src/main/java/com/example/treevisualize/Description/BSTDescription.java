package com.example.treevisualize.Description;

public class BSTDescription implements Description {
    @Override
    public String getDescription() {
        return "A Binary Search Tree (BST) is a type of binary tree data structure in which each node "
        		+ "contains a unique key and satisfies a specific ordering property:\r\n"
        		+ "+ All nodes in the left subtree of a node contain values strictly less than the node’s value.\r\n"
        		+ "+ All nodes in the right subtree of a node contain values strictly greater than the node’s value.\n\n" +
                "Complexity :\n" +
                "=======================================\n" +
                "1. Time Complexity :\n" +
                "   - Search :  O(log n)\n" +
                "   - Insert :  O(log n)\n" +
                "   - Delete :  O(log n)\n\n" +
                "2. Space Complexity :\n" +
                "   - O(n)";
    }
}