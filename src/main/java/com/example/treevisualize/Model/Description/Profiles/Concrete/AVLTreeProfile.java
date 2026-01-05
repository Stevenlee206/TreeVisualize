package com.example.treevisualize.Model.Description.Profiles.Concrete;

import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.AVLDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.AVLDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.AVLInsert;
import com.example.treevisualize.Model.Tree.AVLTree;
import com.example.treevisualize.View.Visualizer.AVLTreeRenderer;

public class AVLTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("AVL Tree", "/images/AVL_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                AVLTree::new,
                new BinaryInserter(),
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new AVLTreeRenderer(),
                new BinarySkewAlignmentStrategy(),
                new AVLDescription(),
                new AVLInsert(),
                new AVLDeleteStrategy(),
                null
        );
    }
}