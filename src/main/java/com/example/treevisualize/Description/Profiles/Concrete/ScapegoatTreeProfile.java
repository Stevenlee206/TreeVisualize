package com.example.treevisualize.Description.Profiles.Concrete;

import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Description.ScapegoatDescription;
import com.example.treevisualize.Description.Profiles.*;
import com.example.treevisualize.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.Delete.ScapegoatDeleteStrategy;
import com.example.treevisualize.PseudoCodeStore.Insert.ScapegoatInsert;
import com.example.treevisualize.Trees.ScapegoatTree;
import com.example.treevisualize.Visualizer.ScapegoatTreeRenderer;

public class ScapegoatTreeProfile implements TreeProfile {
    @Override
    public TreeMetadata getMetadata() {
        return new TreeMetadata("Scapegoat Tree", "/images/Scapegoat_icon.png", false);
    }

    @Override
    public TreeOperations getOperations() {
        return new TreeOperations(
                ScapegoatTree::new,
                new BinaryInserter(),
                new StandardDeleter(),
                new StandardSearcher()
        );
    }

    @Override
    public TreePresentation getPresentation() {
        return new TreePresentation(
                new ScapegoatTreeRenderer(),
                new BinarySkewAlignmentStrategy(),
                new ScapegoatDescription(),
                new ScapegoatInsert(),
                new ScapegoatDeleteStrategy(),
                null
        );
    }
}
