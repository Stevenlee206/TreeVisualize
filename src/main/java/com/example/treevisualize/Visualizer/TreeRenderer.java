package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface TreeRenderer {
    void renderChildren(GraphicsContext gc, Node node, double x, double y, double hGap, TreeVisualizer visualizer);
    Color getNodeColor(Node node);
}