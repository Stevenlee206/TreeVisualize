package com.example.treevisualize.Model.PseudoCodeStore.Search;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

import java.util.Arrays;
import java.util.List;

public class GTSearch implements PseudoCodeStrategy {

    public String getTitle() {
        return "GeneralTree-Search(value)";
    }
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅) return",
                "2.  return searchRecursive(GeneralTreeNode root, value)",
                "3.  function searchRecursive(GeneralTreeNode node, int value)",
                "4. 	 if (node == ∅) return",
                "5.		 if (node.value == value) return node",
                "6.  	 found ← searchRecursive(node.leftMostChild, value)",
                "7.  	 if (found ≠ null) return found",
                "8.  	 return searchRecursive(node.rightSibling, value)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        return 0;
    }
}
