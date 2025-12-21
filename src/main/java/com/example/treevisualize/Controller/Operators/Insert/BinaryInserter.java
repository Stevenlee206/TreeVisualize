package com.example.treevisualize.Controller.Operators.Insert;


import com.example.treevisualize.Model.Tree.Tree;

public class BinaryInserter implements Inserter {
        @Override
        public void insert(Tree tree, int value) { tree.insert(value); }
}

