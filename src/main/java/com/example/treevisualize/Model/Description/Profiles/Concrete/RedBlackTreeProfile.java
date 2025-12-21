package com.example.treevisualize.Model.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.*;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.RBTDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.RedBlackTreeInsert;
import com.example.treevisualize.Model.Tree.RedBlackTree;
import com.example.treevisualize.View.Visualizer.BinaryTreeRenderer;

public class RedBlackTreeProfile implements TreeProfile {

    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Red Black Tree", "/images/RBT_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                RedBlackTree::new,       // Factory
                new BinaryInserter(),    // Inserter
                new StandardDeleter(),   // Deleter
                new StandardSearcher()   // Searcher
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new BinaryTreeRenderer(),
                new BinarySkewAlignmentStrategy(),
                new RBTDescription(),
                new RedBlackTreeInsert(),
                new RBTDeleteStrategy(),
                null
        );
    }
}
