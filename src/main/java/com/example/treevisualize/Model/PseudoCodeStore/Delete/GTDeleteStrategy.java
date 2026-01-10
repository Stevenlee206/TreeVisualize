package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class GTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "General Tree - Delete"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  targetNode ← search(value)",                // Index 0
            "2.  if (targetNode == ∅) return",               // Index 1
            "3.  if (targetNode == root) clear()",           // Index 2
            "4.  parent ← targetNode.getParent()",           // Index 3
            "5.  parent.removeChild(targetNode)"            // Index 4
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 0;    // Khớp: targetNode = search(value)
                case DELETE_SUCCESS -> 4;  // Khớp: parent.removeChild sau khi xác định parent
                default -> -1;
            };
        }
        return -1;
    }
}