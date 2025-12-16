package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class BFSPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "BFS(root) - Level Order";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if root == ∅ return",
                "2.  Q ← Queue()",
                "3.  Q.enqueue(root)",
                "4.  while Q ≠ ∅ do",
                "5.      node ← Q.dequeue()",
                "6.      visit(node)",
                "7.      if node.left ≠ ∅ Q.enqueue(node.left)",
                "8.      if node.right ≠ ∅ Q.enqueue(node.right)"
        );
    }
}