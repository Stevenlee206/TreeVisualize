package com.example.treevisualize.Model.Description.Profiles.Concrete;


import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.TreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.GeneralTreeDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.Model.PseudoCodeStore.Search.GTSearch;
import com.example.treevisualize.View.Layout.Strategy.DefaultAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.GTDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.GeneralTreeInsert;
import com.example.treevisualize.Model.Tree.GeneralTree;
import com.example.treevisualize.View.Visualizer.GeneralTreeRenderer;

public class GeneralTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("General Tree", "/images/GT_icon.png", true);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                GeneralTree::new,
                new TreeInserter(),
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
                new GTSearch()
        );
    }
}