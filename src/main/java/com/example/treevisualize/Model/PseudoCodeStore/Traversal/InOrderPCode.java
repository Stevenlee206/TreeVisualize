package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class InOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "In-Order(root)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  inOrderRecursive(root)",
                "2.  func inOrder(node):",
                "3.  \tIf null return",
                "4.  \tinOrder(node.left)",
                "5.  \tresult.add(node)",       // Index 4: VISIT
                "6.  \tinOrder(node.right)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event == TraversalEvent.VISIT) return 4;
        return -1;
    }
}