package com.example.treevisualize.PseudoCodeStore.Traversal;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class BFSPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "BFS(root) - Level Order"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  Q.enqueue(root)",
                "2.  while Q ≠ ∅ do",
                "3.      node ← Q.dequeue()",  // Index 2: TAKE_FROM_DS
                "4.      result.add(node)",    // Index 3: VISIT
                "5.      Q.enqueue(children)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                case START -> 1;
                case TAKE_FROM_DS -> 2; // Highlight dòng dequeue
                case VISIT -> 3;        // Highlight dòng add result
                default -> -1;
            };
        }
        return -1;
    }
}