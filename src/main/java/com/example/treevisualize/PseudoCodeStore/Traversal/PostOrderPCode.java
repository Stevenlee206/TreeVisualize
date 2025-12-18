package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class PostOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Post-Order(root)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",
                "2.  postOrderRecursive(root, result)",
                "3.  return result",
                "4.  function postOrderRecursive(node, result)",
                "5.  	if (node == ∅) return",
                "6.  	postOrderRecursive(node.left, result)",
                "7.  	postOrderRecursive(node.right, result)",
                "8.  	result.add(node)"
        );
    }
}