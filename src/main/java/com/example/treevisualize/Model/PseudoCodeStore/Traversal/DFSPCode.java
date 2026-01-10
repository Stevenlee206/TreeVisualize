package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class DFSPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Depth First Search (DFS)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList()",             // Index 0
                "2.  dfsRecursive(root)",                // Index 1: Khớp với START
                "3.  function dfsRecursive(node):",      // Index 2
                "4.      if (node == ∅) return",         // Index 3
                "5.      result.add(node)",              // Index 4: Khớp với VISIT
                "6.      dfsRecursive(children)"         // Index 5
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                // Sáng khi bắt đầu gọi hàm đệ quy chính
                case START -> 1;
                
                // Sáng khi node hiện tại được nạp vào list (Visit)
                case VISIT -> 4;
                
                // Trả về -1 cho FINISHED để thanh highlight đứng yên tại node cuối cùng
                default -> -1;
            };
        }
        return -1;
    }
}