package com.example.treevisualize.Layout.Strategy;

import com.example.treevisualize.Node.Node;

public interface NodeAlignmentStrategy {
    /**
     * Tính toán toạ độ X của node cha dựa trên vị trí của con đầu và con cuối.
     * @param parent Node cha cần tính
     * @param firstChildX Toạ độ X của con đầu tiên (con trái nhất)
     * @param lastChildX Toạ độ X của con cuối cùng (con phải nhất)
     * @return Toạ độ X mong muốn của cha
     */
    double calculateParentX(Node parent, double firstChildX, double lastChildX);
}