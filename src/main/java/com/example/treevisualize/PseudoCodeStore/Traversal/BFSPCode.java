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
                "1.  if (root == ∅) return",
                "2.  result ← ArrayList()",
                "3.  Q ← Queue()",
                "4.  Q.enqueue(root)",
                "5.  while Q ≠ ∅ do",
                "6.      node ← Q.dequeue()",
                "7.      result.add(node)",
                "8.      if (node.left ≠ ∅) Q.enqueue(node.left)",
                "9.      if (node.right ≠ ∅) Q.enqueue(node.right)",
                "10. return result"
        );
    }
}