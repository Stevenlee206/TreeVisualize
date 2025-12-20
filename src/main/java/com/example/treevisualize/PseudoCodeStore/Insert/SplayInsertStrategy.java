package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.SplayEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class SplayInsertStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Splay Tree Insertion";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. Normal BST Insert(val)",                    // 0
                "2. x = newly inserted node",                   // 1
                "3. Splay(x) ",                                 // 2
                "   while x != root:",                          // 3
                "     if parent is root: Rotate(parent)",       // 4
                "     else if Zig-Zig:   Rotate(grand), ...",   // 5
                "     else (Zig-Zag):    Rotate(parent), ..."   // 6
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START, COMPARE_LESS, GO_LEFT, COMPARE_GREATER, GO_RIGHT -> 0;
                case INSERT_SUCCESS -> 1;
                default -> -1;
            };
        }
        if (event instanceof SplayEvent spe) {
            return switch (spe) {
                case SPLAY_START -> 2;
                case CASE_ZIG -> 4;
                case CASE_ZIG_ZIG -> 5;
                case CASE_ZIG_ZAG -> 6;
                case ROTATE_LEFT, ROTATE_RIGHT -> 3; // Highlight while loop khi xoay
                default -> -1;
            };
        }
        return -1;
    }
}