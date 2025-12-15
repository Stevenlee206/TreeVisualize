package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class PreOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Pre-Order(node)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if node == ∅ return",
                "2.  visit(node)",
                "3.  Pre-Order(node.left)",
                "4.  Pre-Order(node.right)"
        );
    }
}