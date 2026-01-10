package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BSTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "BST - Insert(node, value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  if (root == ∅) root ← new Node(val)",       // Index 0 (CHECK_ROOT_EMPTY)
            "2.  insertRecursive(root, val)",                // Index 1 (START)
            "3.  function insertRecursive(current, val):",   // Index 2
            "4.      if (val < current.val):",               // Index 3 (COMPARE_LESS)
            "5.          if (current.left == ∅):",           // Index 4 (FOUND_INSERT_POS)
            "6.              current.left ← new Node(val)",  // Index 5 (INSERT_SUCCESS)
            "7.          else: insertRecursive(left, val)",  // Index 6 (GO_LEFT)
            "8.      else if (val > current.val):",          // Index 7 (COMPARE_GREATER)
            "9.          if (current.right == ∅):",          // Index 8 (FOUND_INSERT_POS)
            "10.             current.right ← new Node(val)", // Index 9 (INSERT_SUCCESS)
            "11.         else: insertRecursive(right, val)"  // Index 10 (GO_RIGHT)
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case CHECK_ROOT_EMPTY -> 0;
                case START -> 1;
                case COMPARE_LESS -> 3;
                case FOUND_INSERT_POS -> 4; // Hoặc 8 tùy theo node đang xét
                case INSERT_SUCCESS -> 5;   // Hoặc 9 tùy theo node đang xét
                case GO_LEFT -> 6;
                case COMPARE_GREATER -> 7;
                case GO_RIGHT -> 10;
                // case FINISHED trả về -1 để thanh highlight đứng yên tại dòng cuối cùng thực hiện
                default -> -1;
            };
        }
        return -1;
    }
}