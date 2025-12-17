package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BinaryTreeRenderer implements TreeRenderer {

    @Override
    public void renderChildren(GraphicsContext gc, Node node, double x, double y, double hGap, TreeVisualizer visualizer) {
        if (!(node instanceof BinaryTreeNode)) return;
        BinaryTreeNode bNode = (BinaryTreeNode) node;

        double nextHGap = hGap * 0.5;
        double nextY = y + TreeVisualizer.VERTICAL_GAP;
        if (bNode.getLeftChild() != null) {
            double leftX = x - hGap;
            gc.strokeLine(x, y, leftX, nextY);
            visualizer.calculateLayout(bNode.getLeftChild(), leftX, nextY, nextHGap);
        }

        if (bNode.getRightChild() != null) {
            double rightX = x + hGap;
            gc.strokeLine(x, y, rightX, nextY);
            visualizer.calculateLayout(bNode.getRightChild(), rightX, nextY, nextHGap);
        }
    }

    @Override
    public Color getNodeColor(Node node) {
        if (node instanceof RedBlackTreeNode) {
            RedBlackTreeNode rbNode = (RedBlackTreeNode) node;
            return (rbNode.getColor() == NodeColor.RED) ? Color.RED : Color.BLACK;
        }
        return Color.GREEN;
    }
}
