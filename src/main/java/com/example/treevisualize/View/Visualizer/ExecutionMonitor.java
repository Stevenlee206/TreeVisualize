package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;

@FunctionalInterface
public interface ExecutionMonitor {
    void onEvent(AlgorithmEvent event, Node currentNode);
}
