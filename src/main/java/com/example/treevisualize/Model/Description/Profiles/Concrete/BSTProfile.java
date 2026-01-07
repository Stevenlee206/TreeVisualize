package com.example.treevisualize.Model.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.TreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.BSTDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.BSTDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.BSTInsert;
import com.example.treevisualize.Model.Tree.BinarySearchTree;
import com.example.treevisualize.View.Visualizer.BinaryTreeRenderer;
import com.example.treevisualize.Model.PseudoCodeStore.Search.BSTSearchStrategy;

public class BSTProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Binary Search Tree", "/images/BST_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                BinarySearchTree::new,
                new TreeInserter(),
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
                new BSTSearchStrategy()
        );
    }
}