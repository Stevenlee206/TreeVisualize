package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class PreOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Pre-Order(root)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",
                "2.  preOrderRecursive(root)",
                "3.  return result",
                "4.  func preOrder(node):",
                "5.  \tif (node == ∅) return",
                "6.  \tresult.add(node)",       // Index 5: Highlight VISIT tại đây
                "7.  \tpreOrder(node.left)",
                "8.  \tpreOrder(node.right)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                case START -> 1;
                // case CHECK_NULL -> 4;  <-- ĐÃ XÓA DÒNG NÀY
                case VISIT -> 5;
                case FINISHED -> 2;
                default -> -1;
            };
        }
        return -1;
    }
}