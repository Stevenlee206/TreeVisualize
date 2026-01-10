package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class PostOrderPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Post-Order Traversal"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",             // Index 0
                "2.  postOrderRecursive(root)",          // Index 1: Khớp với START
                "3.  function postOrderRecursive(node):",// Index 2
                "4.      if (node == ∅) return",         // Index 3
                "5.      postOrderRecursive(left)",      // Index 4
                "6.      postOrderRecursive(right)",     // Index 5
                "7.      result.add(node)"               // Index 6: Khớp với VISIT
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                // Sáng khi bắt đầu gọi hàm đệ quy (START)
                case START -> 1;
                
                // Sáng khi node hiện tại được nạp vào list (VISIT - sau cùng)
                case VISIT -> 6;
                
                // FINISHED trả về -1 để thanh highlight đứng yên tại dòng logic cuối cùng
                default -> -1;
            };
        }
        return -1;
    }
}