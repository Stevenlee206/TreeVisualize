package com.example.treevisualize.View.Layout.Strategy;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.Node;

public class BinarySkewAlignmentStrategy implements NodeAlignmentStrategy {

    // Độ lệch để tạo góc nhìn ~45 độ (dựa trên LEVEL_GAP = 70)
    private final double SKEW_OFFSET = 35.0;

    @Override
    public double calculateParentX(Node parent, double firstChildX, double lastChildX) {
        // Mặc định vẫn tính trung điểm
        double midPoint = (firstChildX + lastChildX) / 2.0;

        if (parent instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) parent;
            int childrenCount = parent.getChildren().size();

            // Case 1: Chỉ có con TRÁI -> Cha lệch sang PHẢI (như thể có con phải tàng hình)
            if (bNode.getLeftChild() != null && childrenCount == 1) {
                return firstChildX + SKEW_OFFSET;
            }

            // Case 2: Chỉ có con PHẢI -> Cha lệch sang TRÁI (như thể có con trái tàng hình)
            else if (bNode.getRightChild() != null && childrenCount == 1) {
                // Lưu ý: Khi chỉ có 1 con thì firstChildX chính là toạ độ của con đó
                return firstChildX - SKEW_OFFSET;
            }
        }

        return midPoint;
    }
}