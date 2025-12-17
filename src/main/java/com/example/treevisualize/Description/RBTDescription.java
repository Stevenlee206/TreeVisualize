package com.example.treevisualize.Description;

public class RBTDescription extends TreeDescription {

    @Override
    protected String getTreeName() {
        return "Red-Black Tree";
    }

    @Override
    protected String getDefinition() {
        return "A self-balancing binary search tree where each node has an extra bit representing 'color' (Red or Black). It ensures the tree remains balanced during insertions and deletions.";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Search : O(log n)
               - Insert : O(log n)
               - Delete : O(log n)""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n)";
    }
}
