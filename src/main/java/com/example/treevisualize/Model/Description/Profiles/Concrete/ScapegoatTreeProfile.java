package com.example.treevisualize.Model.Description.Profiles.Concrete;

import com.example.treevisualize.Controller.Operators.Delete.StandardDeleter;
import com.example.treevisualize.Controller.Operators.Insert.BinaryInserter;
import com.example.treevisualize.Controller.Operators.Search.StandardSearcher;
import com.example.treevisualize.Model.Description.ScapegoatDescription;
import com.example.treevisualize.Model.Description.Profiles.*;
import com.example.treevisualize.View.Layout.Strategy.BinarySkewAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Delete.ScapegoatDeleteStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.Insert.ScapegoatInsert;
import com.example.treevisualize.Model.Tree.ScapegoatTree;
import com.example.treevisualize.View.Visualizer.ScapegoatTreeRenderer;

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
