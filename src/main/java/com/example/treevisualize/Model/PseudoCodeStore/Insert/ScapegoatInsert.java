package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.ScapegoatEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class ScapegoatInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Scapegoat Tree - Insert & Rebuild"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function insert(val):",                         // Index 0
                "2.      newNode ← insertAndGetNode(val)",           // Index 1
                "3.      updateAncestorsSize(newNode)",              // Index 2
                "4.      if (depth(newNode) > log_{1/α}(q)):",       // Index 3
                "5.          s ← findScapegoat(newNode)",            // Index 4
                "6.          rebuild(s)",                            // Index 5
                "7.  ",                                              // Index 6
                "8.  function insertAndGetNode(val):",               // Index 7
                "9.      Standard_BST_Insert(val), n++, q++",        // Index 8
                "10. ",                                              // Index 9
                "11. function findScapegoat(u):",                    // Index 10
                "12.     while (size(u) ≤ α * size(p)):",            // Index 11
                "13.         u ← p",                                 // Index 12
                "14.     return p",                                  // Index 13
                "15. ",                                              // Index 11
                "16. function rebuild(s):",                          // Index 14
                "17.     list ← flatten(s)",                         // Index 15
                "18.     s ← buildBalanced(list, 0, list.size-1)"    // Index 16
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;
                case INSERT_SUCCESS -> 8;
                default -> -1;
            };
        }
        if (event instanceof ScapegoatEvent e) {
            return switch (e) {
                case UPDATE_SIZE -> 2;
                case CHECK_DEPTH -> 3;
                case FIND_SCAPEGOAT -> 4;
                case REBUILD_START -> 5;
                default -> -1;
            };
        }
        return -1;
    }
}