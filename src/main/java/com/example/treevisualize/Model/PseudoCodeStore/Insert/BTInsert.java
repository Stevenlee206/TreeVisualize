package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Binary Tree - Insert (Level-Order)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  newNode ← new Node(val)",               // Index 0
                "2.  if (root == ∅) root ← newNode",          // Index 1 (CHECK_ROOT_EMPTY)
                "3.  Q ← Queue(), Q.enqueue(root)",          // Index 2 (START)
                "4.  while Q ≠ ∅ do",                        // Index 3
                "5.      current ← Q.poll()",                // Index 4
                "6.      if (current.left == ∅):",           // Index 5 (FOUND_INSERT_POS)
                "7.          current.left ← newNode, return",// Index 6 (INSERT_SUCCESS)
                "8.      else: Q.enqueue(current.left)",     // Index 7 (GO_LEFT)
                "9.      if (current.right == ∅):",          // Index 8 (FOUND_INSERT_POS)
                "10.         current.right ← newNode, return",// Index 9 (INSERT_SUCCESS)
                "11.     else: Q.enqueue(current.right)"     // Index 10 (GO_RIGHT)
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 2;             // Khớp Q ← Queue()
                case CHECK_ROOT_EMPTY -> 1;  // Khớp if (root == ∅)
                case FOUND_INSERT_POS -> {
                    yield 5; 
                }
                case INSERT_SUCCESS -> 6;    // Hoặc 9 tùy ngữ cảnh, mặc định highlight dòng gán đầu tiên
                case GO_LEFT -> 7;           // Khớp else: Q.enqueue(current.left)
                case GO_RIGHT -> 10;         // Khớp else: Q.enqueue(current.right)
                default -> -1;
            };
        }
        return -1;
    }
}