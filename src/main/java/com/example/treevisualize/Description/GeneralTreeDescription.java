package com.example.treevisualize.Description;

public class GeneralTreeDescription implements Description {
    @Override
    public String getDescription() {
        return "A general tree is a non-linear data structure that consists of nodes connected by edges. "
        		+ "Each node can have an arbitrary number of children, which means there is no fixed limit "
        		+ "on the number of child nodes a parent node can have. The topmost node is called the root, "
        		+ "and nodes without children are referred to as leaf nodes. General trees are often used to "
        		+ "represent hierarchical relationships, such as file systems or organizational structures.\n\n" +
                "Complexity :\n" +
                "=======================================\n" +
                "1. Time Complexity :\n" +
                "   - Search :  O(n)\n" +
                "   - Insert :  O(n)\n" +
                "   - Delete :  O(n)\n" +
                "2. Space Complexity :\n" +
                "   - O(n)";
    }
}