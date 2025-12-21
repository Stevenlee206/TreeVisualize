package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BSTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "BST-Delete(value)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅) return",                               // 0
                "2.  if (search(value) == ∅) return",                      // 1
                "3.  root ← deleteRecursive(root, value) ",                // 2
                "4.  function deleteRecursive(node, value)",               // 3
                "5.  if (node == ∅) return ∅",                             // 4
                "6.  if (value < node.value)",                             // 5
                "7.      node.left ← deleteRecursive(node.left, value)",   // 6
                "8.      return node",                                     // 7
                "9.  else if (value > current.value)",                     // 8
                "10.     node.right ← deleteRecursive(node.right, value)", // 9
                "11.     return node",                                     // 10
                "12. else",                                                // 11
                "13.     if (node.left == ∅ & node.right == ∅) return ∅",  // 12
                "14.     if (node.left == ∅) return node.right",           // 13
                "15.     if (node.right == ∅) return node.left",           // 14
                "16.     successorValue ← findMin(node.right)",            // 15
                "17.     node.value ← successorValue",                     // 16
                "18.     node.right ← deleteRecursive(node.right, succ)"   // 17
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 0;
                case COMPARE_LESS -> 5;
                case GO_LEFT -> 6;
                case COMPARE_GREATER -> 8;
                case GO_RIGHT -> 9;
                case DELETE_SUCCESS -> 11; // Vào nhánh else tìm thấy
                default -> -1;
            };
        }
        return -1;
    }
}