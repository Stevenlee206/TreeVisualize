package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class PreOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Pre-Order Traversal"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",             // Index 0
                "2.  preOrderRecursive(root)",           // Index 1: Khớp với START
                "3.  function preOrderRecursive(node):", // Index 2
                "4.      if (node == ∅) return",         // Index 3
                "5.      result.add(node)",              // Index 4: Khớp với VISIT
                "6.      preOrderRecursive(children)"    // Index 5
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                // Sáng khi bắt đầu gọi hàm đệ quy chính (START)
                case START -> 1;
                
                // Sáng khi node được thăm (VISIT - ngay đầu hàm đệ quy)
                case VISIT -> 4;
                
                // FINISHED trả về -1 để thanh highlight đứng yên tại dòng logic thực hiện cuối cùng
                default -> -1;
            };
        }
        return -1;
    }
}