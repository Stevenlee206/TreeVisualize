package com.example.treevisualize.Controller.Operators.Delete;

import com.example.treevisualize.Model.Tree.Tree;

public class StandardDeleter implements Deleter {
    @Override
    public void delete(Tree tree, int value) { tree.delete(value); }
}
