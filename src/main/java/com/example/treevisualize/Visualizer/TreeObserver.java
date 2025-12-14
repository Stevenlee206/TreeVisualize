package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;

public interface TreeObserver {
    void onNodeChanged(Node node);
    void onStructureChanged();
    void onError(String message);
}
