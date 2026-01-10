package com.example.treevisualize.Model.Description;

public class SplayTreeDescription extends TreeDescription {

    @Override
    protected String getTreeName() {
        return "Splay Tree";
    }

    @Override
    protected String getDefinition() {
        return "A Splay Tree is a self-adjusting binary search tree with the additional property " +
                "that recently accessed elements are quick to access again.\n" +
                "Key Feature: Splaying\n" +
                "Every operation (Insert, Search, Delete) moves the accessed node to the root " +
                "using a sequence of rotations (Zig, Zig-Zig, Zig-Zag). This tends to keep " +
                "active nodes near the top, making it excellent for caches and frequent-access scenarios.";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Average (Amortized): O(log n)
               - Worst Case: O(n) (linear, e.g., accessing elements in sequential order)
               *Note: While a single operation can be slow (O(n)), a sequence of M operations 
               takes O(M log n) time, ensuring good long-term performance.*""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n) (Standard node storage)";
    }
}