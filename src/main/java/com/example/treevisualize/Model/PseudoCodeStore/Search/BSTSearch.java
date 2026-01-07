package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

import java.util.Arrays;
import java.util.List;

public class BSTSearch implements PseudoCodeStrategy {

    @Override
    public String getTitle() {
        return "BST-Search(value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if root = ∅ then",
                "2.      return ∅;",
                "3.  return searchRecursive(root, value)",
                "4.  function searchRecursive(node, value)",
                "5.      if node = ∅ then",
                "6.          return ∅",
                "7.      if node.value = value then",
                "8.          return node",
                "9.      else if value < node.value then",
                "10.         return searchRecursive(node.left, value)",
                "11.     else",
                "12.         return searchRecursive(node.right, value)"

        );
    }
    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof BSTEvents be) {
            return switch (be) {
                case SEARCH_START -> 0;    // Dòng 1
                case SEARCH_FAILED -> 5;   // Dòng 6
                case CHECK_NODE -> 4;      // Dòng 5
                case COMPARE_EQUAL -> 6;   // Dòng 7
                case SEARCH_SUCCESS -> 7;  // Dòng 8
                case COMPARE_LESS -> 8;    // Dòng 9
                case GO_LEFT -> 9;         // Dòng 10
                case COMPARE_GREATER -> 10;// Dòng 11
                case GO_RIGHT -> 11;       // Dòng 12
                default -> -1;
            };
        }
        return -1;
    }

}
