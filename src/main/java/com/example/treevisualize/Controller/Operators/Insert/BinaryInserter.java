package com.example.treevisualize.Controller.Operators.Insert;


import com.example.treevisualize.Trees.Tree;

public class BinaryInserter implements Inserter {
        @Override
        public void insert(Tree tree, int value) { tree.insert(value); }
}

