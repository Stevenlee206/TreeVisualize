package com.example.treevisualize.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.GeneralTreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Description.BinaryTreeDescription;
import com.example.treevisualize.Description.Profiles.*;
import com.example.treevisualize.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.Delete.BSTDeleteStrategy;
import com.example.treevisualize.PseudoCodeStore.Insert.BTInsert;
import com.example.treevisualize.Trees.BinaryTree;
import com.example.treevisualize.Visualizer.BinaryTreeRenderer;

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
                new GeneralTreeInserter(), // Quan trọng: Dùng GeneralInserter để hỗ trợ chèn (parent, child)
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
                null
        );
    }
}