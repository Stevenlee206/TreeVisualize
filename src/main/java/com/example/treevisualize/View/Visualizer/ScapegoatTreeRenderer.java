package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Node.ScapegoatTreeNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScapegoatTreeRenderer extends BinaryTreeRenderer {

    /**
     * Override hàm drawNode để hiển thị thêm thông tin Size (Kích thước cây con).
     * Hàm này được gọi bởi TreeVisualizer sau khi đã tính toán xong toạ độ (x, y).
     *
     * @param gc     GraphicsContext để vẽ
     * @param node   Node hiện tại cần vẽ
     * @param x      Tọa độ X của node trên màn hình
     * @param y      Tọa độ Y của node trên màn hình
     * @param radius Bán kính của node
     */
    @Override
    public void drawNode(GraphicsContext gc, Node node, double x, double y, double radius) {
        // 1. Gọi renderer cha (BinaryTreeRenderer) để vẽ hình tròn nền, viền và giá trị số
        super.drawNode(gc, node, x, y, radius);

        // 2. Vẽ thêm thông tin Size bên cạnh node nếu đúng là node của Scapegoat Tree
        if (node instanceof ScapegoatTreeNode) {
            ScapegoatTreeNode sgNode = (ScapegoatTreeNode) node;

            // --- Cấu hình font chữ nhỏ, đậm ---
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            gc.setFill(Color.DARKMAGENTA); // Màu tím đặc trưng cho thông số Size

            // --- Vẽ text "Sz: [size]" ---
            // x + radius + 5: Vẽ lệch sang phải một chút để không đè lên hình tròn
            // y + 4: Căn giữa theo chiều dọc tương đối so với tâm node
            String infoText = "Sz: " + sgNode.getSize();

            gc.fillText(infoText, x + radius + 14, y + 4);
        }
    }
}