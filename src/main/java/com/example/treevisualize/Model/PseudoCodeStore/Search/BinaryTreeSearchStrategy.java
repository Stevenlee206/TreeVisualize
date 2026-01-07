package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BinaryTreeSearchStrategy implements PseudoCodeStrategy {

    @Override
    public String getTitle() { 
        return "BinaryDFS(node, value)"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  if (node == ∅) return ∅",               // 0
            "2.  if (node.value == value) return node", // 1
            "3.  res ← DFS(node.left, value)",          // 2
            "4.  if (res ≠ ∅) return res",               // 3
            "5.  res ← DFS(node.right, value)",         // 4
            "6.  return res"                            // 5
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch(se) {
                case CHECK_ROOT_EMPTY -> 0;
                case SEARCH_CHECK -> 1;
                case SEARCH_FOUND -> 1; // Highlights the return part of line 2
                case GO_LEFT -> 2;
                case GO_RIGHT -> 4;
                default -> -1;
            };
        }
        return -1;
    }
}