package com.example.treevisualize.Controller.Operators.Insert;

import com.example.treevisualize.Model.Tree.BinaryTree;
import com.example.treevisualize.Model.Tree.Tree;

public class NormalBinaryTreeInserter implements Inserter {

    @Override
    public void insert(Tree tree, int value) {
        if (tree instanceof BinaryTree && tree.getRoot() == null) {
            tree.insert(value);
        }
    }

    @Override
    public void insert(Tree tree, int parent, int child) {
        if (tree instanceof BinaryTree) {
            ((BinaryTree) tree).insert(parent, child);
        }
    }
}
