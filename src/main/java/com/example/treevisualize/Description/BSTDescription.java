package com.example.treevisualize.Description;

public class BSTDescription implements Description {
    @Override
    public String getDescription() {
        return "Binary Search Tree binary tree with the key of each internal node being greater than all the keys in the respective node's left subtree and less than the ones in its right subtree.\n\n" +
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