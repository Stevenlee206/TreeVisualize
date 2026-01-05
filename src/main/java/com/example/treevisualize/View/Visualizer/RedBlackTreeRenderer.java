package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Node.NodeColor;
import com.example.treevisualize.Model.Node.NodeStatus;
import com.example.treevisualize.Model.Node.RedBlackTreeNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class RedBlackTreeRenderer implements TreeRenderer {

    @Override
    public void drawNode(GraphicsContext gc, Node node, double x, double y, double radius) {
        if (!(node instanceof RedBlackTreeNode)) return;

        RedBlackTreeNode rbNode = (RedBlackTreeNode) node;
        Color bgColor = getNodeColor(rbNode);

        // Vẽ nền
        gc.setFill(bgColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Viền
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // Text (node đỏ thì chữ trắng)
        gc.setFill(bgColor == Color.BLACK ? Color.WHITE : Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gc.fillText(String.valueOf(rbNode.getValue()), x, y + 5);
    }

    @Override
    public Color getNodeColor(Node node) {
        RedBlackTreeNode rbNode = (RedBlackTreeNode) node;

        // Highlight theo thuật toán
        if (rbNode.getStatus() == NodeStatus.ACTIVE) {
            return Color.ORANGE;
        }
        if (rbNode.getStatus() == NodeStatus.FOUND) {
            return Color.LIMEGREEN;
        }

        // Màu Red–Black Tree
        return (rbNode.getColor() == NodeColor.RED) ? Color.RED : Color.BLACK;
    }
}
