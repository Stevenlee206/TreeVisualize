package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.GeneralTreeNode;
import com.example.treevisualize.Model.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class GeneralTreeRenderer implements TreeRenderer {

    /**
     * Thay đổi từ renderChildren -> drawNode.
     * Hàm này chỉ chịu trách nhiệm vẽ hình tròn và giá trị của node tại toạ độ (x, y).
     * Không còn đệ quy, không còn vẽ đường nối (strokeLine).
     */
    @Override
    public void drawNode(GraphicsContext gc, Node node, double x, double y, double radius) {
        if (!(node instanceof GeneralTreeNode)) return;

        // 1. Lấy màu (mặc định là BLUE theo code cũ của bạn)
        Color bgColor = getNodeColor(node);

        // 2. Vẽ hình tròn nền
        gc.setFill(bgColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // 3. Vẽ viền đen
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // 4. Vẽ giá trị (Value)
        // Kiểm tra màu nền để chọn màu chữ (Trắng/Đen) cho dễ đọc
        gc.setFill(ColorUtils.isDark(bgColor) ? Color.WHITE : Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // +5 để căn giữa theo chiều dọc
        gc.fillText(String.valueOf(node.getValue()), x, y + 5);
    }

    @Override
    public Color getNodeColor(Node node) {
        return Color.BLUE; // Giữ nguyên màu xanh đặc trưng của General Tree
    }
}