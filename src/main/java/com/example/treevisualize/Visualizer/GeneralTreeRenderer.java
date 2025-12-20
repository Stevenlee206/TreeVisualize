package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GeneralTreeRenderer implements TreeRenderer {

    @Override
    public void renderChildren(GraphicsContext gc,
                               Node node,
                               double x,
                               double y,
                               double hGap,
                               TreeVisualizer visualizer) {

        if (!(node instanceof GeneralTreeNode)) return;
        GeneralTreeNode parent = (GeneralTreeNode) node;

        double nextY = y + TreeVisualizer.VERTICAL_GAP;
        double nextHGap = hGap * 0.6;

        GeneralTreeNode child =
                (GeneralTreeNode) parent.getLeftMostChild();

        double totalWidth = 0;
        GeneralTreeNode temp = child;
        while (temp != null) {
            totalWidth += temp.getSubtreeSize() * hGap;
            temp = (GeneralTreeNode) temp.getRightSibling();
        }
        double childX = x - totalWidth / 2;
        while (child != null) {

            double subtreeWidth = child.getSubtreeSize() * hGap;
            double centerX = childX + subtreeWidth / 2;

            // draw edge
            gc.strokeLine(x, y, centerX, nextY);

            // recursive layout
            visualizer.calculateLayout(
                    child,
                    centerX,
                    nextY,
                    nextHGap
            );

            childX += subtreeWidth;
            child = (GeneralTreeNode) child.getRightSibling();
        }
    }

    @Override
    public Color getNodeColor(Node node) {
        return Color.BLUE;
    }
}