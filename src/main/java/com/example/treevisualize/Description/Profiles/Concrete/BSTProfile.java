package com.example.treevisualize.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Description.BSTDescription;
import com.example.treevisualize.Description.Profiles.*;
import com.example.treevisualize.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.Delete.BSTDeleteStrategy;
import com.example.treevisualize.PseudoCodeStore.Insert.BSTInsert;
import com.example.treevisualize.Trees.BinarySearchTree;
import com.example.treevisualize.Visualizer.BinaryTreeRenderer;

public class BSTProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Binary Search Tree", "/images/BST_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                BinarySearchTree::new,
                new BinaryInserter(),
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new BinaryTreeRenderer(),
                new BinarySkewAlignmentStrategy(),
                new BSTDescription(),
                new BSTInsert(),
                new BSTDeleteStrategy(),
                null
        );
    }
}