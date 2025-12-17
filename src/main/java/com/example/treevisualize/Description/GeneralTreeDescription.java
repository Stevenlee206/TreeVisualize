package com.example.treevisualize.Description;

public class GeneralTreeDescription extends TreeDescription {

    @Override
    protected String getTreeName() {
        return "General Tree";
    }

    @Override
    protected String getDefinition() {
        return "A tree data structure where each node can have an arbitrary number of children. There is no specific ordering constraint between siblings.";
    }

    @Override
    protected String getTimeComplexity() {
        return """
               - Search : O(n)
               - Insert : O(1) (Adding child)
               - Delete : O(n)""";
    }

    @Override
    protected String getSpaceComplexity() {
        return "O(n)";
    }
}