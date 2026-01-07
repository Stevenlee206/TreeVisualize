package com.example.treevisualize.Model.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.TreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.BinaryTreeDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.BSTDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.BTInsert;
import com.example.treevisualize.Model.Tree.BinaryTree;
import com.example.treevisualize.View.Visualizer.BinaryTreeRenderer;
import com.example.treevisualize.Model.PseudoCodeStore.Search.BinaryTreeSearchStrategy;

public class NormalBinaryTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        // requiresParentInput = true
        return new TreeMetadata("Binary Tree (Normal)", "/images/BT_icon.png", true);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                BinaryTree::new,
                new TreeInserter(), // Quan trọng: Dùng GeneralInserter để hỗ trợ chèn (parent, child)
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new BinaryTreeRenderer(),
                new BinarySkewAlignmentStrategy(),
                new BinaryTreeDescription(),
                new BTInsert(),
                new BSTDeleteStrategy() ,// Thường dùng chung chiến lược xóa với BST hoặc BTDelete riêng nếu có,
                new BinaryTreeSearchStrategy()
        );
    }
}