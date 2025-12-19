package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface TreeRenderer {
    // Nhiệm vụ mới: Vẽ hình tròn node, số, và màu sắc tại toạ độ (x, y)
    void drawNode(GraphicsContext gc, Node node, double x, double y, double radius);

    // Hàm này giữ nguyên để lấy màu
    Color getNodeColor(Node node);
}