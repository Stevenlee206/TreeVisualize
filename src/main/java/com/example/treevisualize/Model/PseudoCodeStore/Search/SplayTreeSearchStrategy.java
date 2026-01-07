package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import com.example.treevisualize.View.Visualizer.Events.SplayEvent;
import java.util.Arrays;
import java.util.List;

public class SplayTreeSearchStrategy implements PseudoCodeStrategy {

    @Override
    public String getTitle() { 
        return "SplaySearch(value)"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1. node ← BSTSearch(root, value)",         // 0
            "2. if (node ≠ ∅)",                         // 1
            "3.     Splay(node)",                       // 2
            "4.     return node",                       // 3
            "--- Splay(x) Logic ---",                   // 4
            "5. while (x.parent ≠ ∅)",                  // 5
            "6.     if (x.parent == root) Zig(x)",      // 6
            "7.     else if (Zig-Zig Case) ZigZig(x)",  // 7
            "8.     else ZigZag(x)"                     // 8
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        // Handle Standard Events (Search Phase)
        if (event instanceof StandardEvent se) {
            return switch(se) {
                case START, SEARCH_CHECK -> 0;
                case SEARCH_FOUND -> 1;
                default -> -1;
            };
        }
        // Handle Splay-Specific Events (Restructuring Phase)
        if (event instanceof SplayEvent spe) {
            return switch(spe) {
                case SPLAY_START -> 2;
                case CASE_ZIG -> 6;
                case CASE_ZIG_ZIG -> 7;
                case CASE_ZIG_ZAG -> 8;
                default -> -1;
            };
        }
        return -1;
    }
}