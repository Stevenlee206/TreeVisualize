package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class PostOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Post-Order(node)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if node == ∅ return",
                "2.  Post-Order(node.left)",
                "3.  Post-Order(node.right)",
                "4.  visit(node)"
        );
    }
}
