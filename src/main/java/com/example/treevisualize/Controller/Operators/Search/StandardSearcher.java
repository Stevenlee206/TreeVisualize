package com.example.treevisualize.Controller.Operators.Search;

import com.example.treevisualize.Model.Tree.Tree;

import com.example.treevisualize.View.Visualizer.Events.StandardEvent;

public class StandardSearcher implements Searcher {
    @Override
    public void search(Tree tree, int value) {
        if (tree == null) return;
        // Optional: reset statuses so the visualization starts clean
        tree.resetTreeStatus();

        // Notify start so any pseudo-code mapping or recorder can capture a START frame
        tree.notifyEvent(StandardEvent.START, tree.getRoot());
        tree.notifyEvent(StandardEvent.SEARCH_START, tree.getRoot());

        // Use the UI-aware recursive search so events and node-status changes are emitted
        tree.search(value);

        // Optional: notify end
        tree.notifyEvent(StandardEvent.SEARCH_END, tree.getRoot());
    }
}
