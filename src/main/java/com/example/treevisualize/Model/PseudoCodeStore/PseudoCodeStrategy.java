package com.example.treevisualize.Model.PseudoCodeStore;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

import java.util.List;

public interface PseudoCodeStrategy {
    String getTitle();
    List<String> getLines();
    default int getLineIndex(AlgorithmEvent event) { return -1; }
}