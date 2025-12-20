package com.example.treevisualize.Controller.Operators.Insert;

import com.example.treevisualize.Trees.GeneralTree;
import com.example.treevisualize.Trees.Tree;

public class GeneralTreeInserter implements Inserter {
    @Override
    public void insert(Tree tree, int value) {
        if (tree.getRoot() == null && tree instanceof GeneralTree){
            ((GeneralTree) tree).insert(-1, value);
        }
    }
    @Override
    public void insert(Tree tree, int p, int c) {
        if (tree instanceof GeneralTree) ((GeneralTree) tree).insert(p, c);
    }
}
