package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.ScapegoatEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class ScapegoatDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Scapegoat Tree - Delete & Global Rebuild"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function delete(val):",                         // Index 0
                "2.      if (search(val) == ∅) return",              // Index 1
                "3.      root ← deleteRecursive(root, val)",         // Index 2
                "4.      if (n < α * q):",                           // Index 3
                "5.          rebuild(root), q ← n",                  // Index 4
                "6.  ",                                              // Index 5
                "7.  function deleteRecursive(node, val):",          // Index 6
                "8.      node ← Standard_BST_Delete(node, val), n--",// Index 7
                "9.      node.updateSize()",                         // Index 8
                "10.     return node",                               // Index 9
                "11. ",                                              // Index 10
                "12. function rebuild(s):",                          // Index 11
                "13.     list ← flatten(s)",                         // Index 12
                "14.     s ← buildBalanced(list, 0, list.size-1)"    // Index 13
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 1;
                case DELETE_SUCCESS -> 7;
                default -> -1;
            };
        }
        if (event instanceof ScapegoatEvent e) {
            return switch (e) {
                case CHECK_WEIGHT -> 3;
                case REBUILD_START -> 4;
                default -> -1;
            };
        }
        return -1;
    }
}