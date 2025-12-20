package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.ScapegoatEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class ScapegoatDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Delete(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. BST-Delete(val)",         // 0
                "2. n--",                     // 1
                "3. If n < α * maxSize:",     // 2
                "4.    Rebuild(Root) "        // 3
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            if (se == StandardEvent.DELETE_SUCCESS) return 1;
            return 0; // Mặc định các bước search/delete map về dòng 1
        }
        if (event instanceof ScapegoatEvent sge) {
            return switch (sge) {
                case CHECK_WEIGHT -> 2;
                case REBUILD_START -> 3;
                default -> -1;
            };
        }
        return -1;
    }
}