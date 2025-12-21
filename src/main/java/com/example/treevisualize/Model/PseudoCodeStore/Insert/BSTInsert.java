package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BSTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "BST-Insert(root, val)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅)",                      // Index 0
                "2.      root ← new Node(val)",            // Index 1
                "3.      return",                          // Index 2
                "4.  current ← root",                      // Index 3
                "5.  while true do",                       // Index 4
                "6.      if (val < current.val)",          // Index 5
                "7.          if (current.left == ∅)",      // Index 6
                "8.              current.left ← new Node", // Index 7
                "9.              break",                   // Index 8
                "10.         else current ← current.left", // Index 9
                "11.     else",                            // Index 10
                "12.         if (current.right == ∅)",     // Index 11
                "13.             current.right ← new Node",// Index 12
                "14.             break",                   // Index 13
                "15.         else current ← current.right" // Index 14
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case CHECK_ROOT_EMPTY -> 0;
                case INSERT_SUCCESS -> 2; // Hoặc dòng break (8/13) tùy ngữ cảnh
                case START -> 3;
                case COMPARE_LESS -> 5;
                case FOUND_INSERT_POS -> 6; // Kiểm tra null
                case GO_LEFT -> 9;
                case COMPARE_GREATER -> 10;
                case GO_RIGHT -> 14;
                default -> -1;
            };
        }
        return -1;
    }
}