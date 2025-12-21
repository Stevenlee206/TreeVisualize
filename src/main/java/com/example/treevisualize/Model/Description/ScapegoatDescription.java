package com.example.treevisualize.Model.Description;

public class ScapegoatDescription extends TreeDescription {
    @Override protected String getTreeName() { return "Scapegoat Tree"; }
    @Override protected String getDefinition() {
        return "A self-balancing BST based on 'weight-balancing' rather than height. " +
                "It does not store height or color. When a subtree becomes unbalanced (finding a 'scapegoat'), " +
                "it completely rebuilds that subtree into a perfectly balanced shape.";
    }
    @Override protected String getTimeComplexity() {
        return "- Amortized Search/Insert/Delete: O(log n)\n" +
                "- Rebuild operation: O(n) (but happens rarely)";
    }
    @Override protected String getSpaceComplexity() { return "O(n)"; }
}