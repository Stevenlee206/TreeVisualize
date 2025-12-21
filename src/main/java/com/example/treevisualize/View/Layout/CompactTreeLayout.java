package com.example.treevisualize.View.Layout;

import com.example.treevisualize.Model.Node.Node;
import javafx.geometry.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompactTreeLayout implements TreeLayout {
    private final double X_GAP = 50;
    private final double Y_GAP = 60;
    private int currentXIndex = 0;

    @Override
    public Map<Node, Point2D> calculateLayout(Node root) {
        Map<Node, Point2D> positions = new HashMap<>();
        currentXIndex = 0;
        processNode(root, 0, positions);
        return positions;
    }

    // Đổi tên từ inorderTraversal thành processNode để tổng quát hơn
    private void processNode(Node node, int depth, Map<Node, Point2D> positions) {
        if (node == null) return;

        List<Node> children = node.getChildren();
        int mid = children.size() / 2;

        // 1. Duyệt một nửa số con đầu (Tương ứng nhánh Trái trong Binary)
        for (int i = 0; i < mid; i++) {
            processNode(children.get(i), depth + 1, positions);
        }

        // 2. Gán toạ độ cho Node hiện tại (Ở giữa)
        double x = currentXIndex * X_GAP;
        double y = depth * Y_GAP + 50;
        positions.put(node, new Point2D(x, y));
        currentXIndex++;

        // 3. Duyệt phần còn lại (Tương ứng nhánh Phải trong Binary)
        for (int i = mid; i < children.size(); i++) {
            processNode(children.get(i), depth + 1, positions);
        }
    }
}