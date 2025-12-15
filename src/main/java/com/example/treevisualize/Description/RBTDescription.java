package com.example.treevisualize.Description;

public class RBTDescription implements Description {
    @Override
    public String getDescription() {
        return "Red-Black Tree is a self-balancing binary search tree data structure . The nodes in a red-black tree hold an extra \"color\" bit, often drawn as red and black.\n\n" +
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