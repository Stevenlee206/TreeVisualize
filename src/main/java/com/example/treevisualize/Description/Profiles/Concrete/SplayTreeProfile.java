package com.example.treevisualize.Description.Profiles.Concrete;

import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Description.SplayTreeDescription;
import com.example.treevisualize.Description.Profiles.*;
import com.example.treevisualize.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.Delete.SplayDeleteStrategy;
import com.example.treevisualize.PseudoCodeStore.Insert.SplayInsertStrategy;
import com.example.treevisualize.Trees.SplayTree;
import com.example.treevisualize.Visualizer.BinaryTreeRenderer;

public class SplayTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Splay Tree", "/images/Splay_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                SplayTree::new,
                new BinaryInserter(),
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
                null
        );
    }
}
