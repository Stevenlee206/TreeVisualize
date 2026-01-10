package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BSTSearchStrategy implements PseudoCodeStrategy {

    @Override
    public String getTitle() { 
        return "BSTSearch(node, value)"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  if (node == ∅) return ∅",               // 0
            "2.  if (node.value == value) return node", // 1
            "3.  if (value < node.value)",              // 2
            "4.      return search(node.left, value)",  // 3
            "5.  else",                                 // 4
            "6.      return search(node.right, value)"  // 5
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch(se) {
                case CHECK_ROOT_EMPTY -> 0;
                case SEARCH_CHECK -> 1;
                case SEARCH_FOUND -> 1;
                case COMPARE_LESS -> 2;
                case GO_LEFT -> 3;
                case COMPARE_GREATER -> 4;
                case GO_RIGHT -> 5;
                default -> -1;
            };
        }
        return -1;
    }
}