package com.example.treevisualize.Model.Description.Profiles.Concrete;

import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.TreeInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.SplayTreeDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.SplayDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.SplayInsertStrategy;
import com.example.treevisualize.Model.Tree.SplayTree;
import com.example.treevisualize.View.Visualizer.BinaryTreeRenderer;
import com.example.treevisualize.Model.PseudoCodeStore.Search.SplayTreeSearchStrategy;
public class SplayTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Splay Tree", "/images/Splay_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                SplayTree::new,
                new TreeInserter(),
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new BinaryTreeRenderer(), // Splay thường không cần Renderer riêng, dùng chung Binary
                new BinarySkewAlignmentStrategy(),
                new SplayTreeDescription(),
                new SplayInsertStrategy(),
                new SplayDeleteStrategy(),
                new SplayTreeSearchStrategy()
        );
    }
}
