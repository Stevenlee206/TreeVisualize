package com.example.treevisualize.Description;

public class BinaryTreeDescription extends TreeDescription {
    @Override
    protected String getTreeName() {
        return "Binary Tree (Normal)";
    }

    @Override
    protected String getDefinition() {
        return "A basic hierarchical structure where each node has at most two children (referred to as the Left child and Right child). Unlike BST, there is no strict ordering constraint between nodes.";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Search : O(n) (No ordering, must scan all)
               - Insert : O(n) (Typically Level-Order insertion)
               - Delete : O(n) (Search target + Swap deeply)""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n)";
    }
}
