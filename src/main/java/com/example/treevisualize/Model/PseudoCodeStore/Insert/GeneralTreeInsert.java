package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class GeneralTreeInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "General Tree - Insert"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  newNode ← new Node(childVal)",        // Index 0
            "2.  parent ← search(parentVal)",          // Index 1
            "3.  if (parent ≠ ∅) then:",               // Index 2
            "4.      parent.children.add(newNode)"     // Index 3
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;            // Nhảy vào dòng gọi search
                case FOUND_INSERT_POS -> 2; // Tìm thấy cha, kiểm tra điều kiện
                case INSERT_SUCCESS -> 3;   // Thực hiện thêm vào list con
                default -> -1;
            };
        }
        return -1;
    }
}