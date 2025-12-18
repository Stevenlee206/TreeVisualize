package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.ScapegoatTreeNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScapegoatTreeRenderer extends BinaryTreeRenderer {

    @Override
    public void renderChildren(GraphicsContext gc, Node node, double x, double y, double hGap, TreeVisualizer visualizer) {
        // 1. Gọi renderer cha để vẽ các đường nối (edges) và đệ quy vẽ con
        super.renderChildren(gc, node, x, y, hGap, visualizer);

        // 2. Vẽ hiển thị Size của cây con tại node hiện tại
        if (node instanceof ScapegoatTreeNode) {
            ScapegoatTreeNode sgNode = (ScapegoatTreeNode) node;

            // Cấu hình font chữ hiển thị
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            gc.setFill(Color.DARKMAGENTA); // Màu tím đậm đặc trưng cho Scapegoat

            // Vẽ dòng chữ "Sz: [size]" ngay bên cạnh phải của Node
            // x + 22: Để không đè lên hình tròn node (giả định bán kính ~20)
            // y + 5: Căn chỉnh theo chiều dọc
            String infoText = "Sz: " + sgNode.getSize();
            gc.fillText(infoText, x + 22, y + 4);

            // (Tuỳ chọn) Bạn có thể vẽ thêm thông tin Alpha nếu muốn debug kỹ hơn
            // Ví dụ: tỉ lệ size con / size cha
        }
    }
}