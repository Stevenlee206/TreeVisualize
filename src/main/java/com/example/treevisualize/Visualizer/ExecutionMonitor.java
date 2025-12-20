package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;

@FunctionalInterface
public interface ExecutionMonitor {
    // Nhận AlgorithmEvent (Interface) nên có thể truyền StandardEvent HOẶC AVLEvent
    void onEvent(AlgorithmEvent event, Node currentNode);
}
