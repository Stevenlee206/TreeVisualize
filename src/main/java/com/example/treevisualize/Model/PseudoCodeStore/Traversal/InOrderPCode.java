package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class InOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "In-Order Traversal"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",             // Index 0
                "2.  inOrderRecursive(root)",            // Index 1: Khớp với START
                "3.  function inOrderRecursive(node):",  // Index 2
                "4.      if (node == ∅) return",         // Index 3
                "5.      inOrderRecursive(left)",        // Index 4
                "6.      result.add(node)",              // Index 5: Khớp với VISIT
                "7.      inOrderRecursive(right)"        // Index 6
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                // Sáng khi bắt đầu gọi hàm đệ quy chính (START)
                case START -> 1;
                
                // Sáng khi node hiện tại được nạp vào list (VISIT)
                case VISIT -> 5;
                
                // Trả về -1 cho FINISHED để thanh highlight đứng yên tại node cuối cùng được thăm
                default -> -1;
            };
        }
        return -1;
    }
}