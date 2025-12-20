package com.example.treevisualize.Description.Profiles;

import com.example.treevisualize.Description.Description;
import com.example.treevisualize.Layout.Strategy.NodeAlignmentStrategy;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.TreeRenderer;

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