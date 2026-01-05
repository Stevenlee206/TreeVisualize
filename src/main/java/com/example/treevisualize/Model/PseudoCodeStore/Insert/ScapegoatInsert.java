package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.ScapegoatEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class ScapegoatInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Insert(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. BST-Insert(val)",                           // 0
                "2. Update subtree sizes up to root",           // 1
                "3. If depth(val) > log_1/α(TreeSize):",        // 2
                "4.    Find 'Scapegoat' node (ancestor...)",    // 3
                "5.    Rebuild(Scapegoat) "                     // 4
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            // Mọi bước insert BST map về dòng 1
            return 0;
        }
        if (event instanceof ScapegoatEvent sge) {
            return switch (sge) {
                case UPDATE_SIZE -> 1;
                case CHECK_DEPTH -> 2;
                case FIND_SCAPEGOAT -> 3;
                case REBUILD_START -> 4;
                default -> -1;
            };
        }
        return -1;
    }
}