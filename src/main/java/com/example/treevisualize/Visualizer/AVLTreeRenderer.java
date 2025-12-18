package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.AVLTreeNode;
import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AVLTreeRenderer extends BinaryTreeRenderer {

    /**
     * Override hàm drawNode để hiển thị thêm thông tin Height và Balance Factor
     */
    @Override
    public void renderChildren(GraphicsContext gc, Node node, double x, double y, double hGap,TreeVisualizer visualizer) {
        // 1. Gọi renderer cha để vẽ hình tròn, số, màu sắc cơ bản
        super.renderChildren(gc, node, x, y, hGap,visualizer);

        // 2. Vẽ thêm thông tin bổ sung nếu là AVLTreeNode
        if (node instanceof AVLTreeNode) {
            AVLTreeNode avlNode = (AVLTreeNode) node;

            // --- Cấu hình Font chữ nhỏ ---
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));

            gc.setFill(Color.BLUE);
            gc.fillText("H: " + avlNode.getHeight(), x + 22, y - 5);
            // --- 2. Hiển thị Balance Factor (BF) ---
            // BF không lưu trong node nên ta tính nhanh tại chỗ: Left - Right
            int bf = calculateBalanceFactor(avlNode);
            // Đổi màu cảnh báo nếu BF sắp mất cân bằng (-1 hoặc 1)
            if (bf == 0) {
                gc.setFill(Color.DARKGREEN); // Cân bằng tuyệt đối
            } else if (Math.abs(bf) == 1) {
                gc.setFill(Color.CHOCOLATE); // Hơi lệch
            } else {
                gc.setFill(Color.RED); // Mất cân bằng (Thường ít khi thấy vì cây tự xoay ngay)
            }

            // Vẽ ở góc dưới bên phải của node
            gc.fillText("BF: " + bf, x + 22, y + 8);        }
    }

    // Hàm phụ trợ tính BF để hiển thị
    private int calculateBalanceFactor(AVLTreeNode node) {
        int leftH = 0;
        int rightH = 0;

        if (node.getLeftChild() instanceof AVLTreeNode) {
            leftH = ((AVLTreeNode) node.getLeftChild()).getHeight();
        }
        if (node.getRightChild() instanceof AVLTreeNode) {
            rightH = ((AVLTreeNode) node.getRightChild()).getHeight();
        }

        return leftH - rightH;
    }
}
