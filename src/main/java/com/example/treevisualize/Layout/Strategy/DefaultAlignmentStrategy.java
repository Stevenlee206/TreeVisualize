package com.example.treevisualize.Layout.Strategy;

import com.example.treevisualize.Node.Node;

public class DefaultAlignmentStrategy implements NodeAlignmentStrategy {
    @Override
    public double calculateParentX(Node parent, double firstChildX, double lastChildX) {
        // Mặc định: Cha nằm chính giữa 2 con biên
        return (firstChildX + lastChildX) / 2.0;
    }
}
