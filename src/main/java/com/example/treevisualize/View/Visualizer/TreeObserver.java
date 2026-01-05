package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;

public interface TreeObserver {
    void onNodeChanged(Node node);
    void onStructureChanged();
    void onError(String message);
    void onPseudoStep(int lineIndex);
}