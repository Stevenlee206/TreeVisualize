package com.example.treevisualize.Controller.Operators.Insert;

import com.example.treevisualize.Model.Tree.Tree;

public class TreeInserter implements Inserter {

    @Override
    public void insert(Tree tree, int value) {
        if (tree != null) {
            tree.insert(value);
        }
    }

    @Override
    public void insert(Tree tree, int parent, int child) {
        if (tree != null) {
            tree.insert(parent, child);
        }
    }
}