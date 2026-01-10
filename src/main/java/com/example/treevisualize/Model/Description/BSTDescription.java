package com.example.treevisualize.Model.Description;

public class BSTDescription extends TreeDescription {
    @Override
    protected String getTreeName() {
        return "Binary Search Tree";
    }

    @Override
    protected String getDefinition() {
        return "A rooted binary tree data structure with the key property: The value of each node must be greater than all values in its left subtree and less than all values in its right subtree.";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Search : O(log n) (Avg) / O(n) (Worst)
               - Insert : O(log n) (Avg) / O(n) (Worst)
               - Delete : O(log n) (Avg) / O(n) (Worst)
               
               * Note: Worst case O(n) happens when the tree becomes skewed (like a linked list).""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n)";
    }
}