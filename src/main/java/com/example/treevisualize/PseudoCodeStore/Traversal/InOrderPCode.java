package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class InOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "In-Order Traversal";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "In-Order(node):",
                "1.  if node == ∅ return",
                "2.  In-Order(node.left)",
                "3.  visit(node)",
                "4.  In-Order(node.right)"
        );
    }
}