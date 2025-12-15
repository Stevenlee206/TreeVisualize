package com.example.treevisualize.Description;

public class BinaryTreeDescription implements Description {
    @Override
    public String getDescription() {
        return "Binary Tree is a tree in which each node has at most two children.\n\n" +
                "Complexity :\n" +
                "=======================================\n" +
                "1. Time Complexity :\n" +
                "   - Search :  O(n)\n" +
                "   - Insert :  O(n)\n" +
                "   - Delete :  O(n)\n\n" +
                "2. Space Complexity :\n" +
                "   - O(n)";
    }
}