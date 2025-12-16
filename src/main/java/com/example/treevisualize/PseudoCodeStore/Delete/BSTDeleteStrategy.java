package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class BSTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "BST-Delete(value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅) return",
                "2.  if (search(value) == ∅) return",
                "3.  root ← deleteRecursive(root, value) ",
                "4.  function deleteRecursive(node, value)",
                "5.  if (node == ∅) return ∅",
                "6.  if (value < node.value)",
                "7.      node.left ← deleteRecursive(node.left, value)",
                "8.      return node",
                "9.  else if (value > current.value)",
                "10.     node.right ← deleteRecursive(node.right, value)",
                "11.     return node",
                "12. else",
                "13.     if (node.left == ∅ & node.right == ∅) return ∅",
                "14. 	 if (node.left == ∅) return node.right",
                "15.	 if (node.right == ∅) return node.left",
                "16.	 successorValue ← findMin(node.right)",
                "17. 	 node.value ← successorValue",
                "18.	 node.right ← deleteRecursive(node.right, successorValue)"
        );
    }
}