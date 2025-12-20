package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.SplayEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class SplayDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Splay-Delete(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  target ← Search(val)",          // 0
                "2.  if target == ∅ return error",   // 1
                "3.  parent ← target.parent",        // 2
                "4.  BST-Delete(target) ",           // 3
                "5.  if parent ≠ ∅ then",            // 4
                "6.      Splay(parent) ",            // 5
                "7.  else if root ≠ ∅",              // 6
                "8.      Splay(root) "               // 7
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START, COMPARE_LESS, COMPARE_GREATER -> 0;
                case DELETE_START -> 3;
                case DELETE_SUCCESS -> 4; // Sau khi xóa xong
                default -> -1;
            };
        }
        if (event instanceof SplayEvent spe) {
            return switch (spe) {
                case SPLAY_START -> 5; // Mặc định highlight dòng 6 (phổ biến nhất)
                case CASE_ZIG, CASE_ZIG_ZIG, CASE_ZIG_ZAG -> 5;
                default -> -1;
            };
        }
        return -1;
    }
}