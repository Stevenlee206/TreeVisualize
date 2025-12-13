package Visualizer;

import Node.Node;

public interface TreeObserver {
    void onNodeChanged(Node node);
    void onStructureChanged();
    void onError(String message);
}
