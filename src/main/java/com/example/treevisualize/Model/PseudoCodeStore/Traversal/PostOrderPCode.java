package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class PostOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Post-Order(root)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",
                "2.  postOrderRecursive(root)",
                "3.  return result",
                "4.  function postOrderRecursive(node, result)",
                "5.  \tif (node == ∅) return",
                "6.  \tpostOrderRecursive(node.left, result)",
                "7.  \tpostOrderRecursive(node.right, result)",
                "8.  \tresult.add(node)"        // Index 7: Highlight VISIT tại đây
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                case START -> 1;
                // case CHECK_NULL -> 4; <-- ĐÃ XÓA DÒNG NÀY
                case VISIT -> 7;
                case FINISHED -> 2;
                default -> -1;
            };
        }
        return -1;
    }
}