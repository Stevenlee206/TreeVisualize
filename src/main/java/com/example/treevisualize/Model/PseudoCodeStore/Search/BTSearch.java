package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;

import java.util.Arrays;
import java.util.List;

public class BTSearch implements PseudoCodeStrategy {

    @Override
    public String getTitle() {
        return "BT-Search(value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅) then",
                "2.      return ∅",
                "3.  return searchRecursive(BinaryTreeNode root, value)",
                "4.  function searchRecursive(GeneralTreeNode node, int value)",
                "5. 	 if (node == ∅) then",
                "6.          return ∅",
                "7.		 if (node.value == value) then",
                "8.          return node",
                "9.  	 found ← searchRecursive(node.left, value)",
                "10.  	 if (found ≠ null) ",
                "11.         return found",
                "12.  	 return searchRecursive(node.right, value)"

        );
    }
    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (!(event instanceof StandardEvent se)) {
            return -1;
        }

        return switch (se) {
            case START -> -1;                 // return searchRecursive(root, value)
            case CHECK_ROOT_EMPTY -> 0;      // if root = ∅
            case CHECK_NODE_EMPTY -> 4;      // if node = ∅
            case CHECK_VALUE -> 7;           // if node.value = value
            case GO_LEFT -> 8;           // search left subtree
            case FOUND_IN_LEFT -> 11;        // if found ≠ ∅
            case GO_RIGHT -> 10;         // search right subtree
            default -> -1;
        };
    }

