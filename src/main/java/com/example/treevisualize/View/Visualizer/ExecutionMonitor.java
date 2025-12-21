package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;

@FunctionalInterface
public interface ExecutionMonitor {
    // Nhận AlgorithmEvent (Interface) nên có thể truyền StandardEvent HOẶC AVLEvent
    void onEvent(AlgorithmEvent event, Node currentNode);
}
