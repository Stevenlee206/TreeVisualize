package com.example.treevisualize.Description;

public class RBTDescription implements Description {
    @Override
    public String getDescription() {
        return "A Red-Black Tree is a type of binary search tree (BST) that includes an "
        		+ "additional attribute for each node: a color, which can be either red or black. "
        		+ "This color-coding helps maintain the tree's balance during insertions and "
        		+ "deletions, ensuring that the height of the tree remains logarithmic relative "
        		+ "to the number of nodes, specifically O(log n).\n\n" +
                "COMPLEXITY:\n" +
                "------------------------------------\n" +
                "1. Time Complexity :\n" +
                "   - Search : O(log n)\n" +
                "   - Insert : O(log n)\n" +
                "   - Delete: O(log n)\n\n" +
                "2. Space Complexity :\n" +
                "   - O(n).";
    }
}