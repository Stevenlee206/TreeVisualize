package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class BinaryTreeRenderer implements TreeRenderer {

    @Override
    public void drawNode(GraphicsContext gc, Node node, double x, double y, double radius) {
        // 1. Lấy màu nền
        Color bgColor = getNodeColor(node);

        // 2. Vẽ hình tròn nền
        gc.setFill(bgColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // 3. Vẽ viền (Stroke)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // 4. Vẽ giá trị (Value) ở giữa
        gc.setFill(ColorUtils.isDark(bgColor) ? Color.WHITE : Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gc.fillText(String.valueOf(node.getValue()), x, y + 5); // +5 để căn giữa dọc
    }

    @Override
    public Color getNodeColor(Node node) {
        if (node instanceof RedBlackTreeNode) {
            RedBlackTreeNode rbNode = (RedBlackTreeNode) node;
            return (rbNode.getColor() == NodeColor.RED) ? Color.RED : Color.BLACK;
        }
        return Color.LIGHTGREEN;
    }
}