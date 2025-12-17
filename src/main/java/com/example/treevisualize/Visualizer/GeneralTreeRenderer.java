package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GeneralTreeRenderer implements TreeRenderer {

    @Override
    public void renderChildren(GraphicsContext gc, Node node, double x, double y, double hGap, TreeVisualizer visualizer) {
        if (!(node instanceof GeneralTreeNode)) return;
        GeneralTreeNode gNode = (GeneralTreeNode) node;

        double nextHGap = hGap * 0.5;
        double nextY = y + TreeVisualizer.VERTICAL_GAP;
        Node child = gNode.getLeftMostChild();
        double childX = x - hGap;

        while (child != null) {
            gc.strokeLine(x, y, childX, nextY);
            visualizer.calculateLayout(child, childX, nextY, nextHGap);
            child = ((GeneralTreeNode) child).getRightSibling();
            childX += hGap;
        }
    }

    @Override
    public Color getNodeColor(Node node) {
        return Color.BLUE;
    }
}