package com.example.treevisualize.Controller.SubSystems.Playback.Renderer;

import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeSnapShot;
import com.example.treevisualize.Visualizer.TreeVisualizer;

public class JavaFXStaticRenderer implements StaticRenderer {
    private final Tree tree;
    private final TreeVisualizer visualizer;
    private final PseudoCodeBlock pseudoCode;

    public JavaFXStaticRenderer(Tree tree, TreeVisualizer visualizer, PseudoCodeBlock pseudoCode) {
        this.tree = tree;
        this.visualizer = visualizer;
        this.pseudoCode = pseudoCode;
    }

    @Override
    public void render(TreeSnapShot snapshot) {
        if (snapshot == null) return;

        // 1. Cập nhật Model (Cây ngầm định)
        tree.setRoot(snapshot.getRootCopy());

        // 2. Cập nhật View (Vẽ lại Canvas)
        visualizer.render();

        // 3. Cập nhật Text (Highlight mã giả)
        if (pseudoCode != null) {
            pseudoCode.highlightLine(snapshot.getPseudoLineIndex());
        }
    }
}