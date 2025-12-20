package com.example.treevisualize.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.GeneralTreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Description.GeneralTreeDescription;
import com.example.treevisualize.Description.Profiles.*;
import com.example.treevisualize.Layout.Strategy.DefaultAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.Delete.GTDeleteStrategy;
import com.example.treevisualize.PseudoCodeStore.Insert.GeneralTreeInsert;
import com.example.treevisualize.Trees.GeneralTree;
import com.example.treevisualize.Visualizer.GeneralTreeRenderer;

public class GeneralTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("General Tree", "/images/GT_icon.png", true);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                GeneralTree::new,
                new GeneralTreeInserter(),
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new GeneralTreeRenderer(),
                new DefaultAlignmentStrategy(), // Layout căn giữa
                new GeneralTreeDescription(),
                new GeneralTreeInsert(),
                new GTDeleteStrategy(),
                null
        );
    }
}