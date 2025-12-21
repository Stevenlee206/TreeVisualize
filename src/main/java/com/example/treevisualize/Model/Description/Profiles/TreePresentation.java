package com.example.treevisualize.Model.Description.Profiles;

import com.example.treevisualize.Model.Description.Description;
import com.example.treevisualize.View.Layout.Strategy.NodeAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.TreeRenderer;

public class TreePresentation {
    private final TreeRenderer renderer;
    private final NodeAlignmentStrategy layoutStrategy;
    private final Description description;
    private final PseudoCodeStrategy insertCode;
    private final PseudoCodeStrategy deleteCode;
    private final PseudoCodeStrategy searchCode; // [MỚI] Thêm Search Strategy

    public TreePresentation(TreeRenderer renderer, NodeAlignmentStrategy layout,
                            Description description,
                            PseudoCodeStrategy insertCode, PseudoCodeStrategy deleteCode,PseudoCodeStrategy searchCode) {
        this.renderer = renderer;
        this.layoutStrategy = layout;
        this.description = description;
        this.insertCode = insertCode;
        this.deleteCode = deleteCode;
        this.searchCode = searchCode;
    }

    public TreeRenderer getRenderer() { return renderer; }
    public NodeAlignmentStrategy getLayoutStrategy() { return layoutStrategy; }
    public String getDescriptionText() { return description.getDescription(); }
    public PseudoCodeStrategy getInsertCode() { return insertCode; }
    public PseudoCodeStrategy getDeleteCode() { return deleteCode; }
    public PseudoCodeStrategy getSearchCode() { return searchCode; }
}