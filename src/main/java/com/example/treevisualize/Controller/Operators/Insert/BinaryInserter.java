package com.example.treevisualize.Controller.Operators.Insert;

import com.example.treevisualize.Model.Tree.BinaryTree;
import com.example.treevisualize.Model.Tree.Tree;

public class BinaryInserter implements Inserter {

    @Override
    public void insert(Tree tree, int value) {
        if (!(tree instanceof BinaryTree bt))
            throw new IllegalArgumentException("Not a BinaryTree");
        bt.insert(value); 
    }

    @Override
    public void insert(Tree tree, int parent, int child) {
        if (!(tree instanceof BinaryTree bt))
            throw new IllegalArgumentException("Not a BinaryTree");

        bt.insertChild(parent, child); 
    }
}
