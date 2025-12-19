package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.AVLTreeNode;
import com.example.treevisualize.Node.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AVLTreeRenderer extends BinaryTreeRenderer {

    @Override
    public void drawNode(GraphicsContext gc, Node node, double x, double y, double radius) {
        // 1. Gọi cha để vẽ hình tròn và số
        super.drawNode(gc, node, x, y, radius);

        // 2. Vẽ thêm thông tin AVL
        if (node instanceof AVLTreeNode) {
            AVLTreeNode avlNode = (AVLTreeNode) node;

            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));

            // Vẽ Height
            gc.setFill(Color.BLUE);
            gc.fillText("H: " + avlNode.getHeight(), x + radius + 2, y - 5);

            // Vẽ Balance Factor
            int bf = calculateBalanceFactor(avlNode);
            if (bf == 0) gc.setFill(Color.DARKGREEN);
            else if (Math.abs(bf) == 1) gc.setFill(Color.CHOCOLATE);
            else gc.setFill(Color.RED);

            gc.fillText("BF: " + bf, x + radius + 2, y + 10);
        }
    }

    private int calculateBalanceFactor(AVLTreeNode node) {
        int leftH = (node.getLeftChild() instanceof AVLTreeNode) ? ((AVLTreeNode) node.getLeftChild()).getHeight() : 0;
        int rightH = (node.getRightChild() instanceof AVLTreeNode) ? ((AVLTreeNode) node.getRightChild()).getHeight() : 0;
        return leftH - rightH;
    }
}