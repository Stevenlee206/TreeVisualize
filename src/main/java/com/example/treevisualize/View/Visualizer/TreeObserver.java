package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;

public interface TreeObserver {
    void onNodeChanged(Node node);//node value or status changed
    void onStructureChanged();//tree structure changed
    void onError(String message);//error occurred during operation
    void onPseudoStep(int lineIndex);//code execution step
}