package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;
import java.util.Arrays;
import java.util.List;

public class BFSPCode implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Breadth First Search (BFS)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  result ← ArrayList(), Q ← Queue()", // Index 0
                "2.  BFS(root)",                         // Index 1: START
                "3.  while Q ≠ ∅ do",                    // Index 2
                "4.      node ← Q.dequeue()",            // Index 3: TAKE_FROM_DS
                "5.      result.add(node)",               // Index 4: VISIT
                "6.      Q.enqueue(children)"            // Index 5
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof TraversalEvent te) {
            return switch (te) {
                case START -> 1;         // Highlight dòng bắt đầu
                case TAKE_FROM_DS -> 3;  // Highlight dòng lấy node ra khỏi Queue
                case VISIT -> 4;         // Highlight dòng thêm vào kết quả/thăm node
                // FINISHED trả về -1 để thanh highlight đứng yên tại dòng thực hiện cuối cùng
                default -> -1;
            };
        }
        return -1;
    }
}