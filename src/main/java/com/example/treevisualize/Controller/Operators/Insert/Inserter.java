package com.example.treevisualize.Controller.Operators.Insert;
import com.example.treevisualize.Trees.Tree;

public interface Inserter {
        void insert(Tree tree, int value);

        default void insert(Tree tree, int parentValue, int childValue) {
            System.err.println("Operation not supported.");
        }
}

