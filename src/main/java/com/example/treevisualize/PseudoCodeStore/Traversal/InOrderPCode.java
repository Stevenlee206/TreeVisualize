package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class InOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "In-Order(root)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",
                "2.  inOrderRecursive(root, result)",
                "3.  return result",
                "4.  function inOrderRecursive(node, result)",
                "5.  	if (node == ∅) return",
                "6.  	inOrderRecursive(node.left, result)",
                "7.  	result.add(node)",
                "8.  	inOrderRecursive(node.right, result)"
        );
    }
}