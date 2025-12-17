package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class PreOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Pre-Order(root)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
        		"1.  result ← ArrayList()",
                "2.  preOrderRecursive(root, result)",
                "3.  return result",
                "4.  function preOrderRecursive(node, result)",
                "5.  	if (node == ∅) return",
                "6.  	result.add(node)",
                "7.  	preOrderRecursive(node.left, result)",
                "8.  	preOrderRecursive(node.right, result)"
        );
    }
}