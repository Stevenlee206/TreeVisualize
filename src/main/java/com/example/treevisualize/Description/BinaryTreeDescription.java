package com.example.treevisualize.Description;

public class BinaryTreeDescription implements Description {
    @Override
    public String getDescription() {
        return "Binary Tree is a non-linear and hierarchical data structure where each node has at "
        		+ "most two children referred to as the left child and the right child. The topmost node "
        		+ "in a binary tree is called the root, and the bottom-most nodes(having no children) are "
        		+ "called leaves.\n\n" +
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